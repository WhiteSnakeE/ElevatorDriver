package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.commands.CloseDoorCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.mockito.Mockito.*;

class CloseDoorCommandTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);
    private final CloseDoorCommand closeDoorCommand = new CloseDoorCommand(shaftRepository);

    @Test
    public void executeTest () {
        Shaft shaft = spy(Shaft.class);
        shaft.setId(123L);
        HashMap<String, Object> params = new HashMap<>();
        params.put("Shaft", shaft);

        Cabin cabin = mock(Cabin.class);

        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.CLOSED);

        closeDoorCommand.execute(params);
        verify(cabin).closeDoor();
        verify(shaftRepository).updateDoorStateById(123L, DoorState.CLOSED.toString());
    }
}