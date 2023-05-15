package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.commands.CloseDoorCommand;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.ShaftService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static org.mockito.Mockito.*;

class CloseDoorCommandTest {

    private final ShaftService shaftService = mock(ShaftService.class);

    private final CloseDoorCommand closeDoorCommand = new CloseDoorCommand(shaftService);

    @Test
    public synchronized void executeTest() {
        Shaft shaft = mock(Shaft.class);
        Cabin cabin = mock(Cabin.class);

        HashMap<String, Object> params = new HashMap<>();
        params.put(SHAFT_PARAM, shaft);

        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.CLOSED);

        closeDoorCommand.execute(params);

        verify(shaft).closeCabinDoor();
        verify(shaftService).updateDoorStateById(0L, DoorState.CLOSED);
        verify(shaftService).updateSequenceById(0L, null);
    }
}