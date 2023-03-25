package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.enums.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.verify;

public class ElevatorDriverTest extends IntegrationTest  {

    @Test
    public void addNewSequenceToOrderTest () {
        getElevatorDriver().addNewSequenceToOrder(5, Direction.UPWARDS);
        Assertions.assertNotEquals(0, getElevatorDriver().getOrderSequenceOfStops().size());
        Assertions.assertEquals(List.of(5), getElevatorDriver().getOrderSequenceOfStops().get(0).getStopFloors());
        Assertions.assertEquals(Direction.UPWARDS, getElevatorDriver().getOrderSequenceOfStops().get(0).getDirection());

        verify(getElevatorDriver()).runCommands();
    }

    @Test
    public void removeSequenceFromOrderTest () {
        getElevatorDriver().getOrderSequenceOfStops().clear();
        getElevatorDriver().addNewSequenceToOrder(5, Direction.UPWARDS);
        getElevatorDriver().removeSequenceFromOrder();

        Assertions.assertEquals(0, getElevatorDriver().getOrderSequenceOfStops().size());
    }
}
