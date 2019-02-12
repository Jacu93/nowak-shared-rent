package com.piekoszek.nowaksharedrent.auth

import spock.lang.Specification

class AuthControllerTest extends Specification {

    def "Save an account in database"() {

        given: "new account data is entered"
        def acc = new Account("example@mail.com", "tester", "secret")

        expect: "method createAccount should return true"
        AuthService.createAccount(acc)
    }

    def "Saving 2 accounts in a row with different id (email)"(Account acc) {

        given: "data of the accounts that are going to be saved one after another"
        def firstAccount = new Account("example@mail.com", "tester", "secret")
        def secondAccount = new Account("testing@mail.com", "tester", "secret")

        expect: "method createAccount should return true"
        AuthService.createAccount(acc)

        where: "true should be returned for both attempts"
        acc | _
        firstAccount | _
        secondAccount | _
    }

    def "Saving 2 accounts in a row with the same id (email)"(Account acc, boolean result) {

        given: "data of the accounts that are going to be saved one after another"
        def account = new Account("example@mail.com", "tester", "secret")

        expect: "method createAccount should return true"
        AuthService.createAccount(acc) == result

        where: "true should be returned only for the first attempt"
        acc | result
        account | true
        account | false
    }
}