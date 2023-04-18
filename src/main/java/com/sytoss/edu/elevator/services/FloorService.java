package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FloorService {

    private final House house;

    public void goUpCabinRequest (int floorNumber) {
        Floor floor = house.getFloors().get(floorNumber - 1);

        if (floor instanceof FloorWithUpButton) {
            //todo remove house.getId() egorBP
            //todo fix tests egorBP
            ((FloorWithUpButton) floor).pressUpButton(house.getId());
        }
    }
}
