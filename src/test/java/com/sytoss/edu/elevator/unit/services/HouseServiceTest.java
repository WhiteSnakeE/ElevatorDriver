package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.enums.Direction;
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
import com.sytoss.edu.elevator.utils.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class HouseServiceTest {

    private final HouseRepository houseRepository = mock(HouseRepository.class);

    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);

    private final CommandManager commandManager = mock(CommandManager.class);

    private final HouseBuilder houseBuilder = new HouseBuilder(commandManager);

    private final HouseConverter houseConverter = mock(HouseConverter.class);

    private final ShaftConverter shaftConverter = mock(ShaftConverter.class);

    private final HouseService houseService = new HouseService(houseRepository, shaftRepository, houseBuilder, houseConverter, shaftConverter, commandManager);

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

    @Test
    public void getHouseByShaftIdTest() {
        Long shaftId = 5L;
        HouseDTO houseDTO = mock(HouseDTO.class);
        House house = mock(House.class);

        List<ShaftDTO> shaftDTOList = new ArrayList<>();

        ShaftRepository.ShaftHouseId shaftHouseId = mock(ShaftRepository.ShaftHouseId.class);
        when(shaftRepository.getAllById(5L)).thenReturn(shaftHouseId);
        when(shaftHouseId.getHouseDTO()).thenReturn(houseDTO);
        when(houseDTO.getId()).thenReturn(shaftId);

        when(houseRepository.getReferenceById(3L)).thenReturn(houseDTO);

        when(houseDTO.getId()).thenReturn(3L);
        when(shaftRepository.findByHouseDTOId(3L)).thenReturn(shaftDTOList);
        when(houseConverter.fromDTO(houseDTO, shaftDTOList)).thenReturn(house);
        houseService.getHouseByShaftId(shaftId);

        verify(houseConverter).fromDTO(houseDTO, shaftDTOList);
    }

    @Test
    public void getHouseDTOTest() {
        HouseDTO houseDTO = mock(HouseDTO.class);
        when(houseRepository.getReferenceById(3L)).thenReturn(houseDTO);

        houseService.getHouseDTO(3L);

        verify(houseRepository).getReferenceById(3L);
    }

    @Test
    public void getHouseByIdTest() {
        HouseDTO houseDTO = mock(HouseDTO.class);
        House house = mock(House.class);
        List<ShaftDTO> shaftDTOList = new ArrayList<>();

        when(shaftRepository.findByHouseDTOId(4L)).thenReturn(shaftDTOList);
        when(houseRepository.getReferenceById(4L)).thenReturn(houseDTO);
        when(houseConverter.fromDTO(houseDTO, shaftDTOList)).thenReturn(house);

        houseService.getHouseById(4L);
        verify(houseConverter).fromDTO(houseDTO, shaftDTOList);
    }


    @Test
    public void updateOrderByIdTest(){
        List<SequenceOfStops> order = new ArrayList<>();
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(List.of(1, 2, 3));
        sequence.setDirection(Direction.UPWARDS);
        order.add(sequence);
        houseService.updateOrderById(1L, order);

        verify(houseRepository).updateOrderById(1L, JsonUtil.orderSequenceToStringInJSON(order));
    }
}
