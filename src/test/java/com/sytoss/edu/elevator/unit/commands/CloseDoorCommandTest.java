package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.commands.CloseDoorCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class CloseDoorCommandTest {

    private final CloseDoorCommand closeDoorCommand = new CloseDoorCommand();

    @Test
    public void executeTest () {
        Shaft shaft = mock(Shaft.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        Cabin cabin = mock(Cabin.class);

        when(shaft.getCabin()).thenReturn(cabin);
        closeDoorCommand.execute(params);
        verify(cabin).closeDoor();
    }
}