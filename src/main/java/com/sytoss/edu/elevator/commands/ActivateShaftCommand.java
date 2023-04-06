package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class ActivateShaftCommand implements Command {

    private final CommandManager commandManager;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        params.put("Direction", shaft.getSequenceOfStops().getDirection());

        if (shaft.getCabinPosition() == getLastFloor(shaft)) {
            commandManager.getCommand(Command.OPEN_DOOR_COMMAND).execute(params);
            commandManager.getCommand(Command.CLOSE_DOOR_COMMAND).execute(params);
            return;
        }

        log.info("Shaft with id [{}] activates on floor: [{}]", shaft.getId(), shaft.getCabinPosition());
        while (shaft.getCabinPosition() < getLastFloor(shaft)) {
            if (shaft.getEngine().getEngineState().equals(EngineState.STAYING)) {
                commandManager.getCommand(Command.MOVE_CABIN_COMMAND).execute(params);
            }

            shaft.setCabinPosition(shaft.getCabinPosition() + 1);
            log.info("Shaft with id [{}] is on floor №: [{}]", shaft.getId(), shaft.getCabinPosition());

            if (shaft.getSequenceOfStops().getStopFloors().contains(shaft.getCabinPosition())) {
                commandManager.getCommand(Command.STOP_CABIN_COMMAND).execute(params);
                commandManager.getCommand(Command.OPEN_DOOR_COMMAND).execute(params);
                commandManager.getCommand(Command.CLOSE_DOOR_COMMAND).execute(params);
            }
        }
        shaft.clearSequence();
    }

    private int getLastFloor(Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(shaft.getSequenceOfStops().getStopFloors().size() - 1);
    }
}
