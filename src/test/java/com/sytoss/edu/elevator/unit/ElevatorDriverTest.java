package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.Mockito.verify;

@SpringBootTest
public class ElevatorDriverTest {

    @SpyBean
    @Autowired
    private ElevatorDriver elevatorDriver;

    @Test
    public void addNewSequenceToOrderTest () {
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);
        Assertions.assertNotEquals(0, elevatorDriver.getOrderSequenceOfStops().size());
        Assertions.assertEquals(List.of(5), elevatorDriver.getOrderSequenceOfStops().get(0).getStopFloors());
        Assertions.assertEquals(Direction.UPWARDS, elevatorDriver.getOrderSequenceOfStops().get(0).getDirection());

        verify(elevatorDriver).runCommands();
    }

    @Test
    public void removeSequenceFromOrderTest () {
        elevatorDriver.getOrderSequenceOfStops().clear();
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);
        elevatorDriver.removeSequenceFromOrder();

        Assertions.assertEquals(0, elevatorDriver.getOrderSequenceOfStops().size());
    }
}
