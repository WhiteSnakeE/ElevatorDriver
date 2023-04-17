package com.sytoss.edu.elevator.bom.house;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Entity;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class House extends Entity {

    private List<Shaft> shafts = new ArrayList<>();

    private List<Floor> floors = new ArrayList<>();

    public Shaft moveSequenceToShaft(ElevatorDriver elevatorDriver) {
        Shaft nearestCabin = findNearestCabin(elevatorDriver.getOrderSequenceOfStops());

        if (nearestCabin == null) {
            return null;
        }

        boolean isNeedActivate = nearestCabin.updateSequence(elevatorDriver);

        if (!isNeedActivate) {
            return null;
        }

        return nearestCabin;
    }


    private Shaft findNearestCabin(List<SequenceOfStops> orderSequenceOfStops) {
        List<Shaft> appropriateShafts = getFreeShafts();

        if (appropriateShafts.isEmpty()) {
            log.info("findNearestCabin: all cabin is busy!");
            appropriateShafts = shaftWithAppropriateDirection(orderSequenceOfStops.get(0).getDirection(), orderSequenceOfStops);
            if (appropriateShafts.isEmpty()) {
                log.info("findNearestCabin: appropriate cabin not found");
                return null;
            }
        }

        int firstStop = orderSequenceOfStops.get(0).getStopFloors().get(0);
        int minLength = Integer.MAX_VALUE;
        Shaft nearestCabin = appropriateShafts.get(0);

        for (Shaft shaft : appropriateShafts) {
            int currentLength = Math.abs(firstStop - shaft.getCabinPosition());
            if (currentLength < minLength) {
                nearestCabin = shaft;
                minLength = currentLength;
            }
        }
        return nearestCabin;
    }

    private List<Shaft> getFreeShafts() {
        return shafts.stream().filter(Shaft::isFree).toList();
    }

    private List<Shaft> shaftWithAppropriateDirection(Direction currentDirection,
                                                      List<SequenceOfStops> orderSequenceOfStops) {
        return shafts.stream().filter(shaft -> shaft.isSameDirection(currentDirection, orderSequenceOfStops.get(0).getStopFloors().get(0))).toList();
    }
}






