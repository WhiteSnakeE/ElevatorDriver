package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.services.ShaftService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

import static com.sytoss.edu.elevator.commands.CommandManager.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class PressUpButtonCommand implements Command {

    private final CommandManager commandManager;

    private final ShaftService shaftService;

    @Override
    public void execute(HashMap<String, Object> params) {
        log.info("Start PressUpButton.execute COMMAND with params: {}", params);

        int numberFloor = (int) params.get(FLOOR_NUMBER_PARAM);
        Direction direction = (Direction) params.get(DIRECTION_PARAM);
        House house = (House) params.get(HOUSE_PARAM);

        house.addNewSequenceToOrder(numberFloor, direction);
        List<SequenceOfStops> order = house.getElevatorDriver().getOrderSequenceOfStops();
        order.get(order.size() - 1).addSequenceOfStopsListener(shaftService);

        params.remove(FLOOR_NUMBER_PARAM);

        commandManager.getCommand(Command.FIND_NEAREST_CABIN_COMMAND).execute(params);
    }
}
