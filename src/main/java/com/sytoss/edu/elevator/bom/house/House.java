package com.sytoss.edu.elevator.bom.house;

import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class House {

    @Getter
    private List<Shaft> shafts = new ArrayList<>();

    @Getter
    private List<Floor> floors = new ArrayList<>();

    public Shaft moveSequenceToShaft (List<SequenceOfStops> orderSequenceOfStops) {
        Shaft nearestCabin = findNearestCabin(orderSequenceOfStops);
        nearestCabin.setSequenceOfStops(orderSequenceOfStops.get(0));
        return nearestCabin;
    }

    private Shaft findNearestCabin (List<SequenceOfStops> orderSequenceOfStops) {
        List<Shaft> freeShafts = getFreeShafts();
        int firstStop = orderSequenceOfStops.get(0).getStopFloors().get(0);
        int minLength = Integer.MAX_VALUE;
        Shaft nearestCabin = freeShafts.get(0);

        for (Shaft shaft : freeShafts) {
            int currentLength = Math.abs(firstStop - shaft.getCabinPosition());
            if (currentLength < minLength) {
                nearestCabin = shaft;
                minLength = currentLength;
            }
        }
        return nearestCabin;
    }

    private List<Shaft> getFreeShafts () {
        List<Shaft> result = new ArrayList<>();
        for (Shaft shaft : shafts) {
            if (shaft.isFree()) {
                result.add(shaft);
            }
        }
        return result;
    }
}
