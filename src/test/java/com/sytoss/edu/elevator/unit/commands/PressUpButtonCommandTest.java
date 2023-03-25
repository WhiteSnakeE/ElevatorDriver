package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.commands.PressUpButtonCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.HashMap;

import static org.mockito.Mockito.verify;

public class PressUpButtonCommandTest extends IntegrationTest {
    @Autowired
    private PressUpButtonCommand pressUpButtonCommand;
    @MockBean
    private ElevatorDriver elevatorDriver;
    @SpyBean
    private CommandManager commandManager;
    @MockBean
    FindNearestCabinCommand findNearestCabinCommand;

    @Test
    public void executeTest () {
        HashMap<String, Object> params = new HashMap<>();
        params.put("numberFloor", 5);
        params.put("Direction", Direction.UPWARDS);
        pressUpButtonCommand.execute(params);

        verify(elevatorDriver).addNewSequenceToOrder(5, Direction.UPWARDS);
        verify(commandManager).getCommand(CommandManager.FIND_NEAREST_CABIN_COMMAND);
        verify(findNearestCabinCommand).execute(null);
    }
}
