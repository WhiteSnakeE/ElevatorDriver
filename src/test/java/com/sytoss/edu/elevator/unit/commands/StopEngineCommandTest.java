package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.commands.StopEngineCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class StopEngineCommandTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final StopEngineCommand stopEngineCommand = new StopEngineCommand(shaftRepository);

    @Test
    public void executeTest() {
        Shaft shaft = spy(Shaft.class);
        shaft.setId(123L);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        Engine engine = mock(Engine.class);

        when(shaft.getEngine()).thenReturn(engine);
        when(engine.getEngineState()).thenReturn(EngineState.STAYING);
        stopEngineCommand.execute(params);

        verify(engine).stop();
        verify(shaftRepository).updateEngineStateById(123L, EngineState.STAYING);
    }
}