package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.floors.FirstFloor;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.bom.house.floors.MiddleFloor;
import com.sytoss.edu.elevator.services.FloorService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FloorServiceTest {
    @SpyBean
    @Autowired
    private House house;
    @Mock
    private FirstFloor firstFloor;
    @Mock
    private MiddleFloor middleFloor;
    @Autowired
    private FloorService floorService;

    @Test
    public void goUpCabinRequestToFirstFloorTest () {
        when(house.getFloors()).thenReturn(List.of(firstFloor));
        floorService.goUpCabinRequest(1);
        verify(firstFloor).pressUpButton();
    }

    @Test
    public void goUpCabinRequestToMiddleFloorTest () {
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
