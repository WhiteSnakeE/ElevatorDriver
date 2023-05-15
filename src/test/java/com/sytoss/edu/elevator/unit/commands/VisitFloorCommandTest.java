package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.commands.VisitFloorCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.HouseService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.sytoss.edu.elevator.commands.CommandManager.HOUSE_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static org.mockito.Mockito.*;

public class VisitFloorCommandTest {

    private final House house = mock(House.class);

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final HouseService houseService = mock(HouseService.class);

    private final VisitFloorCommand visitFloorCommand = new VisitFloorCommand(shaftRepository, houseService);

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        Floor floor = mock(Floor.class);

        HashMap<String, Object> params = new HashMap<>();
        params.put(SHAFT_PARAM, shaft);

        when(shaft.getId()).thenReturn(2L);
        when(houseService.getHouseByShaftId(shaft.getId())).thenReturn(house);
        when(house.nextFloor(shaft.getCabinPosition())).thenReturn(floor);
        when(floor.getFloorNumber()).thenReturn(3);

        visitFloorCommand.execute(params);

        verify(shaftRepository).updateCabinPositionById(2L, floor.getFloorNumber());
        verify(shaft).setCabinPosition(floor.getFloorNumber());
    }
}
