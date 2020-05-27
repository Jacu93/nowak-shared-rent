package com.piekoszek.nowaksharedrent.invite

import com.piekoszek.nowaksharedrent.apartment.Apartment
import com.piekoszek.nowaksharedrent.apartment.ApartmentService
import com.piekoszek.nowaksharedrent.user.UserService
import com.piekoszek.nowaksharedrent.invite.exceptions.InviteCreatorException
import com.piekoszek.nowaksharedrent.uuid.UuidService
import spock.lang.Specification
import spock.lang.Subject

class InviteServiceTest extends Specification {

    @Subject
    InviteService inviteService
    ApartmentService apartmentService = Mock(ApartmentService)
    UserService userService = Mock(UserService)
    UuidService uuidService = Mock(UuidService)

    def setup() {
        inviteService = new InviteServiceConfiguration().inviteService(apartmentService, userService, uuidService)
    }

    def "Creating new invitation" () {

        given: "Email associated with registered account was provided by the user who is an admin of targeted apartment"
        def sender = "admin@test.com"
        def receiver = "tester@test.com"
        def apartmentId = "0d1bcf5d-91c9-4a32-8930-62e850e53310"

        when:
        uuidService.generateUuid() >> "21b987c9-1660-4873-b37e-3f8949b54602"
        userService.isAccountExists(receiver) >> true
        Apartment apartment = new Apartment(apartmentId, "Example 1", "City", "admin@test.com")
        apartmentService.getApartment(apartmentId) >> apartment
        inviteService.createInvitation(sender, receiver, apartmentId)

        then: "Invitation is created"
        List<Invitation> invitations = new ArrayList<>(inviteService.getInvitations(receiver))
        invitations.size() == 1
        invitations[0].getId() == "21b987c9-1660-4873-b37e-3f8949b54602"
        invitations[0].getReceiver() == receiver
        invitations[0].getSender() == sender
        invitations[0].getApartmentId() == apartmentId
        invitations[0].getApartmentName() == "Example 1, City"
    }

    def "Creating two identical invitations one after another (same receiver, same apartment)" () {

        given: "Email associated with registered account was provided by the user. Before the first invitation was resolved user attempted to invite the same account to the same apartment"
        def sender = "admin@test.com"
        def receiver = "tester@test.com"
        def apartmentId = "0d1bcf5d-91c9-4a32-8930-62e850e53310"

        when:
        uuidService.generateUuid() >> ["21b987c9-1660-4873-b37e-3f8949b54602", "828a1452-4004-42ae-a656-d3813d82d867"]
        userService.isAccountExists(receiver) >> true
        Apartment apartment = new Apartment(apartmentId, "Example 1", "City", "admin@test.com")
        apartmentService.getApartment(apartmentId) >> apartment
        inviteService.createInvitation(sender, receiver, apartmentId)
        inviteService.createInvitation(sender, receiver, apartmentId)

        then: "Second invitation is not created"
        def ex = thrown(InviteCreatorException)
        ex.message == "Duplicated invitation found!"
    }

    def "Creating new invitation but invited user is already tenant of this apartment" () {

        given: "Account associated with an email provided by the user is already tenant in the targeted apartment"
        def sender = "admin@test.com"
        def receiver = "tester@test.com"
        def apartmentId = "0d1bcf5d-91c9-4a32-8930-62e850e53310"

        when:
        uuidService.generateUuid() >> "21b987c9-1660-4873-b37e-3f8949b54602"
        userService.isAccountExists(receiver) >> true
        Apartment apartment = new Apartment(apartmentId, "Example 1", "City", "admin@test.com")
        apartmentService.getApartment(apartmentId) >> apartment
        apartmentService.hasTenant(apartment, receiver) >> true
        inviteService.createInvitation(sender, receiver, apartmentId)

        then: "Invitation is not created"
        def ex = thrown(InviteCreatorException)
        ex.message == "User is already tenant of this apartment!"
    }

    def "Creating new invitation but invited account does not exist" () {

        given: "Email provided by the user is not associated with any existing account"
        def sender = "admin@test.com"
        def receiver = "tester@test.com"
        def apartmentId = "0d1bcf5d-91c9-4a32-8930-62e850e53310"

        when:
        uuidService.generateUuid() >> "21b987c9-1660-4873-b37e-3f8949b54602"
        userService.isAccountExists(receiver) >> false
        Apartment apartment = new Apartment(apartmentId, "Example 1", "City", "admin@test.com")
        apartmentService.getApartment(apartmentId) >> apartment
        inviteService.createInvitation(sender, receiver, apartmentId)

        then: "Invitation is not created"
        def ex = thrown(InviteCreatorException)
        ex.message == "User with email " + receiver + " not found!"
    }

    def "Accepting invitation" () {

        given: "User accepts existing invitation"
        def sender = "admin@test.com"
        def receiver = "tester@test.com"
        def apartmentId = "0d1bcf5d-91c9-4a32-8930-62e850e53310"
        def invitationId = "21b987c9-1660-4873-b37e-3f8949b54602"

        when:
        uuidService.generateUuid() >> invitationId
        userService.isAccountExists(receiver) >> true
        Apartment apartment = new Apartment(apartmentId, "Example 1", "City", "admin@test.com")
        apartmentService.getApartment(apartmentId) >> apartment
        inviteService.createInvitation(sender, receiver, apartmentId)

        then: "Invitation is removed. User becomes tenant in targeted apartment"
        inviteService.resolveInvitation(receiver, invitationId, true).get() == "Invitation accepted"
    }

    def "Rejecting invitation" () {

        given: "User rejects existing invitation"
        def sender = "admin@test.com"
        def receiver = "tester@test.com"
        def apartmentId = "0d1bcf5d-91c9-4a32-8930-62e850e53310"
        def invitationId = "21b987c9-1660-4873-b37e-3f8949b54602"

        when:
        uuidService.generateUuid() >> invitationId
        userService.isAccountExists(receiver) >> true
        Apartment apartment = new Apartment(apartmentId, "Example 1", "City", "admin@test.com")
        apartmentService.getApartment(apartmentId) >> apartment
        inviteService.createInvitation(sender, receiver, apartmentId)

        then: "Invitation is removed"
        inviteService.resolveInvitation(receiver, invitationId, false).get() == "Invitation rejected"
    }
}
