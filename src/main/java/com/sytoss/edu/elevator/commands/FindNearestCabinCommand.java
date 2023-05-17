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
        Shaft nearestCabin = house.findNearestCabin(house.getElevatorDriver().getOrderSequenceOfStops());

        if (nearestCabin == null) {
            return;
        }

        if (nearestCabin.isCabinMoving()) {
            updateSequences(house, nearestCabin);
            return;
        }

        updateSequences(house, nearestCabin);


        if (nearestCabin.getCabinPosition() > nearestCabin.getSequenceOfStops().getStopFloors().get(0)) {
            nearestCabin.clearSequence();
            log.info("FINDNEARESTCABINCOMMAND {}", nearestCabin.getSequenceOfStops());
            shaftService.updateSequenceById(nearestCabin.getId(), nearestCabin.getSequenceOfStops());
            return;
        }

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
            paramsActivateCommand.put(CommandManager.HOUSE_PARAM, house);
            commandManager.getCommand(MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
            log.info("startMoveCabin: finish threads for shaft with id {}", nearestCabin.getId());
        });
    }

    private void updateSequences(House house, Shaft nearestCabin) {
        nearestCabin.updateSequence(house.getElevatorDriver());
        log.info("updateSequences {}", nearestCabin.getSequenceOfStops());
        shaftService.updateSequenceById(nearestCabin.getId(), nearestCabin.getSequenceOfStops());
        houseService.updateOrderById(house.getId(), house.getElevatorDriver().getOrderSequenceOfStops());
    }
}
