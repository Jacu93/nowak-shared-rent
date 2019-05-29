package com.piekoszek.nowaksharedrent.apartment

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

//    def "Creating new apartment"() {
//
//        given: ""
//
//        when:
//
//        then: ""
//    }
//
//    def "Adding new tenant to the apartment"() {
//
//        given: ""
//
//        when:
//
//        then: ""
//    }
//
//    def "Adding new tenant to the apartment which is already there"() {
//
//        given: ""
//
//        when:
//
//        then: ""
//    }
//
//    def "Updating balance of tenants after transaction with defined payer"() {
//
//        given: ""
//
//        when:
//
//        then: ""
//    }
//
//    def "Updating balance of tenants after transaction with undefined payer"() {
//
//        given: ""
//
//        when:
//
//        then: ""
//    }
}
