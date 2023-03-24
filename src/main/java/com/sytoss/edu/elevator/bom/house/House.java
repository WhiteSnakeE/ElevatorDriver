package com.sytoss.edu.elevator.bom.house;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.house.floors.Floor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class House {
    @Getter
    private List<Shaft> shafts = new ArrayList<>();

    @Getter
    private List<Floor> floors = new ArrayList<>();

    @Getter
    @Setter
    private ElevatorDriver elevatorDriver;

    public Shaft moveSequenceToShaft() {

        if (elevatorDriver.getOrderSequenceOfStops().isEmpty()) {
            return null;
        }

        ArrayList<Shaft> freeShafts = new ArrayList<>();

        for (Shaft shaft : shafts) {
            if (shaft.getSequenceOfStops() == null) {
                freeShafts.add(shaft);
            }
        }

        if (freeShafts.isEmpty()) {
            return null;
        }

        Shaft nearestCabin = shafts.get(0);
        int floorNumber = getFirstStop();
        int min = Integer.MAX_VALUE;

        for (Shaft shaft : freeShafts) {
            if (Math.abs(floorNumber - shaft.getCabinPosition()) < min) {
                min = Math.abs(floorNumber - shaft.getCabinPosition());
                nearestCabin = shaft;
            }
        }

        nearestCabin.setSequenceOfStops(elevatorDriver.getOrderSequenceOfStops().get(0));
        elevatorDriver.removeSequenceFromOrder();
        return nearestCabin;
    }

    private int getFirstStop() {
        return elevatorDriver.getOrderSequenceOfStops().get(0).getStopFloors().get(0);
    }
}
