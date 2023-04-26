package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
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

        if (nearestCabin.getSequenceOfStops().isFirst(nearestCabin.getCabinPosition())) {
            houseThreadPool.getFixedThreadPool().submit(() -> {
                HashMap<String, Object> paramsActivateCommand = new HashMap<>();
                paramsActivateCommand.put(CommandManager.SHAFT_PARAM, nearestCabin);
                paramsActivateCommand.put(CommandManager.ITERATOR_PARAM, house.getFloors().listIterator());
                log.info("[FindNearestCabin] start OpenDoorCommand for shaft with id {}", nearestCabin.getId());
                commandManager.getCommand(OPEN_DOOR_COMMAND).execute(paramsActivateCommand);
                log.info("[FindNearestCabin] finish OpenDoorCommand for shaft with id {}", nearestCabin.getId());
            });
            return;
        }

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
