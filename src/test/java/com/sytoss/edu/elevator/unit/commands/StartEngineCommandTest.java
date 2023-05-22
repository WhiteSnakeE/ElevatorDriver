package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.commands.StartEngineCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class StartEngineCommandTest {

    private final StartEngineCommand startEngineCommand = new StartEngineCommand();

    @Test
    public void executeTest() {
        Shaft shaft = spy(Shaft.class);
        shaft.setId(123L);
        Engine engine = mock(Engine.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put(CommandManager.SHAFT_PARAM, shaft);

        when(shaft.getEngine()).thenReturn(engine);
        when(engine.getEngineState()).thenReturn(EngineState.GOING_UP);
        when(shaft.getSequenceOfStops()).thenReturn(mock(SequenceOfStops.class));
        when(shaft.getSequenceOfStops().getDirection()).thenReturn(Direction.UPWARDS);

        startEngineCommand.execute(params);
        verify(shaft).startEngine(Direction.UPWARDS);
    }
}