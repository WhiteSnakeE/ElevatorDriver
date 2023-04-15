package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PressUpButtonCommand implements Command {

    private final ElevatorDriver elevatorDriver;

    private final CommandManager commandManager;

    private final HouseConverter houseConverter;

    private final HouseRepository houseRepository;

    @Override
    public void execute (HashMap<String, Object> params) {
        log.info("Start PressUpButton.execute COMMAND with params: {}", params);
        int numberFloor = (int) params.get("numberFloor");
        Direction direction = (Direction) params.get("Direction");
        long idHouse = (long) params.get("houseId");

        elevatorDriver.addNewSequenceToOrder(numberFloor, direction);
        String json = houseConverter.orderSequenceToStringInJSON((elevatorDriver.getOrderSequenceOfStops()));

        houseRepository.updateOrderById(idHouse, json);

        commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND).execute(null);
    }
}
