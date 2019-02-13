package com.piekoszek.nowaksharedrent.auth

import spock.lang.Specification
import spock.lang.Subject

class AuthControllerTest extends Specification {

    @Subject
    AuthService authService

    def setup() {
        authService = new AccountConfiguration().authService()
    }

    def "Save an account in database"() {

        given: "new account data is entered"
        def account = new Account("example@mail.com", "tester", "secret")

        expect: "method createAccount should return true"
        authService.createAccount(account)
    }

    def "Saving 2 accounts in a row with different id (email)"() {

        given: "data of the accounts that are going to be saved one after another"
        def firstAccount = new Account("example@mail.com", "tester", "secret")
        def secondAccount = new Account("testing@mail.com", "tester", "secret")

        expect: "method createAccount should return true twice"
        authService.createAccount(firstAccount)
        authService.createAccount(secondAccount)
    }

    def "Saving 2 accounts in a row with the same id (email)"() {

        given: "data of the accounts that are going to be saved one after another"
        def account = new Account("example@mail.com", "tester", "secret")

        expect: "method createAccount should return true only for the first attempt"
        authService.createAccount(account)
        authService.createAccount(account) == false
    }
}