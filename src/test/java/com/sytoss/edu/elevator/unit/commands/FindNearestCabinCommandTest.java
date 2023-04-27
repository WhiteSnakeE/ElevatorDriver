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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

public class FindNearestCabinCommandTest {

    private final House house = mock(House.class);

    private final CommandManager commandManager = mock(CommandManager.class);

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final HouseRepository houseRepository = mock(HouseRepository.class);

    private final HouseThreadPool houseThreadPool = spy(HouseThreadPool.class);

    private final FindNearestCabinCommand findNearestCabinCommand = new FindNearestCabinCommand(house, elevatorDriver, commandManager, shaftRepository, houseRepository, houseThreadPool);

    @Test
    public void executeTest () {
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);

        MoveCabinCommand moveCabinCommand = mock(MoveCabinCommand.class);
        Shaft shaft = mock(Shaft.class);

        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.UPWARDS);
        sequence.setStopFloors(List.of(2, 3, 4));

        when(house.findNearestCabin(elevatorDriver.getOrderSequenceOfStops())).thenReturn(shaft);
        when(commandManager.getCommand(Command.MOVE_CABIN_COMMAND)).thenReturn(moveCabinCommand);
        when(shaft.isCabinMoving()).thenReturn(false);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);

        findNearestCabinCommand.execute(null);
        houseThreadPool.await();

        verify(house).findNearestCabin(elevatorDriver.getOrderSequenceOfStops());
        verify(commandManager.getCommand(Command.MOVE_CABIN_COMMAND)).execute(any());
        verify(shaft).updateSequence(elevatorDriver);
        verify(shaft).isCabinMoving();
    }

    @Test
    public void executeShaftIsNullTest () {
        when(commandManager.getCommand(Command.MOVE_CABIN_COMMAND)).thenReturn(mock(MoveCabinCommand.class));
        findNearestCabinCommand.execute(null);
        verify(commandManager.getCommand(Command.MOVE_CABIN_COMMAND), times(0)).execute(Mockito.any());
    }
}
