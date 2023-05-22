package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.Cabin;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.events.DoorStateChangedEvent;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.CabinService;
import com.sytoss.edu.elevator.services.ShaftService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CabinServiceTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final CabinService cabinService = new CabinService(shaftRepository);

    @Test
    public void handleDoorStateChangedTest() {
        DoorStateChangedEvent event = mock(DoorStateChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        Cabin cabin = mock(Cabin.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getCabin()).thenReturn(cabin);
        when(shaft.getId()).thenReturn(1L);
        when(cabin.getDoorState()).thenReturn(DoorState.OPENED);

        cabinService.handleDoorStateChanged(event);
        verify(shaftRepository).updateDoorStateById(1L, DoorState.OPENED);
    }
}
