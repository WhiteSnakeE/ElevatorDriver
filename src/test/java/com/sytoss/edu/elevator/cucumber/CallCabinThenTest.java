package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.enums.Direction;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.atLeastOnce;

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
    }

    @Then("Shaft with index {int} should have sequence of stops with floors {intList} and direction {string}")
    public void shaftWithIndexShouldHaveSequence(Integer shaftIndex, List<Integer> floors,String direction){
        for (int i=0;i<floors.size();++i){
            Assertions.assertEquals(floors.get(i),getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(i));
            Assertions.assertEquals(Direction.valueOf(direction),getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
        }
    }

    @Then("commands should have be invoked for shaft with index {int}: {stringList}")
    public void commandsShouldHaveBeInvokedForShaftWithIndexOpenDoorCheckOverweightCloseDoorForFloor (Integer shaftIndex,
            List<String>commands) {
        for (String command : commands) {
            Mockito.verify(getCommandManager().getCommand(command), atLeastOnce()).execute(Mockito.any());
        }
    }
}
