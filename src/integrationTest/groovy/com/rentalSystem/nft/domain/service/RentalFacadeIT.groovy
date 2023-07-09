package com.rentalSystem.nft.domain.service

import com.rentalSystem.xrpl.IntegrationTest


class RentalFacadeIT extends IntegrationTest{

    def "initial integration test"() {
        when: "define variable"
            var x = "Hello"
        then:
            x == "Hello"
    }
}
