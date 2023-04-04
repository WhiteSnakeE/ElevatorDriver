package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class ActivateShaftCommand implements Command {

    @Autowired
    private CommandManager commandManager;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        params.put("Direction", shaft.getSequenceOfStops().getDirection());

        if (shaft.getCabinPosition() == getLastFloorInSequence(shaft)) {
            log.info("Cabin with id {} in the same floor as requested floor", shaft.getCabin().getId());

            cabinManipulation(shaft, params);
            shaft.clearSequence();

            return;
        }

        log.info("Cabin with id {} start to move and now current floor is {} ", shaft.getCabin().getId(), shaft.getCabinPosition());
        int lastFloor = getLastFloorInSequence(shaft);

        while (shaft.getCabinPosition() <= lastFloor) {
            if (shaft.getSequenceOfStops() == null) {
                return;
            }

            if (shaft.getEngine().getEngineState() == EngineState.STAYING) {
                commandManager.getCommand(MOVE_CABIN_COMMAND).execute(params);
            }

            shaft.setCabinPosition(1 + shaft.getCabinPosition());
            log.info("Cabin with id {} on floor №{}", shaft.getCabin().getId(), shaft.getCabinPosition());

            if (shaft.getSequenceOfStops().getStopFloors().contains(shaft.getCabinPosition())) {
                commandManager.getCommand(STOP_CABIN_COMMAND).execute(params);
                log.info("Cabin with id {} stop", shaft.getCabin().getId());
                cabinManipulation(shaft, params);

                shaft.clearSequence();
            }
        }
    }

    private Integer getLastFloorInSequence (Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(shaft.getSequenceOfStops().getStopFloors().size() - 1);
    }

    private void cabinManipulation (Shaft shaft, HashMap<String, Object> params) {
        commandManager.getCommand(OPEN_DOOR_COMMAND).execute(params);
        log.info("Door in cabin with id {} was opened", shaft.getCabin().getId());

        if (!shaft.getCabin().isOverWeight()) {
            log.info("Cabin with id {} is not overweight", shaft.getCabin().getId());
        }

        commandManager.getCommand(CLOSE_DOOR_COMMAND).execute(params);
        log.info("Door in cabin with id {} was closed", shaft.getCabin().getId());
    }
}
