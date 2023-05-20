package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.buttons.FloorWithUpButton;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.utils.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class FloorService {

    private final HouseService houseService;

    public void goUpCabinRequest(int houseNumber, int floorNumber) {
        House house = houseService.getHouseById(houseNumber);
        HouseDTO houseDTO = houseService.getHouseDTO(houseNumber);
        house.getElevatorDriver().setOrderSequenceOfStops(setOrder(houseDTO));

        if (floorNumber > house.getFloors().size()) {
            log.warn("Floor " + floorNumber + " doesn't exist in this house!");
            return;
        }

        Floor floor = house.getFloors().get(floorNumber - 1);

        if (floor instanceof FloorWithUpButton) {
            ((FloorWithUpButton) floor).pressUpButton();
        }
    }

    private List<SequenceOfStops> stringJsonToOrder(String order) {
        return JsonUtil.stringJSONToOrderSequence(order);
    }


    private String getOrderFromHouseDTO(HouseDTO houseDTO) {
        return houseDTO.getOrderSequenceOfStops();
    }

    private List<SequenceOfStops> setOrder(HouseDTO houseDTO) {
        String order = getOrderFromHouseDTO(houseDTO);
        return stringJsonToOrder(order);
    }
}
