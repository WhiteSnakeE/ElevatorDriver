package com.sytoss.edu.elevator.controller;

import com.sytoss.edu.elevator.services.FloorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
