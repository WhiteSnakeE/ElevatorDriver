package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.events.EngineStateChangedEvent;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.EngineService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class EngineServiceTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final EngineService engineService = new EngineService(shaftRepository);

    @Test
    public void handleEngineStateChangedTest() {
        EngineStateChangedEvent event = mock(EngineStateChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        Engine engine = mock(Engine.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getId()).thenReturn(1L);
        when(shaft.getEngine()).thenReturn(engine);
        when(engine.getEngineState()).thenReturn(EngineState.STAYING);

        engineService.handleEngineStateChanged(event);
        verify(shaftRepository).updateEngineStateById(1L, EngineState.STAYING);
    }
}
