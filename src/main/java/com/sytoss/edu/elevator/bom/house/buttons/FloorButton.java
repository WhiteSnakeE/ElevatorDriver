package com.sytoss.edu.elevator.bom.house.buttons;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.commands.Command;

import java.util.HashMap;

public abstract class FloorButton extends CommandButton {
    public FloorButton (Command command) {
        super(command);
    }

    public void press (int floorNumber, Direction direction) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("numberFloor", floorNumber);
        params.put("Direction", direction);
        execute(params);
    }
}
