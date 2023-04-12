package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.commands.StartEngineCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class StartEngineCommandTest {

    private final StartEngineCommand startEngineCommand = new StartEngineCommand();

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        Engine engine = mock(Engine.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        params.put("Direction", Direction.UPWARDS);

        when(shaft.getEngine()).thenReturn(engine);
        startEngineCommand.execute(params);
        verify(engine).start(Direction.UPWARDS);
    }
}