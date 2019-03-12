package com.piekoszek.nowaksharedrent.auth

import com.piekoszek.nowaksharedrent.hash.HashService
import com.piekoszek.nowaksharedrent.jwt.JwtService
import spock.lang.Specification
import spock.lang.Subject

import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class AuthServiceTest extends Specification {

    @Subject
    AuthService authService
    ValidatorFactory validatorFactory
    Validator validator
    HashService hashService = Mock(HashService)
    JwtService jwtService = Mock(JwtService)

    def setup() {
        authService = new AuthServiceConfiguration().authService(hashService, jwtService)
        validatorFactory = Validation.buildDefaultValidatorFactory()
        validator = validatorFactory.getValidator()
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

    def "Saving an account with wrong email" () {

        given: "new account data is entered"
        def account = new Account("example@@mail.com", "tester", "secret")
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account)

        expect: "error message should appear"
        constraintViolations.size() == 1
        constraintViolations.iterator().next().getMessage() == "Invalid email"
    }

    def "Saving an account with too short password" () {

        given: "new account data is entered"
        def account = new Account("example@mail.com", "tester", "sec")
        Set<ConstraintViolation<Account>> constraintViolations = validator.validate(account)

        expect: "error message should appear"
        constraintViolations.size() == 1
        constraintViolations.iterator().next().getMessage() == "Too short password"
    }
}