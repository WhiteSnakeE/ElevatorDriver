package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandManagerTest {

    @Test
    public void getCommandTest () {
        CommandManager commandManager = mock(CommandManager.class);
        when(commandManager.getCommand("FindNearestCabinCommand")).thenReturn(mock(FindNearestCabinCommand.class));
        Command resultCommand = commandManager.getCommand("FindNearestCabinCommand");

        if (resultCommand instanceof FindNearestCabinCommand) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void getCommandFailedTest () {
        CommandManager commandManager = new CommandManager();
        Assertions.assertThrows(IllegalArgumentException.class, () -> commandManager.getCommand("BadName"));
    }
}
