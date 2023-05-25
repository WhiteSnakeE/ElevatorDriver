package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.services.HouseService;
import com.sytoss.edu.elevator.services.ShaftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;

@Component
@Slf4j
@RequiredArgsConstructor
public class VisitFloorCommand implements Command {

    private final ShaftService shaftService;

    private final HouseService houseService;

    @Override
    public void execute(HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get(SHAFT_PARAM);
        House house = houseService.getHouseByShaftId(shaft.getId());
        Floor floor = house.nextFloor(shaft.getCabinPosition());
        log.info("Shaft with id [{}] is on floor: [{}]", shaft.getId(), floor.getFloorNumber());
        shaftService.updateCabinPositionById(shaft.getId(), floor.getFloorNumber());
        shaft.setCabinPosition(floor.getFloorNumber());
    }
}
