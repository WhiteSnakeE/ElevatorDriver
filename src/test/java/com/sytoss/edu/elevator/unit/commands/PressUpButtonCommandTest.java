package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.commands.PressUpButtonCommand;
import com.sytoss.edu.elevator.services.HouseService;
import com.sytoss.edu.elevator.services.ShaftService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListResourceBundle;

import static org.mockito.Mockito.*;


public class PressUpButtonCommandTest {

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    private final CommandManager commandManager = mock(CommandManager.class);

    private final ShaftService shaftService = mock(ShaftService.class);

    private final PressUpButtonCommand pressUpButtonCommand = new PressUpButtonCommand(commandManager, shaftService);

    @Test
    public void executeTest() {
        House house = mock(House.class);
        HashMap<String, Object> params = new HashMap<>();

        params.put(CommandManager.FLOOR_NUMBER_PARAM, 5);
        params.put(CommandManager.HOUSE_PARAM, house);
        params.put(CommandManager.DIRECTION_PARAM, Direction.UPWARDS);

        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(List.of(5));
        sequence.setDirection(Direction.UPWARDS);

        List<SequenceOfStops> order = new ArrayList<>();
        order.add(sequence);

        when(commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND)).thenReturn(mock(FindNearestCabinCommand.class));
        when(house.getElevatorDriver()).thenReturn(elevatorDriver);
        when(elevatorDriver.getOrderSequenceOfStops()).thenReturn(order);

        pressUpButtonCommand.execute(params);
        verify(elevatorDriver).addNewSequenceToOrder(5, Direction.UPWARDS);
        verify(commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND)).execute(params);
    }
}
