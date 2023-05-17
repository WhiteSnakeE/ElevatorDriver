package com.sytoss.edu.elevator.bom.house.buttons;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpFloorButton extends FloorButton {

    public UpFloorButton(Command command) {
        super(command);
    }

    public void press(House house, int floorNumber) {
        log.info("UpFloorButton: press floorNumber {} ", floorNumber);
        super.press(house, floorNumber, Direction.UPWARDS);
    }
}
