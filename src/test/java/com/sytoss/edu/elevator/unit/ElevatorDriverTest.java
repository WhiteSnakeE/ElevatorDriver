package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.AbstractJunitTest;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class ElevatorDriverTest extends AbstractJunitTest {

    @SpyBean
    private ElevatorDriver elevatorDriver;

    @SpyBean
    private FindNearestCabinCommand findNearestCabinCommand;

    @Test
    public void initTest () {
        Assertions.assertEquals(2, elevatorDriver.getShafts().size());
        Assertions.assertEquals(16, elevatorDriver.getFloors().size());
        Assertions.assertNotEquals(0, elevatorDriver.getCommandMap().size());
    }

    @Test
    public void registerCommandTest () {
        elevatorDriver.getCommandMap().clear();
        elevatorDriver.registerCommand("findNearestCabin", findNearestCabinCommand);
        Assertions.assertNotEquals(elevatorDriver.getCommandMap().size(), 0);

    }

    @Test
    public void executeCommandTest () {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.getStopFloors().add(5);
        sequence.setCurrentFloor(1);
        sequence.setDirection(Direction.UPWARDS);

        elevatorDriver.addSequenceToOrder(sequence);

        elevatorDriver.executeCommand("findNearestCabin", null);

        verify(findNearestCabinCommand).execute(null);
    }

    @Test
    public void runCommandsTest () {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.getStopFloors().add(5);
        sequence.setCurrentFloor(1);
        sequence.setDirection(Direction.UPWARDS);
        elevatorDriver.addSequenceToOrder(sequence);

        elevatorDriver.runCommands();
        verify(findNearestCabinCommand).execute(null);
    }

    @Test
    public void executeCommandFailed () {
        Assertions.assertThrows(IllegalStateException.class, () -> elevatorDriver.executeCommand("BadName", null));
    }

    @Test
    public void addSequenceToOrderTest () {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.getStopFloors().add(4);
        sequence.setCurrentFloor(10);
        sequence.setDirection(Direction.DOWNWARDS);

        elevatorDriver.addSequenceToOrder(sequence);
        Assertions.assertNotEquals(0, elevatorDriver.getOrderSequenceOfStops().size());
        Assertions.assertEquals(sequence, elevatorDriver.getOrderSequenceOfStops().get(0));
    }

    @Test
    public void removeSequenceFromOrderTest () {
        elevatorDriver.getOrderSequenceOfStops().clear();

        SequenceOfStops sequenceUpwards = new SequenceOfStops();
        sequenceUpwards.setDirection(Direction.UPWARDS);
        sequenceUpwards.getStopFloors().addAll(Arrays.asList(2, 7, 10));
        sequenceUpwards.setCurrentFloor(1);

        SequenceOfStops sequenceDownwards = new SequenceOfStops();
        sequenceDownwards.setDirection(Direction.DOWNWARDS);
        sequenceDownwards.getStopFloors().addAll(Arrays.asList(10, 9, 6));
        sequenceDownwards.setCurrentFloor(9);

        elevatorDriver.addSequenceToOrder(sequenceUpwards);
        elevatorDriver.addSequenceToOrder(sequenceDownwards);

        Assertions.assertEquals(2, elevatorDriver.getOrderSequenceOfStops().size());

        Assertions.assertEquals(Arrays.asList(2, 7, 10), elevatorDriver.getOrderSequenceOfStops().get(0).getStopFloors());

        Assertions.assertEquals(Arrays.asList(10, 9, 6), elevatorDriver.getOrderSequenceOfStops().get(1).getStopFloors());

        elevatorDriver.removeSequenceFromOrder(0);

        Assertions.assertEquals(1, elevatorDriver.getOrderSequenceOfStops().size());

        Assertions.assertEquals(Arrays.asList(10, 9, 6), elevatorDriver.getOrderSequenceOfStops().get(0).getStopFloors());
    }
}
