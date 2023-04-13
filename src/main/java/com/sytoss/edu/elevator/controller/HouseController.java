package com.sytoss.edu.elevator.controller;

import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.services.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/house")
public class HouseController {

    private final HouseService houseService;

    @PostMapping("/register")
    public void saveRequest(
            @RequestBody HouseDTO houseDTO) {
        houseService.saveRequest(houseDTO);
    }
}
