package com.piekoszek.nowaksharedrent.apartment

import com.piekoszek.nowaksharedrent.dto.User
import com.piekoszek.nowaksharedrent.dto.UserApartment
import com.piekoszek.nowaksharedrent.dto.UserService
import com.piekoszek.nowaksharedrent.uuid.UuidService
import spock.lang.Specification
import spock.lang.Subject

class ApartmentServiceTest extends Specification {

    @Subject
    ApartmentService apartmentService
    UserService userService = Mock(UserService)
    UuidService uuidService = Mock(UuidService)

    def setup() {
        apartmentService = new ApartmentServiceConfiguration().apartmentService(uuidService, userService)
    }

    def "Creating new apartment"() {

        given: "User provided apartment address and city"
        def address = "Street 1"
        def city = "City"
        def userId = "user@mail.com"

        when:
        userService.getUser(userId) >> new User(userId, "User", new HashSet<UserApartment>())
        uuidService.generateUuid() >> "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        apartmentService.createApartment(address, city, userId)

        then: "Apartment is created and user is an admin of this apartment. He is also added as a tenant"
        def apartment = apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a")
        apartment.getId() == "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        apartment.getAddress() == address
        apartment.getCity() == city
        apartment.getAdmin() == userId
        apartmentService.hasTenant(apartment, userId)
    }

    def "Adding new tenant to the existing apartment"() {

        given: "User provided tenant information and id of an apartment where it should be added"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def tenantId = "tenant@mail.com"
        def adminId = "admin@mail.com"
        def address = "Street 1"
        def city = "City"

        when:
        userService.getUser(adminId) >> new User(adminId, "Admin", new HashSet<UserApartment>())
        userService.getUser(tenantId) >> new User(tenantId, "Tenant", new HashSet<UserApartment>())
        uuidService.generateUuid() >> apartmentId
        apartmentService.createApartment(address, city, adminId)
        apartmentService.addTenant(tenantId, apartmentId)

        then: "Tenant is added to the apartment"
        apartmentService.hasTenant(apartmentService.getApartment(apartmentId), tenantId)
    }

    def "Adding new tenant to the apartment where he was already added"() {

        given: "User provided tenants information and id of an apartment where they should be added"
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def firstTenantId = "tenant@mail.com"
        def secondTenantId = "tenant@mail.com"
        def adminId = "admin@mail.com"
        def address = "Street 1"
        def city = "City"

        when:
        userService.getUser(adminId) >> new User(adminId, "Admin", new HashSet<UserApartment>())
        userService.getUser(firstTenantId) >> new User(firstTenantId, "Tenant", new HashSet<UserApartment>())
        userService.getUser(secondTenantId) >> new User(secondTenantId, "Tenant", new HashSet<UserApartment>())
        uuidService.generateUuid() >> apartmentId
        apartmentService.createApartment(address, city, adminId)
        apartmentService.addTenant(firstTenantId, apartmentId)
        apartmentService.addTenant(secondTenantId, apartmentId)

        then: "Nothing happens. Apartment still has only 2 tenants (admin and regular tenant"
        def apartment = apartmentService.getApartment("9dc8bd06-6a89-455a-bc31-9f85f5036b5a")
        apartment.getTenants().size() == 2
    }

    def "Updating balance of tenants after transaction with defined payer"() {

        given: "User wants to update balance for tenants - common product transaction type with defined payer. Tenant is payer in this case."
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def tenantId = "tenant@mail.com"
        def adminId = "admin@mail.com"
        def address = "Street 1"
        def city = "City"
        def value = 20

        when:
        userService.getUser(adminId) >> new User(adminId, "Admin", new HashSet<UserApartment>())
        userService.getUser(tenantId) >> new User(tenantId, "Tenant", new HashSet<UserApartment>())
        uuidService.generateUuid() >> apartmentId
        apartmentService.createApartment(address, city, adminId)
        apartmentService.addTenant(tenantId, apartmentId)
        apartmentService.updateBalance(tenantId, apartmentId, value)

        then: "Balance of tenants is updated"
        Set<Tenant> tenants = apartmentService.getApartment(apartmentId).getTenants()
        for (Tenant tenant : tenants) {
            if (tenant.getEmail() == adminId) {
                assert tenant.getBalance() == 10
            } else {
                assert tenant.getBalance() == -10
            }
        }
    }

    def "Updating balance of tenants after transaction with undefined payer"() {

        given: "User wants to update balance for tenants - bill transaction type with undefined payer."
        def apartmentId = "9dc8bd06-6a89-455a-bc31-9f85f5036b5a"
        def tenantId = "tenant@mail.com"
        def adminId = "admin@mail.com"
        def address = "Street 1"
        def city = "City"
        def value = 20

        when:
        userService.getUser(adminId) >> new User(adminId, "Admin", new HashSet<UserApartment>())
        userService.getUser(tenantId) >> new User(tenantId, "Tenant", new HashSet<UserApartment>())
        uuidService.generateUuid() >> apartmentId
        apartmentService.createApartment(address, city, adminId)
        apartmentService.addTenant(tenantId, apartmentId)
        apartmentService.updateBalance(apartmentId, value)

        then: "Balance of tenants is updated"
        Set<Tenant> tenants = apartmentService.getApartment(apartmentId).getTenants()
        for (Tenant tenant : tenants) {
            assert tenant.getBalance() == 10
        }
    }
}
