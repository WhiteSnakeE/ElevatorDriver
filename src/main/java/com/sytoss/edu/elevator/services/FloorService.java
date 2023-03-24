package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FloorService {
    @Autowired
    private House house;

    public void goUpCabinRequest (int floorNumber) {
        Floor floor = house.getFloors().get(floorNumber - 1);

        if (floor instanceof FloorWithUpButton) {
            ((FloorWithUpButton) floor).pressUpButton();
        }
    }
}
