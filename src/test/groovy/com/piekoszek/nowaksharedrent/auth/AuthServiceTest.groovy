package com.piekoszek.nowaksharedrent.auth

import spock.lang.Specification
import spock.lang.Subject

class AuthServiceTest extends Specification {

    @Subject
    AuthService authService

    def setup() {
        authService = new AuthServiceConfiguration().authService()
    }

    def "Save an account in database"() {

        given: "new account data is entered"
        def account = new Account("example@mail.com", "tester", "secret")

        expect: "account is created successfully"
        authService.createAccount(account)
    }

    def "Saving 2 accounts in a row with different id (email)"() {

        given: "data of accounts that are going to be saved one after another"
        def firstAccount = new Account("example@mail.com", "example", "pass")
        def secondAccount = new Account("testing@mail.com", "tester", "secret")

        expect: "both accounts are created successfully"
        authService.createAccount(firstAccount)
        authService.createAccount(secondAccount)
    }

    def "Saving 2 accounts in a row with the same id (email)"() {

        given: "data of accounts that are going to be saved one after another"
        def firstAccount = new Account("example@mail.com", "tester", "secret")
        def secondAccount = new Account("example@mail.com", "example", "pass")

        expect: "only first account is created successfully, second one is not because of duplicated id (email)"
        authService.createAccount(firstAccount)
        !authService.createAccount(secondAccount)
    }
}