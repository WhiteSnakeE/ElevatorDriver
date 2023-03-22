package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.AbstractJunitTest;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.LiftDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.Mockito.verify;

public class LiftDriverTest extends AbstractJunitTest {

    @SpyBean
    private LiftDriver liftDriver;

    @SpyBean
    private FindNearestCabinCommand findNearestCabinCommand;

    @Test
    public void initTest () {
        Assertions.assertEquals(2, liftDriver.getShafts().size());
        Assertions.assertEquals(16, liftDriver.getFloors().size());
        Assertions.assertNotEquals(0, liftDriver.getCommandMap().size());
    }

    @Test
    public void registerCommandTest () {
        liftDriver.getCommandMap().clear();
        liftDriver.registerCommand("findNearestCabin", findNearestCabinCommand);
        Assertions.assertNotEquals(liftDriver.getCommandMap().size(), 0);

    }

    @Test
    public void executeCommandTest () {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.getStopFloors().add(5);
        sequence.setCurrentFloor(1);
        sequence.setDirection(Direction.UPWARDS);

        liftDriver.addSequenceToOrder(sequence);

        liftDriver.executeCommand("findNearestCabin", null);

        verify(findNearestCabinCommand).execute(null);
    }

    @Test
    public void runCommandsTest () {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.getStopFloors().add(5);
        sequence.setCurrentFloor(1);
        sequence.setDirection(Direction.UPWARDS);
        liftDriver.addSequenceToOrder(sequence);

        liftDriver.runCommands();
        verify(findNearestCabinCommand).execute(null);
    }

    @Test
    public void executeCommandFailed () {
        Assertions.assertThrows(IllegalStateException.class, () -> liftDriver.executeCommand("BadName", null));
    }

    @Test
    public void addSequenceToOrderTest () {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.getStopFloors().add(4);
        sequence.setCurrentFloor(10);
        sequence.setDirection(Direction.DOWNWARDS);

        liftDriver.addSequenceToOrder(sequence);
        Assertions.assertNotEquals(0, liftDriver.getOrderSequenceOfStops().size());
        Assertions.assertEquals(sequence, liftDriver.getOrderSequenceOfStops().get(0));
    }

    @Test
    public void removeSequenceFromOrderTest () {
        liftDriver.getOrderSequenceOfStops().clear();

        SequenceOfStops sequenceUpwards = new SequenceOfStops();
        sequenceUpwards.setDirection(Direction.UPWARDS);
        sequenceUpwards.getStopFloors().addAll(Arrays.asList(2, 7, 10));
        sequenceUpwards.setCurrentFloor(1);

        SequenceOfStops sequenceDownwards = new SequenceOfStops();
        sequenceDownwards.setDirection(Direction.DOWNWARDS);
        sequenceDownwards.getStopFloors().addAll(Arrays.asList(10, 9, 6));
        sequenceDownwards.setCurrentFloor(9);

        liftDriver.addSequenceToOrder(sequenceUpwards);
        liftDriver.addSequenceToOrder(sequenceDownwards);

        Assertions.assertEquals(2, liftDriver.getOrderSequenceOfStops().size());

        Assertions.assertEquals(Arrays.asList(2, 7, 10), liftDriver.getOrderSequenceOfStops().get(0).getStopFloors());

        Assertions.assertEquals(Arrays.asList(10, 9, 6), liftDriver.getOrderSequenceOfStops().get(1).getStopFloors());

        liftDriver.removeSequenceFromOrder(0);

        Assertions.assertEquals(1, liftDriver.getOrderSequenceOfStops().size());

        Assertions.assertEquals(Arrays.asList(10, 9, 6), liftDriver.getOrderSequenceOfStops().get(0).getStopFloors());
    }
}
