package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.utils.JsonUtil;
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

    private final HouseThreadPool houseThreadPool;

    @Override
    public void execute(HashMap<String, Object> params) {
        houseRepository.updateOrderById(house.getId(), JsonUtil.orderSequenceToStringInJSON(elevatorDriver.getOrderSequenceOfStops()));
        Shaft nearestCabin = house.findNearestCabin(elevatorDriver.getOrderSequenceOfStops());

        if (nearestCabin == null) {
            return;
        }

        if (nearestCabin.isCabinMoving()) {
            updateSequences(nearestCabin);
            return;
        }

        updateSequences(nearestCabin);

        houseThreadPool.getFixedThreadPool().submit(() -> {
            log.info("startMoveCabin: start threads for shaft with id {}", nearestCabin.getId());
            HashMap<String, Object> paramsActivateCommand = new HashMap<>();
            paramsActivateCommand.put(CommandManager.SHAFT_PARAM, nearestCabin);
            paramsActivateCommand.put(CommandManager.FLOORS_PARAM, house.getFloors());
            commandManager.getCommand(MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
            log.info("startMoveCabin: finish threads for shaft with id {}", nearestCabin.getId());
        });
    }

    private void updateSequences(Shaft nearestCabin) {
        nearestCabin.updateSequence(elevatorDriver);

        String sequenceOfStops = JsonUtil.sequenceToStringInJSON(nearestCabin.getSequenceOfStops());
        shaftRepository.updateSequenceById(nearestCabin.getId(), sequenceOfStops);

        String orderSequenceOfStops = JsonUtil.orderSequenceToStringInJSON(elevatorDriver.getOrderSequenceOfStops());
        houseRepository.updateOrderById(house.getId(), orderSequenceOfStops);
    }
}
