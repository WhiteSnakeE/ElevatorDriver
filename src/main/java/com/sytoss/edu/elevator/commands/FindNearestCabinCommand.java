package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class FindNearestCabinCommand implements Command {

    private final House house;

    private final ElevatorDriver elevatorDriver;

    private final CommandManager commandManager;


    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = house.moveSequenceToShaft(elevatorDriver);

        if (shaft == null) {
            return;
        }

        HashMap<String, Object> paramsActivateCommand = new HashMap<>();
        paramsActivateCommand.put("Shaft", shaft);
        commandManager.getCommand(MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
    }
}
