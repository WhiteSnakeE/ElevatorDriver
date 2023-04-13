package com.sytoss.edu.elevator.unit.services;

import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.HouseService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class HouseServiceTest {

    private final HouseRepository houseRepository = mock(HouseRepository.class);
    private final ShaftRepository shaftRepository = mock(ShaftRepository.class);
    private final HouseService houseService = new HouseService(houseRepository, shaftRepository);

    @Test
    public void saveRequestTest() {
        HouseDTO houseDTO = spy(HouseDTO.builder().build());
        houseDTO.setNumberOfShafts(2);
        houseService.saveRequest(houseDTO);

        verify(houseRepository).save(any());
        verify(shaftRepository, times(2)).save(any());
    }
}
