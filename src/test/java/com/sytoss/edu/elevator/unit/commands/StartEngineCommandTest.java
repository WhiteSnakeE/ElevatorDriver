package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.commands.StartEngineCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.ShaftService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class StartEngineCommandTest {

    private final ShaftService shaftService = mock(ShaftService.class);

    private final StartEngineCommand startEngineCommand = new StartEngineCommand(shaftService);

    @Test
    public void executeTest() {
        Shaft shaft = spy(Shaft.class);
        shaft.setId(123L);
        Engine engine = mock(Engine.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        params.put("Direction", Direction.UPWARDS);

        when(shaft.getEngine()).thenReturn(engine);
        when(engine.getEngineState()).thenReturn(EngineState.GOING_UP);

        startEngineCommand.execute(params);
        verify(engine).start(Direction.UPWARDS);
        verify(shaftService).updateEngineStateById(123L, EngineState.GOING_UP);
    }
}