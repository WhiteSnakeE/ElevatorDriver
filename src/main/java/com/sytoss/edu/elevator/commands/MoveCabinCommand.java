package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import static com.sytoss.edu.elevator.commands.CommandManager.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MoveCabinCommand implements Command {

    private final CommandManager commandManager;
    private final ShaftRepository shaftRepository;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(SHAFT_PARAM);
        params.put(CommandManager.DIRECTION_PARAM, shaft.getSequenceOfStops().getDirection());
        List<Floor> floorList = (List<Floor>)params.get("Floors");
        ListIterator currentFloor = floorList.listIterator();
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

        currentFloor.next();
        HashMap<String, Object> paramsActivateCommand = new HashMap<>();
        paramsActivateCommand.put(SHAFT_PARAM, shaft);
        paramsActivateCommand.put(ITERATOR_PARAM, currentFloor);
        paramsActivateCommand.put(DIRECTION_PARAM, Direction.UPWARDS);
        commandManager.getCommand(START_ENGINE_COMMAND).execute(paramsActivateCommand);
        commandManager.getCommand(VISIT_FLOOR_COMMAND).execute(paramsActivateCommand);
    }

    private int getLastFloor (Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(shaft.getSequenceOfStops().getStopFloors().size() - 1);
    }

    private int getFirstFloor (Shaft shaft) {
        return shaft.getSequenceOfStops().getStopFloors().get(0);
    }
}

