package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.services.HouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PressUpButtonCommand implements Command {

    private final CommandManager commandManager;

    private final HouseService houseService;

    @Override
    public void execute(HashMap<String, Object> params) {
        log.info("Start PressUpButton.execute COMMAND with params: {}", params);
        int numberFloor = (int) params.get(CommandManager.FLOOR_NUMBER_PARAM);
        Direction direction = (Direction) params.get(CommandManager.DIRECTION_PARAM);
        House house = (House) params.get(CommandManager.HOUSE_PARAM);
        house.getElevatorDriver().addNewSequenceToOrder(numberFloor, direction);
        houseService.updateOrderById(house.getId(), house.getElevatorDriver().getOrderSequenceOfStops());
        HashMap<String, Object> newParams = new HashMap<>();

        newParams.put(CommandManager.HOUSE_PARAM, house);

        commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND).execute(newParams);
    }
}
