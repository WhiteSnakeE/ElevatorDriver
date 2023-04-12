package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.commands.OpenDoorCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class OpenDoorCommandTest {

    private final OpenDoorCommand openDoorCommand = new OpenDoorCommand();

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        Cabin cabin = mock(Cabin.class);

        when(shaft.getCabin()).thenReturn(cabin);
        openDoorCommand.execute(params);
        verify(cabin).openDoor();
    }
}
