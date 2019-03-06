package com.piekoszek.nowaksharedrent.auth

import com.piekoszek.nowaksharedrent.jwt.JwtGenerator
import spock.lang.Specification
import spock.lang.Subject

class JwtGeneratorTest extends Specification {

    @Subject
    JwtGenerator jwtGenerator

    def setup() {
        //jwtGenerator = new JwtGenerator();

    }
}
