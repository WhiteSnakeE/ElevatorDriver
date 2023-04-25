package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.ListIterator;

import static com.sytoss.edu.elevator.commands.CommandManager.ITERATOR_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;

@Component
@Slf4j
@RequiredArgsConstructor
public class VisitFloorCommand implements Command {

    private final ShaftRepository shaftRepository;

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(SHAFT_PARAM);
        ListIterator floors = (ListIterator) params.get(ITERATOR_PARAM);
        Floor currentFloor = (Floor) floors.next();

        log.info("Shaft with id [{}] is on floor: [{}]", shaft.getId(), currentFloor.getFloorNumber());
        shaftRepository.updateCabinPositionById(shaft.getId(), currentFloor.getFloorNumber());
        shaft.setCabinPosition(currentFloor.getFloorNumber(), floors);
    }
}
