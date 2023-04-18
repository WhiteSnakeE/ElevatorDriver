package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.ListIterator;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoveCabinCommand implements Command {

    private final CommandManager commandManager;
    private final House house;
    private final ShaftRepository shaftRepository;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        params.put("Direction", shaft.getSequenceOfStops().getDirection());
        ListIterator currentFloor = house.getFloors().listIterator();
        int startPosition = shaft.getCabinPosition();

        while (currentFloor.hasNext()) {
            Floor floor = (Floor) currentFloor.next();
            if (floor.getFloorNumber() == startPosition) {
                break;
            }
        }

        if (currentFloor.hasPrevious()) {
            currentFloor.previous();
        } else {
            return;
        }


        log.info("Shaft with id [{}] start process: [{}]", shaft.getId(), shaft.getCabinPosition());
        Floor floor = (Floor) currentFloor.next();
        if ((startPosition == getFirstFloor(shaft))) {
            commandManager.getCommand(OPEN_DOOR_COMMAND).execute(params);
            commandManager.getCommand(CLOSE_DOOR_COMMAND).execute(params);
            floor = (Floor) currentFloor.next();
        }


        while (currentFloor.hasNext() && floor.getFloorNumber() <= getLastFloor(shaft)) {
            if (shaft.getEngine().getEngineState().equals(EngineState.STAYING)) {
                commandManager.getCommand(START_ENGINE_COMMAND).execute(params);
            }

            shaft.setCabinPosition(floor.getFloorNumber());
            shaftRepository.updateCabinPositionById(shaft.getId(), floor.getFloorNumber());

            log.info("Shaft with id [{}] update cabin position in DB to: [{}]", shaft.getId(), floor.getFloorNumber());
            if (startPosition != floor.getFloorNumber()) {
                floor.visit(shaft);
            }


            if (shaft.getSequenceOfStops().getStopFloors().contains(shaft.getCabinPosition())) {
                commandManager.getCommand(STOP_ENGINE_COMMAND).execute(params);
                commandManager.getCommand(OPEN_DOOR_COMMAND).execute(params);
                commandManager.getCommand(CLOSE_DOOR_COMMAND).execute(params);

                if (getLastFloor(shaft) != shaft.getCabinPosition()) {
                    commandManager.getCommand(START_ENGINE_COMMAND).execute(params);
                }
            }
            floor = (Floor) currentFloor.next();
        }
        shaft.clearSequence();

        shaftRepository.updateSequenceById(shaft.getId(), null);
        log.info("Shaft with id [{}] end process on floor: [{}]", shaft.getId(), shaft.getCabinPosition());
    }


    private int getLastFloor (Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(shaft.getSequenceOfStops().getStopFloors().size() - 1);
    }

    private int getFirstFloor (Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(0);
    }
}

