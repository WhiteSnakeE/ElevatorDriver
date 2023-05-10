package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.params.HouseParams;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.HouseService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class HouseServiceTest {

    private final HouseRepository houseRepository = mock(HouseRepository.class);

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final CommandManager commandManager = mock(CommandManager.class);

    private final HouseBuilder houseBuilder = new HouseBuilder(commandManager);

    private final HouseConverter houseConverter = mock(HouseConverter.class);

    private final ShaftConverter shaftConverter = mock(ShaftConverter.class);

    private final ElevatorDriver elevatorDriver = mock(ElevatorDriver.class);

    private final House house = spy(House.class);

    private final HouseService houseService = new HouseService(houseRepository, shaftRepository, houseBuilder, houseConverter, shaftConverter, house, elevatorDriver);

    @Test
    public void saveRequestTest() {
        ShaftDTO shaftDTO = mock(ShaftDTO.class);
        HouseDTO houseDTO = mock(HouseDTO.class);

        HouseParams houseParams = spy(HouseParams.builder().build());
        houseParams.setNumberOfShafts(2);

        when(shaftConverter.toDTO(any(), any())).thenReturn(shaftDTO);
        when(shaftDTO.getId()).thenReturn(123L);

        when(houseConverter.toDTO(any(), any())).thenReturn(houseDTO);
        when(houseDTO.getId()).thenReturn(123L);

        houseService.saveRequest(houseParams);

        verify(houseRepository).save(any());
        verify(shaftRepository, times(2)).save(any());
        verify(houseConverter).toDTO(any(), any());
        verify(shaftConverter, times(2)).toDTO(any(), any());
    }
}
