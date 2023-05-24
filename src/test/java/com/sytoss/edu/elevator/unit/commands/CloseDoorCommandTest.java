package com.sytoss.edu.elevator.unit.commands;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.commands.CloseDoorCommand;
import com.sytoss.edu.elevator.services.ShaftService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

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

        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(List.of(1, 2, 3));
        sequence.setDirection(Direction.UPWARDS);

        when(shaft.getCabin()).thenReturn(cabin);
        when(cabin.getDoorState()).thenReturn(DoorState.CLOSED);
        when(shaft.getId()).thenReturn(1L);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);
        when(shaftService.getSequenceOfStopsByShaftId(shaft.getId())).thenReturn(mock(SequenceOfStops.class));

        closeDoorCommand.execute(params);

        verify(shaft).closeCabinDoor();
        verify(shaft).setSequenceOfStops(shaftService.getSequenceOfStopsByShaftId(1L));
        verify(shaftService, times(2)).getSequenceOfStopsByShaftId(shaft.getId());
    }
}