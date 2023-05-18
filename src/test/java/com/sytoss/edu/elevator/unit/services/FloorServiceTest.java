package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.bom.house.floors.MiddleFloor;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.services.FloorService;
import com.sytoss.edu.elevator.services.HouseService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class FloorServiceTest {

    private final House house = mock(House.class);

    private final HouseDTO houseDTO = mock(HouseDTO.class);

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    private final HouseService houseService = mock(HouseService.class);

    private final FloorService floorService = new FloorService(houseService);


    @Test
    public void goUpCabinRequestToFirstFloorTest() {
        FirstFloor firstFloor = mock(FirstFloor.class);
        when(houseService.getHouseById(1)).thenReturn(house);
        when(houseService.getHouseDTO(1)).thenReturn(houseDTO);
        when(house.getElevatorDriver()).thenReturn(elevatorDriver);
        when(house.getFloors()).thenReturn(List.of(firstFloor));
        when(houseDTO.getOrderSequenceOfStops()).thenReturn("");
        floorService.goUpCabinRequest(1, 1);
        verify(firstFloor).pressUpButton();
    }

    @Test
    public void goUpCabinRequestToMiddleFloorTest() {
        FirstFloor firstFloor = mock(FirstFloor.class);
        MiddleFloor middleFloor = mock(MiddleFloor.class);

        List<Floor> list = new ArrayList<>();
        list.add(firstFloor);
        for (int i = 2; i <= 4; ++i) {
            list.add(middleFloor);
        }
        when(houseService.getHouseById(1)).thenReturn(house);
        when(houseService.getHouseDTO(1)).thenReturn(houseDTO);
        when(house.getElevatorDriver()).thenReturn(elevatorDriver);
        when(house.getFloors()).thenReturn(list);
        floorService.goUpCabinRequest(1, 2);
        verify(middleFloor).pressUpButton();
    }
}
