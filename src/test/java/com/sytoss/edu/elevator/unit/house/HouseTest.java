package com.sytoss.edu.elevator.unit.house;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.verify;

public class HouseTest extends IntegrationTest {

    @Autowired
    private House house;

    @Test
    public void moveSequenceToShaftTest () {
        getElevatorDriver().addNewSequenceToOrder(7, Direction.UPWARDS);
        Shaft shaft = house.moveSequenceToShaft();

        Assertions.assertEquals(7, shaft.getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.UPWARDS, shaft.getSequenceOfStops().getDirection());
        Assertions.assertNull(house.getShafts().get(1).getSequenceOfStops());

        verify(getElevatorDriver()).removeSequenceFromOrder();
    }
}
