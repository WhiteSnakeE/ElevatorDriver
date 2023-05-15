package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.commands.MoveCabinCommand;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.HouseService;
import com.sytoss.edu.elevator.services.ShaftService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

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
        when(house.findNearestCabin(elevatorDriver.getOrderSequenceOfStops())).thenReturn(shaft);
        when(commandManager.getCommand(Command.MOVE_CABIN_COMMAND)).thenReturn(moveCabinCommand);
        when(shaft.isCabinMoving()).thenReturn(false);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);
        when(houseThreadPool.getFixedThreadPool()).thenReturn(Executors.newScheduledThreadPool(4));

        HashMap<String, Object> params = new HashMap<>();
        params.put(CommandManager.HOUSE_PARAM, house);
        findNearestCabinCommand.execute(params);
        await();

        verify(house).findNearestCabin(elevatorDriver.getOrderSequenceOfStops());
        verify(commandManager.getCommand(Command.MOVE_CABIN_COMMAND)).execute(any());
        verify(shaft).updateSequence(elevatorDriver);
        verify(shaft).isCabinMoving();
    }

    @Test
    public void executeShaftIsNullTest() {

        HashMap<String, Object> params = new HashMap<>();
        params.put(CommandManager.HOUSE_PARAM, house);

        when(house.getElevatorDriver()).thenReturn(elevatorDriver);
        when(commandManager.getCommand(Command.MOVE_CABIN_COMMAND)).thenReturn(mock(MoveCabinCommand.class));
        findNearestCabinCommand.execute(params);

        verify(commandManager.getCommand(Command.MOVE_CABIN_COMMAND), times(0)).execute(Mockito.any());
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
