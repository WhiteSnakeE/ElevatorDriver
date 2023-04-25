package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.ListIterator;

import static com.sytoss.edu.elevator.commands.CommandManager.ITERATOR_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;

@Component
public class VisitFloorCommand implements Command {

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(SHAFT_PARAM);
        ListIterator floors = (ListIterator) params.get(ITERATOR_PARAM);
        Floor currentFloor = (Floor) floors.next();

        shaft.setCabinPosition(currentFloor.getFloorNumber());
    }
}
