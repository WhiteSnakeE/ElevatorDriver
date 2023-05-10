package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.commands.VisitFloorCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static org.mockito.Mockito.*;

public class VisitFloorCommandTest {

    private final House house = mock(House.class);

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final VisitFloorCommand visitFloorCommand = new VisitFloorCommand(shaftRepository, house);

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        Floor floor = mock(Floor.class);

        HashMap<String, Object> params = new HashMap<>();
        params.put(SHAFT_PARAM, shaft);

        when(house.nextFloor(shaft.getCabinPosition())).thenReturn(floor);
        when(floor.getFloorNumber()).thenReturn(3);

        visitFloorCommand.execute(params);

        verify(shaftRepository).updateCabinPositionById(0L, floor.getFloorNumber());
        verify(shaft).setCabinPosition(floor.getFloorNumber());
    }
}
