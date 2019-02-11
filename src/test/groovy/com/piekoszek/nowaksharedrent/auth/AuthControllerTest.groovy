package com.piekoszek.nowaksharedrent.auth

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class AuthControllerTest extends Specification {
    @LocalServerPort
    private int webPort

    private AccountRepository repository
    private static final String PATH = "/auth/signup"

    def setup() {
        repository.deleteAll()
    }

    def "Register account and return 201 status code"() {
        when:
        given()
                .port(webPort)
                .when()
                .contentType(ContentType.JSON)
                .body([email: "example@mail.com", name: "tester", password: "secret"])
                .post(PATH)
                .then()
                .statusCode(200)
        then:
        repository.count() == 1
        repository.existsById("example@mail.com")
    }

    def "Two times create with same email, first 201, then 400 response"() {
        given:
        given()
                .port(webPort)
                .when()
                .contentType(ContentType.JSON)
                .body([email: "example@mail.com", name: "tester", password: "secret"])
                .post(PATH)
                .then().statusCode(200)

        given()
                .port(webPort)
                .when()
                .contentType(ContentType.JSON)
                .body([email: "example@mail.com", name: "tester", password: "secret"])
                .post(PATH)
                .then().statusCode(400)
                .body("message", equalTo("Account with such email already exists"))
    }
}