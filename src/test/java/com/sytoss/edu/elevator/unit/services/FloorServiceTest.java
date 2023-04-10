package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.bom.house.floors.MiddleFloor;
import com.sytoss.edu.elevator.services.FloorService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class FloorServiceTest {

    private final House house = mock(House.class);
    private final FloorService floorService = new FloorService(house);

    @Test
    public void goUpCabinRequestToFirstFloorTest () {
        FirstFloor firstFloor = mock(FirstFloor.class);

        when(house.getFloors()).thenReturn(List.of(firstFloor));
        floorService.goUpCabinRequest(1);
        verify(firstFloor).pressUpButton();
    }

    @Test
    public void goUpCabinRequestToMiddleFloorTest () {
        FirstFloor firstFloor = mock(FirstFloor.class);
        MiddleFloor middleFloor = mock(MiddleFloor.class);

        List<Floor> list = new ArrayList<>();
        list.add(firstFloor);
        for (int i = 2; i <= 4; ++i) {
            list.add(middleFloor);
        }
        when(house.getFloors()).thenReturn(list);
        floorService.goUpCabinRequest(2);
        verify(middleFloor).pressUpButton();
    }
}
