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

    @Override
    public void execute (HashMap<String, Object> params) {
        //todo fix tests egorBP
        log.info("Start PressUpButton.execute COMMAND with params: {}", params);
        int numberFloor = (int) params.get("numberFloor");
        Direction direction = (Direction) params.get("Direction");

        elevatorDriver.addNewSequenceToOrder(numberFloor, direction);

        commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND).execute(null);
    }
}
