package com.sytoss.edu.elevator.unit.controller;

import com.sytoss.edu.elevator.services.FloorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FloorControllerTest {

    @LocalServerPort
    private int applicationPort;

    private String getBaseUrl () {
        return "http://localhost:" + applicationPort;
    }
    private ResponseEntity<Void> response;
    @SpyBean
    @Autowired
    private FloorService floorService;

    @Test
    public void goUpCabinRequestTest () {
        String url = getBaseUrl() + "/api/floorButton/5/up";
        response = new TestRestTemplate().exchange(url, HttpMethod.POST, null, Void.class);
        verify(floorService).goUpCabinRequest(5);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }
}
