package com.sytoss.edu.elevator.bom.house.floors;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.house.buttons.UpFloorButton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FirstFloor extends Floor implements FloorWithUpButton {

    private final UpFloorButton upFloorButton;

    public FirstFloor(House house,UpFloorButton upFloorButton) {
        super(house,1);
        this.upFloorButton = upFloorButton;
    }

    @Override
    public void pressUpButton(House house) {
        log.info("FirstFloor: pressUpButton ");
        upFloorButton.press(house, getFloorNumber());
    }
}
