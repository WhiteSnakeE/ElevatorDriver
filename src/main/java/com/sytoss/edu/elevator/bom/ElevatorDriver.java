package com.sytoss.edu.elevator.bom;

import com.sytoss.edu.elevator.HouseThreadPool;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.events.CabinPositionChangedEvent;
import com.sytoss.edu.elevator.events.DoorStateChangedEvent;
import com.sytoss.edu.elevator.services.ShaftListener;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.sytoss.edu.elevator.commands.Command.*;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;

@Component
@Getter
@Setter
@Slf4j
@RequiredArgsConstructor
public class ElevatorDriver extends Entity implements ShaftListener {

    private List<SequenceOfStops> orderSequenceOfStops = new ArrayList<>();

    private final CommandManager commandManager;

    private final HouseThreadPool houseThreadPool;

    public void addNewSequenceToOrder (int floorNumber, Direction direction) {
        SequenceOfStops sequenceOfStops = new SequenceOfStops();
        sequenceOfStops.setDirection(direction);
        sequenceOfStops.setStopFloors(new ArrayList<>(List.of(floorNumber)));
        orderSequenceOfStops.add(sequenceOfStops);
    }

    public void removeSequenceFromOrder () {
        if (orderSequenceOfStops.isEmpty()) {
            throw new IllegalStateException("Order sequence of stops is empty!");
        }
        orderSequenceOfStops.remove(0);
    }

    @Override
    public void handleCabinPositionChanged (CabinPositionChangedEvent event) {
        Shaft shaft = event.getShaft();

        if (shaft.getSequenceOfStops().getStopFloors().contains(shaft.getCabinPosition())) {
            HashMap<String, Object> params = new HashMap<>();
            params.put(SHAFT_PARAM, shaft);
            commandManager.getCommand(STOP_ENGINE_COMMAND).execute(params);
            commandManager.getCommand(OPEN_DOOR_COMMAND).execute(params);
        }
    }

    @Override
    public void handleDoorStateChanged (DoorStateChangedEvent event) {
        Shaft shaft = event.getShaft();
        if (shaft.getCabin().getDoorState().equals(DoorState.OPENED)) {
            HashMap<String, Object> params = new HashMap<>();
            params.put(SHAFT_PARAM, shaft);
            houseThreadPool.getFixedThreadPool().schedule(() -> commandManager.getCommand(CLOSE_DOOR_COMMAND).execute(params), 2000, TimeUnit.MILLISECONDS);
        }
    }
}

