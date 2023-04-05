package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
public class FindNearestCabinCommand implements Command {

    @Autowired
    private House house;
    @Autowired
    private ElevatorDriver elevatorDriver;
    @Autowired
    private CommandManager commandManager;

    private int counter = 0;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = house.moveSequenceToShaft(elevatorDriver.getOrderSequenceOfStops());


        if (shaft == null) {
            return;
        }

        HashMap<String, Object> paramsActivateCommand = new HashMap<>();
        paramsActivateCommand.put("Shaft", shaft);
        commandManager.getCommand("ActivateShaftCommand").execute(paramsActivateCommand);

    }
}
