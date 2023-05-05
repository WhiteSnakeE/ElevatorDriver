package com.sytoss.edu.elevator.unit;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.commands.*;
import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;
import com.sytoss.edu.elevator.events.DoorStateChangedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Executors;

import static com.sytoss.edu.elevator.HouseThreadPool.await;
import static com.sytoss.edu.elevator.commands.Command.*;
import static org.mockito.Mockito.*;

public class ElevatorDriverTest {

    private final CommandManager commandManager = mock(CommandManager.class);
    private final HouseThreadPool houseThreadPool = mock(HouseThreadPool.class);
    private final ElevatorDriver elevatorDriver = new ElevatorDriver(commandManager, houseThreadPool);

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

    @Test
    public void handleCabinPositionChangedIsStopTest() {
        CabinPositionChangedEvent event = mock(CabinPositionChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        SequenceOfStops sequence = mock(SequenceOfStops.class);
        StopEngineCommand stopEngineCommand = mock(StopEngineCommand.class);
        OpenDoorCommand openDoorCommand = mock(OpenDoorCommand.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);
        when(sequence.getStopFloors()).thenReturn(List.of(2));
        when(shaft.getCabinPosition()).thenReturn(2);
        when(commandManager.getCommand(STOP_ENGINE_COMMAND)).thenReturn(stopEngineCommand);
        when(commandManager.getCommand(OPEN_DOOR_COMMAND)).thenReturn(openDoorCommand);
        when(houseThreadPool.getFixedThreadPool()).thenReturn(Executors.newScheduledThreadPool(4));

        elevatorDriver.handleCabinPositionChanged(event);
        await();

        verify(openDoorCommand).execute(any());
        verify(stopEngineCommand).execute(any());
    }

    @Test
    public void handleCabinPositionChangedTest() {
        CabinPositionChangedEvent event = mock(CabinPositionChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        SequenceOfStops sequence = mock(SequenceOfStops.class);
        VisitFloorCommand visitFloorCommand = mock(VisitFloorCommand.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);
        when(sequence.getStopFloors()).thenReturn(List.of(2));
        when(shaft.getCabinPosition()).thenReturn(1);
        when(commandManager.getCommand(VISIT_FLOOR_COMMAND)).thenReturn(visitFloorCommand);
        when(houseThreadPool.getFixedThreadPool()).thenReturn(Executors.newScheduledThreadPool(4));

        elevatorDriver.handleCabinPositionChanged(event);
        await();

        verify(visitFloorCommand).execute(any());
    }

    @Test
    public void handleDoorStateChangedToOpened() {
        DoorStateChangedEvent event = mock(DoorStateChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        Cabin cabin = mock(Cabin.class);
        CloseDoorCommand closeDoorCommand = mock(CloseDoorCommand.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.OPENED);
        when(commandManager.getCommand(CLOSE_DOOR_COMMAND)).thenReturn(closeDoorCommand);
        when(houseThreadPool.getFixedThreadPool()).thenReturn(Executors.newScheduledThreadPool(4));

        elevatorDriver.handleDoorStateChanged(event);
        await();

        verify(closeDoorCommand).execute(any());
    }

    @Test
    public void handleDoorStateChangedToClosedNotLastTest() {
        DoorStateChangedEvent event = mock(DoorStateChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        Cabin cabin = mock(Cabin.class);
        SequenceOfStops sequence = mock(SequenceOfStops.class);
        MoveCabinCommand moveCabinCommand = mock(MoveCabinCommand.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getCabin()).thenReturn(cabin);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);
        when(shaft.getCabinPosition()).thenReturn(2);
        when(sequence.isLast(shaft.getCabinPosition())).thenReturn(false);
        when(cabin.getDoorState()).thenReturn(DoorState.CLOSED);
        when(commandManager.getCommand(MOVE_CABIN_COMMAND)).thenReturn(moveCabinCommand);
        when(houseThreadPool.getFixedThreadPool()).thenReturn(Executors.newScheduledThreadPool(4));

        elevatorDriver.handleDoorStateChanged(event);
        await();

        verify(moveCabinCommand).execute(any());
    }

    @Test
    public void handleDoorStateChangedToClosedIsLastTest() {
        DoorStateChangedEvent event = mock(DoorStateChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        Cabin cabin = mock(Cabin.class);
        SequenceOfStops sequence = mock(SequenceOfStops.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getCabin()).thenReturn(cabin);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);
        when(shaft.getCabinPosition()).thenReturn(2);
        when(sequence.isLast(shaft.getCabinPosition())).thenReturn(true);
        when(cabin.getDoorState()).thenReturn(DoorState.CLOSED);

        elevatorDriver.handleDoorStateChanged(event);
        await();

        verify(shaft).clearSequence();
    }
}
