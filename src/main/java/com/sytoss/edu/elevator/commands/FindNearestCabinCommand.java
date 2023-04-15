package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
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

    private final ShaftRepository shaftRepository;

    private final HouseRepository houseRepository;

    private final HouseConverter houseConverter;

    private final ShaftConverter shaftConverter;


    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = house.moveSequenceToShaft(
                elevatorDriver, shaftRepository, houseRepository,
                houseConverter, shaftConverter
        );

        if (shaft == null) {
            return;
        }

        HashMap<String, Object> paramsActivateCommand = new HashMap<>();
        paramsActivateCommand.put("Shaft", shaft);
        commandManager.getCommand(MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
    }
}
