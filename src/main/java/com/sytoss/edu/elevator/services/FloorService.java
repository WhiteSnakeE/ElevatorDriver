package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class FloorService {
    private final Controller controller;
    public void goUpCabinRequest(HashMap<String, String>params) {
        controller.runCommands("findNearestCabin", params);
    }
}
