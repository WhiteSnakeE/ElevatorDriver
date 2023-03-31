package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
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

            commandManager.getCommand("OpenDoorCommand").execute(params);
            log.info("Door in cabin with id {} was opened", shaft.getCabin().getId());

            if (!shaft.getCabin().isOverWeight()) {
                log.info("Cabin with id {} is not overweight", shaft.getCabin().getId());
            } else {
                //TODO Aleksandr Pobizinsky
            }

            commandManager.getCommand("CloseDoorCommand").execute(params);
            log.info("Door in cabin with id {} was closed", shaft.getCabin().getId());
            shaft.clearSequence();

            return;
        }

        log.info("Cabin with id {} start to move and now current floor is {} ", shaft.getCabin().getId(), shaft.getCabinPosition());
        int lastFloor=getLastFloorInSequence(shaft);
        while (shaft.getCabinPosition() !=lastFloor) {

            commandManager.getCommand("MoveCabinCommand").execute(params);
            shaft.setCabinPosition(1 + shaft.getCabinPosition());
            log.info("Cabin with id {} on floor №{}", shaft.getCabin().getId(), shaft.getCabinPosition());
            if (shaft.getCabinPosition() == getFirstFloorInSequence(shaft)) {
                commandManager.getCommand("StopCabinCommand").execute(params);
                log.info("Cabin with id {} stop", shaft.getCabin().getId());

                commandManager.getCommand("OpenDoorCommand").execute(params);
                log.info("Door in cabin with id {} was opened", shaft.getCabin().getId());

                if (!shaft.getCabin().isOverWeight()) {
                    log.info("Cabin with id {} is not overweight", shaft.getCabin().getId());
                } else {
                    //TODO Aleksandr Pobizinsky
                }

                commandManager.getCommand("CloseDoorCommand").execute(params);
                log.info("Door in cabin with id {} was closed", shaft.getCabin().getId());

                shaft.clearSequence();
            }
        }
    }

    private Integer getLastFloorInSequence (Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(shaft.getSequenceOfStops().getStopFloors().size() - 1);
    }

    private Integer getFirstFloorInSequence (Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(0);
    }
}
