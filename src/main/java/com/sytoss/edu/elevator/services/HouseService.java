package com.sytoss.edu.elevator.services;

import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final HouseConverter houseConverter = new HouseConverter();

    private final ShaftConverter shaftConverter = new ShaftConverter();

    private final HouseRepository houseRepository;

    private final ShaftRepository shaftRepository;

    public void saveRequest(HouseDTO houseDTO) {
        var houseRecord = houseConverter.fromDTO(houseDTO);
        houseRepository.save(houseRecord);
        for (int i = 0; i < houseDTO.getNumberOfShafts(); i++) {
            Shaft shaft = new Shaft();
            ShaftDTO shaftDTO = new ShaftDTO(shaft.getId(), shaft.getCabinPosition(), shaft.getCabin().getDoorState().toString(), shaft.getEngine().getEngineState().toString(), shaft.getCabin().getOverWeightState().toString(), houseRecord.getId());
            var shaftRecord = shaftConverter.fromDTO(shaftDTO);
            shaftRepository.save(shaftRecord);
        }
    }
}
