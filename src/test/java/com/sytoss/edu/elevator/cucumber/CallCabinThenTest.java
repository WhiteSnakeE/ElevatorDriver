package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.enums.Direction;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class CallCabinThenTest extends IntegrationTest {
    @Then("Shaft with index {int} should have sequence of stops with floor {int} and direction {string}")
    public void shaftShouldCreateSequenceOfStopsWithFloorAndIdAndDirection (Integer shaftIndex, Integer floorRequested,
            String direction) {
        Assertions.assertEquals(floorRequested, getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.valueOf(direction), getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
    }

    @Then("Shaft with index {int} should not have sequence of stops")
    public void shaftShouldNotHaveSequenceOfStops (Integer shaftIndex) {
        Assertions.assertNull(getHouse().getShafts().get(shaftIndex).getSequenceOfStops());
    }

    @Then("ElevatorDriver has sequence of stops with floor {int}")
    public void elevatorDriverHasSequenceOfStopsWithFloor(Integer floorNumber) {
        Assertions.assertEquals(floorNumber, getElevatorDriver().getOrderSequenceOfStops().get(0).getStopFloors().get(0));
        getElevatorDriver().getOrderSequenceOfStops().clear();
    }
}
