package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.commands.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;

import static org.mockito.Mockito.*;

public class ActivateShaftCommandTest {

    private final CommandManager commandManager = mock(CommandManager.class);
    private final ActivateShaftCommand activateShaftCommand = new ActivateShaftCommand(commandManager);

    @Test
    public void executeTest() {
        Shaft shaft = spy(Shaft.class);
        Engine engine = new Engine();
        engine.setEngineState(EngineState.STAYING);
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.UPWARDS);
        sequence.setStopFloors(List.of(3, 4));

        shaft.setSequenceOfStops(sequence);
        shaft.setCabinPosition(1);
        shaft.setEngine(engine);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);

        when(commandManager.getCommand(Command.OPEN_DOOR_COMMAND)).thenReturn(spy(OpenDoorCommand.class));
        when(commandManager.getCommand(Command.CLOSE_DOOR_COMMAND)).thenReturn(spy(CloseDoorCommand.class));
        when(commandManager.getCommand(Command.MOVE_CABIN_COMMAND)).thenReturn(spy(MoveCabinCommand.class));
        when(commandManager.getCommand(Command.STOP_CABIN_COMMAND)).thenReturn(spy(StopCabinCommand.class));

        activateShaftCommand.execute(params);

        verify(commandManager.getCommand(Command.OPEN_DOOR_COMMAND), times(2)).execute(Mockito.any());
        verify(commandManager.getCommand(Command.CLOSE_DOOR_COMMAND), times(2)).execute(Mockito.any());
        verify(commandManager.getCommand(Command.MOVE_CABIN_COMMAND), times(2)).execute(Mockito.any());
        verify(commandManager.getCommand(Command.STOP_CABIN_COMMAND), times(2)).execute(Mockito.any());
        verify(shaft).clearSequence();
    }
}