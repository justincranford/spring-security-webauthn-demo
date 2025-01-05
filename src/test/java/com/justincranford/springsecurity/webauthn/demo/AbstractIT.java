package com.justincranford.springsecurity.webauthn.demo;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT, classes={ })
@ActiveProfiles({"test"})
public abstract class AbstractIT {
}
