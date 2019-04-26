package com.piekoszek.nowaksharedrent.jwt


import com.piekoszek.nowaksharedrent.dto.User
import com.piekoszek.nowaksharedrent.dto.UserApartment
import com.piekoszek.nowaksharedrent.jwt.exceptions.InvalidTokenException
import com.piekoszek.nowaksharedrent.time.TimeService
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import spock.lang.Specification
import spock.lang.Subject

import javax.crypto.SecretKey

class JwtServiceTest extends Specification {

    @Subject
    JwtService jwtService

    TimeService timeService = Mock(TimeService)
    SecretKey key

    def user = User.builder()
            .name("exampleName")
            .email("example@mail.com")
            .build()

    def setup() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        jwtService = new JwtServiceConfiguration().jwtService(key, timeService)
    }

    def "Generate correct token with user name and email"() {

        given: "JWT is generated at second 1 and checked at second 2"
        timeService.millisSinceEpoch() >>> [1000, 2000]
        def token = jwtService.generateToken(user)

        expect: "Information saved in and read from the token are the same"
        jwtService.readToken(token) == JwtData.builder().name("exampleName").email("example@mail.com").exp(new Date(1000 + 60 * 60 * 1000)).apartments(new HashSet<UserApartment>()).build()
    }

    def "Check valid token"() {

        given: "JWT is generated at second 1 and checked at second 2"
        timeService.millisSinceEpoch() >>> [1000, 2000]
        def token = jwtService.generateToken(user)

        when: "Token is checked"
        jwtService.validateToken(token)

        then: "Nothing happens, which means that token is valid"
    }

    def "Check expired token"() {

        given: "JWT is generated at second 1 and checked one hour later"
        timeService.millisSinceEpoch() >>> [1000, 1000 * 60 * 60 + 2000]
        def token = jwtService.generateToken(user)

        when: "Token is checked"
        jwtService.validateToken(token)

        then: "Token is expired"
        def ex = thrown(InvalidTokenException)
        ex.message == "Token expired"
    }

    def "Check token with wrong format"() {

        given: "JWT in incorrect format"
        def token = "eyJhbGciOiJIUzI1NiJ9eyJpYXQiOjEsImV4cCI6MzYwMX0.Q5HBk79pi0YzazlbpBT0kUXw8vwXcs76FcAw52DKaFI"

        when: "Token is checked"
        jwtService.validateToken(token)

        then: "Token is in incorrect format"
        def ex = thrown(InvalidTokenException)
        ex.message == "Expected bearer authorization type!"
    }

    def "Check token with incorrect signature"() {

        given: "JWT is generated at second 1 and checked at second 2"
        timeService.millisSinceEpoch() >>> [1000, 2000]
        def token = jwtService.generateToken(user)

        and: "Incorrect secret is used for token validation"
        def secret = ")H@McQfTjWnZr4u7x!A%D*G-KaNdRgUk"
        jwtService = new JwtServiceConfiguration().jwtService(Keys.hmacShaKeyFor(secret.getBytes()), timeService)

        when: "Token is checked"
        jwtService.validateToken(token)

        then: "Signature is invalid"
        def ex = thrown(InvalidTokenException)
        ex.message == "Invalid token signature"
    }
}