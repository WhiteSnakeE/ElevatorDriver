package com.sytoss.edu.elevator.unitTests;

import com.sytoss.edu.elevator.ControllerIntegrationTest;
import com.sytoss.edu.elevator.controller.SayHelloController;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ControllerTest extends ControllerIntegrationTest {
    ResponseEntity<String> response;

    @Test
    public void testHelloWorld () {
        SayHelloController controller = new SayHelloController();
        Assertions.assertEquals("Hello World!", controller.sayHello());

        String url = getBaseUrl() + "/api/helloWorld";
        response = new TestRestTemplate().exchange(url, HttpMethod.GET, null, String.class);
        Assertions.assertEquals(HttpStatus.valueOf(200), response.getStatusCode());

    }
}