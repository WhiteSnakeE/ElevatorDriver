package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;
import com.sytoss.edu.elevator.events.SequenceOfStopsChangedEvent;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.ShaftService;
import com.sytoss.edu.elevator.utils.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class ShaftServiceTest {

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final ShaftService shaftService = new ShaftService(shaftRepository);

    @Test
    public void updateDoorStateByIdTest() {
        shaftService.updateDoorStateById(1L, DoorState.OPENED);
        verify(shaftRepository).updateDoorStateById(1L, DoorState.OPENED);
    }

    @Test
    public void updateSequenceByIdTest() {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(List.of(1, 2, 3));
        sequence.setDirection(Direction.UPWARDS);

        shaftService.updateSequenceById(1L, sequence);
        verify(shaftRepository).updateSequenceById(1L, JsonUtil.sequenceToStringInJSON(sequence));
    }

    @Test
    public void updateEngineStateByIdTest() {
        shaftService.updateEngineStateById(1L, EngineState.STAYING);
        verify(shaftRepository).updateEngineStateById(1L, EngineState.STAYING);
    }

    @Test
    public void updateCabinPositionByIdTest() {
        shaftService.updateCabinPositionById(1L, 5);
        verify(shaftRepository).updateCabinPositionById(1L, 5);
    }

    @Test
    public void getSequenceOfStopsByShaftIdTest() {
        ShaftDTO shaftDTO = mock(ShaftDTO.class);
        SequenceOfStops sequenceOfStops = new SequenceOfStops();
        when(shaftRepository.getShaftDTOById(1L)).thenReturn(shaftDTO);
        when(shaftDTO.getSequenceOfStops()).thenReturn(JsonUtil.sequenceToStringInJSON(sequenceOfStops));

        shaftService.getSequenceOfStopsByShaftId(1L);

        verify(shaftRepository).getShaftDTOById(1L);
    }

    @Test
    public void handleCabinPositionChangedTest() {
        CabinPositionChangedEvent event = mock(CabinPositionChangedEvent.class);
        Shaft shaft = mock(Shaft.class);

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getId()).thenReturn(1L);
        when(shaft.getCabinPosition()).thenReturn(5);

        shaftService.handleCabinPositionChanged(event);
        verify(shaftRepository).updateCabinPositionById(1L, 5);
    }

    @Test
    public void handleSequenceOfStopsChangedTest() {
        SequenceOfStopsChangedEvent event = mock(SequenceOfStopsChangedEvent.class);
        Shaft shaft = mock(Shaft.class);
        SequenceOfStops sequence = null;

        when(event.getShaft()).thenReturn(shaft);
        when(shaft.getId()).thenReturn(1L);
        when(shaft.getSequenceOfStops()).thenReturn(sequence);

        shaftService.handleSequenceOfStopsChanged(event);
        verify(shaftRepository).updateSequenceById(1L, JsonUtil.sequenceToStringInJSON(sequence));
    }
}
