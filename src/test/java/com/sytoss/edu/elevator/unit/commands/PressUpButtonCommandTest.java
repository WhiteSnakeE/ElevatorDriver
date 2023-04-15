package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.commands.PressUpButtonCommand;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;


public class PressUpButtonCommandTest {

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);
    private final CommandManager commandManager = mock(CommandManager.class);
    private final HouseConverter houseConverter = mock(HouseConverter.class);
    private final HouseRepository houseRepository = mock(HouseRepository.class);
    private final PressUpButtonCommand pressUpButtonCommand = new PressUpButtonCommand(
            elevatorDriver, commandManager, houseConverter, houseRepository
    );

    @Test
    public void executeTest () {
        HashMap<String, Object> params = new HashMap<>();

        params.put("houseId", 123L);
        params.put("numberFloor", 5);
        params.put("Direction", Direction.UPWARDS);

        when(commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND)).thenReturn(mock(FindNearestCabinCommand.class));

        pressUpButtonCommand.execute(params);

        verify(elevatorDriver).addNewSequenceToOrder(5, Direction.UPWARDS);
        verify(commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND)).execute(null);
        verify(houseConverter).orderSequenceToStringInJSON(anyList());
        verify(houseRepository).updateOrderById(anyLong(), any());
    }
}
