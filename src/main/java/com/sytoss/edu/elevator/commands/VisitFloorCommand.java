package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;

@Component
@Slf4j
@RequiredArgsConstructor
public class VisitFloorCommand implements Command {

    private final ShaftRepository shaftRepository;

    @Override
    public void execute(HashMap<String, Object> params) {
        House house = (House) params.get(CommandManager.HOUSE_PARAM);
        Shaft shaft = (Shaft) params.get(SHAFT_PARAM);
        Floor floor = house.nextFloor(shaft.getCabinPosition());

        log.info("Shaft with id [{}] is on floor: [{}]", shaft.getId(), floor.getFloorNumber());
        shaftRepository.updateCabinPositionById(shaft.getId(), floor.getFloorNumber());
        shaft.setCabinPosition(house, floor.getFloorNumber());
    }
}
