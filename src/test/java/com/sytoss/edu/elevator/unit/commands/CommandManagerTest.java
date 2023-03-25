package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommandManagerTest extends IntegrationTest {
    @Autowired
    CommandManager commandManager;

    @Test
    public void getCommandTest () {
        Command resultCommand = commandManager.getCommand("FindNearestCabinCommand");

        if (resultCommand instanceof FindNearestCabinCommand) {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void getCommandFailedTest () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> commandManager.getCommand("BadName"));
    }
}
