package com.sytoss.edu.elevator.controller;

import com.sytoss.edu.elevator.services.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FloorController {
    private final FloorService floorService;

    @PostMapping("/floorButton/{floorNumber}/up")
    public void goUpCabinRequest (@PathVariable(value = "floorNumber") int floorNumber) {
        floorService.goUpCabinRequest(floorNumber);
    }
}
