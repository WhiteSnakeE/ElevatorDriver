package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

public class ElevatorDriverTest {

    private final ElevatorDriver elevatorDriver = new ElevatorDriver();

    @Test
    public void addNewSequenceToOrderTest () {
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);
        Assertions.assertNotEquals(0, elevatorDriver.getOrderSequenceOfStops().size());
        Assertions.assertEquals(List.of(5), elevatorDriver.getOrderSequenceOfStops().get(0).getStopFloors());
        Assertions.assertEquals(Direction.UPWARDS, elevatorDriver.getOrderSequenceOfStops().get(0).getDirection());
    }

    @Test
    public void removeSequenceFromOrderTest () {
        elevatorDriver.getOrderSequenceOfStops().clear();
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);
        elevatorDriver.removeSequenceFromOrder();

        Assertions.assertEquals(0, elevatorDriver.getOrderSequenceOfStops().size());
    }

    @Test
    public void removeSequenceFromOrderFailedTest () {
        elevatorDriver.getOrderSequenceOfStops().clear();
        Assertions.assertThrows(IllegalStateException.class, elevatorDriver::removeSequenceFromOrder);
    }
}
