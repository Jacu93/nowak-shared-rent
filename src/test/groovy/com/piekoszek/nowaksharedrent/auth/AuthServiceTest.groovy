package com.piekoszek.nowaksharedrent.auth

import com.piekoszek.nowaksharedrent.email.EmailService
import com.piekoszek.nowaksharedrent.user.User
import com.piekoszek.nowaksharedrent.user.UserApartment
import com.piekoszek.nowaksharedrent.user.UserService
import com.piekoszek.nowaksharedrent.hash.HashService
import com.piekoszek.nowaksharedrent.jwt.JwtService
import com.piekoszek.nowaksharedrent.uuid.UuidService
import spock.lang.Specification
import spock.lang.Subject

import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

class AuthServiceTest extends Specification {

    @Subject
    AuthService authService
    UserService userService = Mock(UserService)
    HashService hashService = Mock(HashService)
    JwtService jwtService = Mock(JwtService)
    EmailService emailService = Mock(EmailService)
    UuidService uuidService = Mock(UuidService)


    def setup() {
        authService = new AuthServiceConfiguration().authService(userService, hashService, jwtService, emailService, uuidService)
    }

    def "Save an account in database"() {

        given: "new account data is entered"
        def account = new Account("example@mail.com", "tester", "secret", null)
        def user = User.builder()
                .email(account.getEmail())
                .name(account.getName())
                .apartments(new HashSet<UserApartment>())
                .build()

        when:
        hashService.encrypt(account.getPassword()) >> "2BB80D537B1DA3E38BD30361AA855686BDE0EACD7162FEF6A25FE97BF527A25B"
        jwtService.generateToken(user) >> "bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiSmFjZWsiLCJlbWFpbCI6ImphY2tpZS5uOTNAZ21haWwuY29tIiwiaWF0IjoxNTUzMDc0NjcwLCJleHAiOjE1NTMwNzgyNzB9.tMItmryL5GQfKWoOS8XL1iloCvrrkxBCcf-vLajdQvI_tJpg8QOGVM6obs56DU5m-ySIwMDkIj9s-krL-8E3iQ"

        then: "account is created successfully"
        authService.createAccount(account).isPresent()
    }

    def "Saving 2 accounts in a row with different id (email)"() {

        given: "data of accounts that are going to be saved one after another"
        def firstAccount = new Account("example@mail.com", "example", "pass", null)
        def firstUser = User.builder()
                .email(firstAccount.getEmail())
                .name(firstAccount.getName())
                .apartments(new HashSet<UserApartment>())
                .build()
        def secondAccount = new Account("testing@mail.com", "tester", "secret", null)
        def secondUser = User.builder()
                .email(secondAccount.getEmail())
                .name(secondAccount.getName())
                .apartments(new HashSet<UserApartment>())
                .build()

        when:
        hashService.encrypt(firstAccount.getPassword()) >> "D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1"
        hashService.encrypt(secondAccount.getPassword()) >> "2BB80D537B1DA3E38BD30361AA855686BDE0EACD7162FEF6A25FE97BF527A25B"
        jwtService.generateToken(firstUser) >> "bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiSmFjZWsiLCJlbWFpbCI6ImphY2tpZS5uOTNAZ21haWwuY29tIiwiaWF0IjoxNTUzMDc0NjcwLCJleHAiOjE1NTMwNzgyNzB9.tMItmryL5GQfKWoOS8XL1iloCvrrkxBCcf-vLajdQvI_tJpg8QOGVM6obs56DU5m-ySIwMDkIj9s-krL-8E3iQ"
        jwtService.generateToken(secondUser) >> "bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoicGVubnlzIiwiZW1haWwiOiJwZW5ueXNAdGVzdC5wbCIsImlhdCI6MTU1MzA3NTM2NCwiZXhwIjoxNTUzMDc4OTY0fQ.xL_YQUzM1tsA6e7r9XAB5CsSPmfTf3EbmOaw-CAVv27GxFL-osCd8qobDVkbAtGpHPPVeVurFzH9KCN1GQXt7g"

        then: "both accounts are created successfully"
        authService.createAccount(firstAccount).isPresent()
        authService.createAccount(secondAccount).isPresent()
    }

    def "Saving 2 accounts in a row with the same id (email)"() {

        given: "data of accounts that are going to be saved one after another"
        def firstAccount = new Account("example@mail.com", "tester", "secret", null)
        def firstUser = User.builder()
                .email(firstAccount.getEmail())
                .name(firstAccount.getName())
                .apartments(new HashSet<UserApartment>())
                .build()
        def secondAccount = new Account("example@mail.com", "example", "pass", null)
        def SecondUser = User.builder()
                .email(secondAccount.getEmail())
                .name(secondAccount.getName())
                .apartments(new HashSet<UserApartment>())
                .build()

        when:
        hashService.encrypt(firstAccount.getPassword()) >> "2BB80D537B1DA3E38BD30361AA855686BDE0EACD7162FEF6A25FE97BF527A25B"
        hashService.encrypt(secondAccount.getPassword()) >> "D74FF0EE8DA3B9806B18C877DBF29BBDE50B5BD8E4DAD7A3A725000FEB82E8F1"
        jwtService.generateToken(firstUser) >> "bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiSmFjZWsiLCJlbWFpbCI6ImphY2tpZS5uOTNAZ21haWwuY29tIiwiaWF0IjoxNTUzMDc0NjcwLCJleHAiOjE1NTMwNzgyNzB9.tMItmryL5GQfKWoOS8XL1iloCvrrkxBCcf-vLajdQvI_tJpg8QOGVM6obs56DU5m-ySIwMDkIj9s-krL-8E3iQ"
        jwtService.generateToken(SecondUser) >> "bearer eyJhbGciOiJIUzUxMiJ9.eyJuYW1lIjoiSmFjZWsiLCJlbWFpbCI6ImphY2tpZS5uOTNAZ21haWwuY29tIiwiaWF0IjoxNTUzMDc1NjI2LCJleHAiOjE1NTMwNzkyMjZ9.MqXp_OehMQKySMS71ykR3vvoUbECkjCYvBDyn_FF9T05z-iKu-57VehmDQJ-0HpJ6_p2rDcNg6hHLPOKcVUfNQ"

        then: "only first account is created successfully, second one is not because of duplicated id (email)"
        authService.createAccount(firstAccount).isPresent()
        !authService.createAccount(secondAccount).isPresent()
    }
}