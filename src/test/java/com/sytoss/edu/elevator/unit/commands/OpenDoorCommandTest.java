package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.commands.OpenDoorCommand;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static org.mockito.Mockito.*;

public class OpenDoorCommandTest {

    private final OpenDoorCommand openDoorCommand = new OpenDoorCommand();

    @Test
    public void executeTest() {
        Shaft shaft = mock(Shaft.class);
        Cabin cabin = mock(Cabin.class);

        HashMap<String, Object> params = new HashMap<>();
        params.put(SHAFT_PARAM, shaft);

        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.OPENED);

        openDoorCommand.execute(params);

        verify(shaft).openCabinDoor();
    }
}
