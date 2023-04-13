package com.sytoss.edu.elevator.unit.controller;

import com.sytoss.edu.elevator.controller.HouseController;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.services.HouseService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HouseControllerTest {

    private final HouseService houseService = mock(HouseService.class);
    private final HouseController houseController = new HouseController(houseService);

    @Test
    public void saveRequestTest() {
        HouseDTO houseDTO = mock(HouseDTO.class);
        houseController.saveRequest(houseDTO);
        verify(houseService).saveRequest(houseDTO);
    }
}
