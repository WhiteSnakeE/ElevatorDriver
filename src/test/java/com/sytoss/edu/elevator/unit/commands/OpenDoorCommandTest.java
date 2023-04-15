package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.commands.OpenDoorCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

public class OpenDoorCommandTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);
    private final OpenDoorCommand openDoorCommand = new OpenDoorCommand(shaftRepository);

    @Test
    public void executeTest() {
        Shaft shaft = spy(Shaft.class);
        shaft.setId(123L);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);
        Cabin cabin = mock(Cabin.class);

        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.OPENED);
        openDoorCommand.execute(params);
        verify(cabin).openDoor();
        verify(shaftRepository).updateDoorStateById(123L, DoorState.OPENED.toString());
    }
}
