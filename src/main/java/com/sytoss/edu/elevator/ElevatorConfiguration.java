package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ElevatorConfiguration {

    private final HouseRepository houseRepository;

    private final HouseConverter houseConverter;

    private final ShaftRepository shaftRepository;

    private final ElevatorDriver elevatorDriver;

    private final CommandManager commandManager;

    @Bean("house")
    public House getHouse() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        List<Future> threads = new ArrayList<>();

        List<HouseDTO> houseDTOList = houseRepository.findAll();
        HouseDTO houseDTO = houseDTOList.get(houseDTOList.size() - 1);
        List<ShaftDTO> shaftDTOList = shaftRepository.findByHouseDTOId(houseDTO.getId());
        elevatorDriver.setOrderSequenceOfStops(houseConverter.stringJSONToOrderSequence(houseDTO.getOrderSequenceOfStops()));

        House house = houseConverter.fromDTO(houseDTO, shaftDTOList);

        for (Shaft shaft : house.getShafts()) {
            if (shaft.getSequenceOfStops() != null) {
                threads.add(fixedThreadPool.submit(() -> {
                    log.info("startMoveCabin: start threads for shaft with id {}", shaft.getId());
                    HashMap<String, Object> paramsActivateCommand = new HashMap<>();
                    //todo after merging e23-19 update shaft_param, floor_param (egorBP)
                    paramsActivateCommand.put("Shaft", shaft);
                    paramsActivateCommand.put("Floors", house.getFloors());
                    commandManager.getCommand(Command.MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
                    log.info("startMoveCabin: finish threads for shaft with id {}", shaft.getId());
                }));
            }
        }

        fixedThreadPool.shutdown();

        return house;
    }
}
