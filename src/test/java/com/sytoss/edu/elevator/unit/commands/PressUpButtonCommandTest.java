package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.commands.PressUpButtonCommand;
import com.sytoss.edu.elevator.services.HouseService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;


public class PressUpButtonCommandTest {

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    private final CommandManager commandManager = mock(CommandManager.class);

    private final HouseService houseService = mock(HouseService.class);

    private final PressUpButtonCommand pressUpButtonCommand = new PressUpButtonCommand(commandManager, houseService);

    @Test
    public void executeTest() {
        House house = mock(House.class);
        HashMap<String, Object> params = new HashMap<>();

        params.put("numberFloor", 5);
        params.put(CommandManager.HOUSE_PARAM, house);
        params.put("Direction", Direction.UPWARDS);

        when(commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND)).thenReturn(mock(FindNearestCabinCommand.class));
        when(house.getElevatorDriver()).thenReturn(elevatorDriver);

        pressUpButtonCommand.execute(params);
        HashMap<String, Object> findNearestCabinParams = new HashMap<>();
        findNearestCabinParams.put(CommandManager.HOUSE_PARAM, house);

        verify(elevatorDriver).addNewSequenceToOrder(5, Direction.UPWARDS);
        verify(commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND)).execute(findNearestCabinParams);
    }
}
