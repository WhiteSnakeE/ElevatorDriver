package com.sytoss.edu.elevator.bom.house;

import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.buttons.UpFloorButton;
import com.sytoss.edu.elevator.bom.house.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.house.floors.MiddleFloor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HouseBuilder {
    @Autowired
    private CommandManager commandManager;
    @Autowired
    private ElevatorDriver elevatorDriver;

    public House build(int shaftCount, int floorCount) {
        House result = new House();
        result.setElevatorDriver(elevatorDriver);
        for (int i = 0; i < shaftCount; i++) {
            result.getShafts().add(new Shaft());
        }

        result.getFloors().add(new FirstFloor(new UpFloorButton(commandManager.getCommand(CommandManager.PRESS_UP_BUTTON))));
        for (int i = 1; i < floorCount; i++) {
            int floorNumber = i + 1;
            result.getFloors().add(new MiddleFloor(floorNumber, new UpFloorButton(commandManager.getCommand(CommandManager.PRESS_UP_BUTTON))));
        }
        return result;
    }
}
