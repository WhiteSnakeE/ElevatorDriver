package com.sytoss.edu.elevator.unit.controller;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.services.FloorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;

public class FloorControllerTest extends IntegrationTest {
    private ResponseEntity<Void> response;
    @SpyBean
    FloorService floorService;
    @Test
    public void goUpCabinRequestTest(){
        String url = "/api/floorButton/5/up";
        response=doPost(url,null,Void.class);
        verify(floorService).goUpCabinRequest(5);

        Assertions.assertEquals(200, response.getStatusCode().value());
    }

}
