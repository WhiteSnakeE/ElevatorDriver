package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.services.HouseService;
import com.sytoss.edu.elevator.services.ShaftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class FindNearestCabinCommand implements Command {

    private final CommandManager commandManager;

    private final HouseThreadPool houseThreadPool;

    private final ShaftService shaftService;

    private final HouseService houseService;

    @Override
    public void execute(HashMap<String, Object> params) {
        House house = (House) params.get(CommandManager.HOUSE_PARAM);
        houseService.updateOrderById(house.getId(), house.getElevatorDriver().getOrderSequenceOfStops());
        Shaft nearestCabin = house.findNearestCabin();

        if (nearestCabin == null) {
            return;
        }

        if (nearestCabin.isCabinMoving()) {
            nearestCabin.getSequenceOfStops().addSequenceOfStopsListener(shaftService);
            updateSequences(house, nearestCabin);
            return;
        }

        updateSequences(house, nearestCabin);

        if (nearestCabin.getCabinPosition() > nearestCabin.getSequenceOfStops().getStopFloors().get(0)) {
            log.info("There is must be moving down");
            nearestCabin.clearSequence();
            shaftService.updateSequenceById(nearestCabin.getId(), nearestCabin.getSequenceOfStops());
            return;
        }

        if (nearestCabin.getSequenceOfStops().isFirst(nearestCabin.getCabinPosition())) {
            houseThreadPool.getFixedThreadPool().submit(() -> {
                params.put(CommandManager.SHAFT_PARAM, nearestCabin);
                log.info("[FindNearestCabin] start OpenDoorCommand for shaft with id {}", nearestCabin.getId());
                commandManager.getCommand(OPEN_DOOR_COMMAND).execute(params);
                log.info("[FindNearestCabin] finish OpenDoorCommand for shaft with id {}", nearestCabin.getId());
            });
            return;
        }

        houseThreadPool.getFixedThreadPool().submit(() -> {
            params.put(CommandManager.SHAFT_PARAM, nearestCabin);
            log.info("startMoveCabin: start threads for shaft with id {}", nearestCabin.getId());
            commandManager.getCommand(MOVE_CABIN_COMMAND).execute(params);
            log.info("startMoveCabin: finish threads for shaft with id {}", nearestCabin.getId());
        });
    }

    private void updateSequences(House house, Shaft nearestCabin) {
        nearestCabin.updateSequence(house.getElevatorDriver().getOrderSequenceOfStops());
        house.removeSequenceFromOrder();
        houseService.updateOrderById(house.getId(), house.getElevatorDriver().getOrderSequenceOfStops());
    }
}
