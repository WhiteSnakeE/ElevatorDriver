package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Engine;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.commands.MoveCabinCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class MoveCabinCommandTest {

    private final MoveCabinCommand moveCabinCommand = new MoveCabinCommand();

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        Engine engine = mock(Engine.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        params.put("Direction", Direction.UPWARDS);

        when(shaft.getEngine()).thenReturn(engine);
        moveCabinCommand.execute(params);
        verify(engine).start(Direction.UPWARDS);
    }
}