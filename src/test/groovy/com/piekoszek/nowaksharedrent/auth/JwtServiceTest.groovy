package com.piekoszek.nowaksharedrent.auth

import com.piekoszek.nowaksharedrent.jwt.JwtData
import com.piekoszek.nowaksharedrent.jwt.JwtService
import com.piekoszek.nowaksharedrent.jwt.JwtServiceConfiguration
import com.piekoszek.nowaksharedrent.time.TimeService
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import spock.lang.Specification
import spock.lang.Subject

import javax.crypto.SecretKey

class JwtServiceTest extends Specification {

    @Subject
    JwtService jwtService
    TimeService timeService = Mock()
    SecretKey key

    def jwtData = new JwtData("example@mail.com", "exampleName")

    def setup() {
        key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
        jwtService = new JwtServiceConfiguration().jwtService(key, timeService)
    }

    def "Generate correct token with user name and email"() {

        given: "jwt is generated as second 1 and checked at second 2"
        timeService.millisSinceEpoch() >>> [1000, 2000]
        def token = jwtService.generateToken(jwtData)

        //expect:
        //jwtParser.jwtInfo(token) == JwtInfo.builder().email("example@mail.com").name("exampleName").region("exampleRegion").token(token).build()

    }
}
