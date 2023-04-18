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
        //todo fix tests egorBP
        Shaft nearestCabin = house.findNearestCabin(elevatorDriver.getOrderSequenceOfStops());

        if (nearestCabin == null) {
            return;
        }

        boolean isNeedActivate = nearestCabin.updateSequence(elevatorDriver);

        String sequenceOfStops = shaftConverter.sequenceToStringInJSON(nearestCabin.getSequenceOfStops());
        shaftRepository.updateSequenceById(nearestCabin.getId(), sequenceOfStops);

        String orderSequenceOfStops = houseConverter.orderSequenceToStringInJSON(
                elevatorDriver.getOrderSequenceOfStops()
        );
        houseRepository.updateOrderById(house.getId(), orderSequenceOfStops);

        if (!isNeedActivate) {
            return;
        }

        HashMap<String, Object> paramsActivateCommand = new HashMap<>();
        paramsActivateCommand.put("Shaft", nearestCabin);
        commandManager.getCommand(MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
    }
}
