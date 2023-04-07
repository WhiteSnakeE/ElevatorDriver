package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.commands.StopCabinCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class StopCabinCommandTest {

    private final StopCabinCommand stopCabinCommand = new StopCabinCommand();

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        Engine engine = mock(Engine.class);

        when(shaft.getEngine()).thenReturn(engine);
        stopCabinCommand.execute(params);

        verify(engine).stop();
    }
}