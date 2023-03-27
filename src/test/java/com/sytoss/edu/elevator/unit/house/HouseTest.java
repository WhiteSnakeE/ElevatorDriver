package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class HouseTest {

    @Autowired
    private House house;
    @SpyBean
    @Autowired
    private ElevatorDriver elevatorDriver;

    @Test
    public void moveSequenceToShaftTest () {
        elevatorDriver.addNewSequenceToOrder(7, Direction.UPWARDS);
        Shaft shaft = house.moveSequenceToShaft(elevatorDriver);

        Assertions.assertEquals(7, shaft.getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.UPWARDS, shaft.getSequenceOfStops().getDirection());
        Assertions.assertNull(house.getShafts().get(1).getSequenceOfStops());

        verify(elevatorDriver).removeSequenceFromOrder();
    }
}
