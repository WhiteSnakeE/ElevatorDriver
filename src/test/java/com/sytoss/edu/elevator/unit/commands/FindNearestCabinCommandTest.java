package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.ActivateShaftCommand;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class FindNearestCabinCommandTest {

    private final House house = mock(House.class);

    private final CommandManager commandManager = mock(CommandManager.class);

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    private final FindNearestCabinCommand findNearestCabinCommand = new FindNearestCabinCommand(house, elevatorDriver, commandManager);

    @Test
    public void executeTest () {
        elevatorDriver.addNewSequenceToOrder(5, Direction.UPWARDS);

        ActivateShaftCommand activateShaftCommand = mock(ActivateShaftCommand.class);
        Shaft shaft = mock(Shaft.class);

        when(house.moveSequenceToShaft(elevatorDriver)).thenReturn(shaft);
        when(commandManager.getCommand(Command.ACTIVATE_SHAFT_COMMAND)).thenReturn(activateShaftCommand);

        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);

        findNearestCabinCommand.execute(null);
        verify(house).moveSequenceToShaft(elevatorDriver);
        verify(commandManager.getCommand(Command.ACTIVATE_SHAFT_COMMAND)).execute(params);
    }

    @Test
    public void executeShaftIsNullTest() {
        when(commandManager.getCommand(Command.ACTIVATE_SHAFT_COMMAND)).thenReturn(mock(ActivateShaftCommand.class));
        findNearestCabinCommand.execute(null);
        verify(commandManager.getCommand(Command.ACTIVATE_SHAFT_COMMAND), times(0)).execute(Mockito.any());
    }
}
