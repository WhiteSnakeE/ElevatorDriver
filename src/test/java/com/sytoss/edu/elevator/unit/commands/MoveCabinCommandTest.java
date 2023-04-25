package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.*;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

public class MoveCabinCommandTest {

    private final CommandManager commandManager = mock(CommandManager.class);
    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);
    private final MoveCabinCommand moveCabinCommand = new MoveCabinCommand(commandManager, shaftRepository);

    @Test
    public void executeTest() {
        StartEngineCommand startEngineCommand = mock(StartEngineCommand.class);
        StopEngineCommand stopEngineCommand = mock(StopEngineCommand.class);


        Shaft shaft = spy(Shaft.class);
        Engine engine = new Engine();
        engine.setEngineState(EngineState.STAYING);
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.UPWARDS);
        sequence.setStopFloors(List.of(3, 4));

        shaft.setSequenceOfStops(sequence);
        shaft.setCabinPosition(1);
        shaft.setEngine(engine);


        House houseResult=new HouseBuilder(mock(CommandManager.class)).build(2,16);

        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        params.put("Floors", houseResult.getFloors());

        when(commandManager.getCommand(Command.OPEN_DOOR_COMMAND)).thenReturn(mock(OpenDoorCommand.class));
        when(commandManager.getCommand(Command.CLOSE_DOOR_COMMAND)).thenReturn(mock(CloseDoorCommand.class));
        when(commandManager.getCommand(Command.START_ENGINE_COMMAND)).thenReturn(startEngineCommand);
        when(commandManager.getCommand(Command.STOP_ENGINE_COMMAND)).thenReturn(stopEngineCommand);

        doAnswer(invocation -> {
            HashMap<String, Object> arg = invocation.getArgument(0);
            Shaft shaftArg = (Shaft) arg.get("Shaft");
            shaftArg.getEngine().setEngineState(EngineState.GOING_UP);
            return null;
        }).when(startEngineCommand).execute(params);

        doAnswer(invocation -> {
            HashMap<String, Object> arg = invocation.getArgument(0);
            Shaft shaftArg = (Shaft) arg.get("Shaft");
            shaftArg.getEngine().setEngineState(EngineState.STAYING);
            return null;
        }).when(stopEngineCommand).execute(params);

        moveCabinCommand.execute(params);

        verify(commandManager.getCommand(Command.OPEN_DOOR_COMMAND), times(2)).execute(Mockito.any());
        verify(commandManager.getCommand(Command.CLOSE_DOOR_COMMAND), times(2)).execute(Mockito.any());
        verify(commandManager.getCommand(Command.START_ENGINE_COMMAND), times(2)).execute(Mockito.any());
        verify(commandManager.getCommand(Command.STOP_ENGINE_COMMAND), times(2)).execute(Mockito.any());
        verify(shaft).clearSequence();
        verify(shaftRepository).updateSequenceById(shaft.getId(), null);
    }
}