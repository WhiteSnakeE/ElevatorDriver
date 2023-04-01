package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import io.cucumber.java.en.Given;

import java.util.List;

public class CallCabinGivenTest extends IntegrationTest {

    @Given("shaft with index {int} has free cabin and cabin position {int}")
    public void shaftWithIdAndEngineHasEngineStateAndShaftHasCurrentPosition (Integer cabinIndex,
            Integer currentPosition) {
        getHouse().getShafts().get(cabinIndex).setSequenceOfStops(null);
        getHouse().getShafts().get(cabinIndex).setCabinPosition(currentPosition);
    }

    @Given("shaft with index {int} has sequence of stops with floor {int} and Direction {string} and cabin position {int}")
    public void shaftWithIndexAndSequenceOfStopsAndDirectionAndCabinPosition(Integer shaftIndex, Integer floorNumber,
            String direction, Integer cabinPosition) {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.valueOf(direction));
        sequence.setStopFloors(List.of(floorNumber));

        getHouse().getShafts().get(shaftIndex).setCabinPosition(cabinPosition);
        getHouse().getShafts().get(shaftIndex).setSequenceOfStops(sequence);
    }

    @Given("All shaft are free and no sequence of stops in queue")
    public void allShaftFreeAndNoSequence(){
        for (Shaft shaft: getHouse().getShafts()){
            shaft.setSequenceOfStops(null);
        }
        getElevatorDriver().getOrderSequenceOfStops().clear();
    }

    @Given("shaft with index {int} has sequence of stops with floors {intList} and Direction {string} and cabin position {int}")
    public void shaftWithIndexHasSequenceOfStopsWithFloorAndDirectionAndCabinPosition(Integer shaftIndex,
            List<Integer> floors, String direction, Integer cabinPosition) {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.valueOf(direction));
        sequence.setStopFloors(floors);

        getHouse().getShafts().get(shaftIndex).setSequenceOfStops(sequence);
        getHouse().getShafts().get(shaftIndex).setCabinPosition(cabinPosition);
    }
}
