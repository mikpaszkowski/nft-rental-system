package com.rentalSystem.xrpl

import com.rentalSystem.XrplRentalSystem
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = XrplRentalSystem.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class IntegrationTest extends Specification{
}
