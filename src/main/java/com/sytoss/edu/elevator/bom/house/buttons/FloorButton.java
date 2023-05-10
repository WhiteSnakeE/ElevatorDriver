package com.sytoss.edu.elevator.bom.house.buttons;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;

import java.util.HashMap;

public abstract class FloorButton extends CommandButton {

    public FloorButton(Command command) {
        super(command);
    }

    public void press(House house,int floorNumber, Direction direction) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(CommandManager.FLOOR_NUMBER_PARAM, floorNumber);
        params.put(CommandManager.DIRECTION_PARAM, direction);
        params.put(CommandManager.HOUSE_PARAM,house);
        execute(params);
    }
}
