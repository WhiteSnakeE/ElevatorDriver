package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.commands.MoveCabinCommand;
import com.sytoss.edu.elevator.commands.OpenDoorCommand;
import com.sytoss.edu.elevator.services.HouseService;
import com.sytoss.edu.elevator.services.ShaftService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import static com.sytoss.edu.elevator.commands.Command.MOVE_CABIN_COMMAND;
import static com.sytoss.edu.elevator.commands.Command.OPEN_DOOR_COMMAND;
import static com.sytoss.edu.elevator.commands.CommandManager.HOUSE_PARAM;
import static org.mockito.Mockito.*;

public class FindNearestCabinCommandTest {

    private final House house = mock(House.class);

    private final CommandManager commandManager = mock(CommandManager.class);

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    private final ShaftService shaftService = mock(ShaftService.class);

    private final HouseService houseService = mock(HouseService.class);

    private final HouseThreadPool houseThreadPool = mock(HouseThreadPool.class);

    private final FindNearestCabinCommand findNearestCabinCommand = new FindNearestCabinCommand(commandManager, houseThreadPool, shaftService, houseService);

    @Test
    public void executeTest() {
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);

        MoveCabinCommand moveCabinCommand = mock(MoveCabinCommand.class);
        Shaft shaft = mock(Shaft.class);

        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.UPWARDS);
        sequence.setStopFloors(List.of(2, 3, 4));

        when(house.getElevatorDriver()).thenReturn(elevatorDriver);
        when(house.findNearestCabin()).thenReturn(shaft);
        when(commandManager.getCommand(MOVE_CABIN_COMMAND)).thenReturn(moveCabinCommand);
        when(shaft.isCabinMoving()).thenReturn(false);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);
        when(houseThreadPool.getFixedThreadPool()).thenReturn(Executors.newScheduledThreadPool(4));

        HashMap<String, Object> params = new HashMap<>();
        params.put(HOUSE_PARAM, house);
        findNearestCabinCommand.execute(params);
        await();

        verify(house).findNearestCabin();
        verify(commandManager.getCommand(MOVE_CABIN_COMMAND)).execute(any());
        verify(shaft).updateSequence(elevatorDriver);
        verify(shaft).isCabinMoving();
    }


    @Test
    public void executeIsCabinMovingTest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put(HOUSE_PARAM, house);
        Shaft shaft = mock(Shaft.class);
        when(house.getElevatorDriver()).thenReturn(mock(ElevatorDriver.class));
        when(house.findNearestCabin()).thenReturn(shaft);
        when(shaft.isCabinMoving()).thenReturn(true);
        when(commandManager.getCommand(OPEN_DOOR_COMMAND)).thenReturn(mock(OpenDoorCommand.class));
        when(commandManager.getCommand(MOVE_CABIN_COMMAND)).thenReturn(mock(MoveCabinCommand.class));
        findNearestCabinCommand.execute(params);

        await();

        verify(houseService, times(2)).updateOrderById(house.getId(), house.getElevatorDriver().getOrderSequenceOfStops());
        verify(house).findNearestCabin();
        verify(shaft).isCabinMoving();
        verify(shaft).updateSequence(house.getElevatorDriver());
        verify(commandManager.getCommand(OPEN_DOOR_COMMAND), times(0)).execute(any());
        verify(commandManager.getCommand(MOVE_CABIN_COMMAND), times(0)).execute(any());
    }

    @Test
    public void executeMovingDownTest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put(HOUSE_PARAM, house);
        Shaft shaft = mock(Shaft.class);
        SequenceOfStops sequenceOfStops = new SequenceOfStops();
        sequenceOfStops.setDirection(Direction.UPWARDS);
        sequenceOfStops.setStopFloors(List.of(2, 3, 4));
        when(house.getElevatorDriver()).thenReturn(mock(ElevatorDriver.class));
        when(house.findNearestCabin()).thenReturn(shaft);
        when(shaft.isCabinMoving()).thenReturn(false);
        when(shaft.getCabinPosition()).thenReturn(5);
        when(shaft.getSequenceOfStops()).thenReturn(sequenceOfStops);
        when(commandManager.getCommand(OPEN_DOOR_COMMAND)).thenReturn(mock(OpenDoorCommand.class));
        when(commandManager.getCommand(MOVE_CABIN_COMMAND)).thenReturn(mock(MoveCabinCommand.class));
        findNearestCabinCommand.execute(params);
        await();

        verify(houseService, times(2)).updateOrderById(house.getId(), house.getElevatorDriver().getOrderSequenceOfStops());
        verify(house).findNearestCabin();
        verify(shaft).isCabinMoving();
        verify(shaft).updateSequence(house.getElevatorDriver());
        verify(shaft).clearSequence();
        verify(commandManager.getCommand(OPEN_DOOR_COMMAND), times(0)).execute(any());
        verify(commandManager.getCommand(MOVE_CABIN_COMMAND), times(0)).execute(any());
    }

    @Test
    public void executeCabinOnTheSameFloorTest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put(HOUSE_PARAM, house);
        Shaft shaft = mock(Shaft.class);
        SequenceOfStops sequenceOfStops = new SequenceOfStops();
        sequenceOfStops.setDirection(Direction.UPWARDS);
        sequenceOfStops.setStopFloors(List.of(2, 3, 4));
        when(house.getElevatorDriver()).thenReturn(mock(ElevatorDriver.class));
        when(house.findNearestCabin()).thenReturn(shaft);
        when(shaft.isCabinMoving()).thenReturn(false);
        when(shaft.getCabinPosition()).thenReturn(2);
        when(shaft.getSequenceOfStops()).thenReturn(sequenceOfStops);
        when(houseThreadPool.getFixedThreadPool()).thenReturn(Executors.newScheduledThreadPool(4));
        when(commandManager.getCommand(OPEN_DOOR_COMMAND)).thenReturn(mock(OpenDoorCommand.class));
        findNearestCabinCommand.execute(params);

        await();

        verify(houseService, times(2)).updateOrderById(house.getId(), house.getElevatorDriver().getOrderSequenceOfStops());
        verify(house).findNearestCabin();
        verify(shaft).isCabinMoving();
        verify(shaft).updateSequence(house.getElevatorDriver());
        verify(commandManager.getCommand(OPEN_DOOR_COMMAND)).execute(any());
    }

    @Test
    public void executeShaftIsNullTest() {

        HashMap<String, Object> params = new HashMap<>();
        params.put(HOUSE_PARAM, house);

        when(house.getElevatorDriver()).thenReturn(elevatorDriver);
        when(commandManager.getCommand(MOVE_CABIN_COMMAND)).thenReturn(mock(MoveCabinCommand.class));
        findNearestCabinCommand.execute(params);

        verify(commandManager.getCommand(MOVE_CABIN_COMMAND), times(0)).execute(Mockito.any());
    }

    private void await() {
        int time = 1000;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
