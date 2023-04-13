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
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;

    private final ShaftRepository shaftRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public void saveRequest(HouseDTO houseDTO) {
        var houseRecord = houseFromDTO(houseDTO);
        houseRepository.save(houseRecord);
        for (int i = 0; i < houseDTO.getNumberOfShafts(); i++) {
            ShaftDTO shaftDTO = shaftToDTO(new Shaft(), houseRecord.getId());
            var shaftRecord = shaftFromDTO(shaftDTO);
            shaftRepository.save(shaftRecord);
        }
    }

    private HouseConverter houseFromDTO(HouseDTO houseDTO) {
        Long id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        HouseConverter houseConverter = modelMapper.map(houseDTO, HouseConverter.class);
        houseConverter.setId(id);
        return houseConverter;
    }

    private ShaftConverter shaftFromDTO(ShaftDTO shaftDTO) {
        return modelMapper.map(shaftDTO, ShaftConverter.class);
    }

    private ShaftDTO shaftToDTO(Shaft shaft, Long houseId) {
        return ShaftDTO.builder().id(shaft.getId()).cabinPosition(shaft.getCabinPosition()).doorState(shaft.getCabin()
                        .getDoorState().toString()).engineState(shaft.getEngine().getEngineState().toString())
                .overweightState(shaft.getCabin().getOverWeightState().toString()).houseId(houseId).build();
    }
}
