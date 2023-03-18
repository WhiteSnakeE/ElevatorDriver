package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.Controller;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.floors.Floor;
import com.sytoss.edu.elevator.bom.floors.MiddleFloor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FloorService {
    @Autowired
    private Controller controller;

    public void goUpCabinRequest (int floorNumber) {
        Floor floor = controller.getFloors().get(floorNumber - 1);

        HashMap<String, Object> params = new HashMap<>();
        params.put("floorNumber", floorNumber);
        params.put("direction", Direction.UPWARDS);

        if (floorNumber == 1) {
            ((FirstFloor) floor).pressUpButton();
        } else {
            ((MiddleFloor) floor).pressUpButton();
        }

        controller.runCommands("findNearestCabin", params);
    }
}
