package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.enums.Direction;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.verify;

@Slf4j
public class CallCabinFeatureTest extends IntegrationTest {

    private ResponseEntity<String> response;

    @Given("shaft with index {int} has free cabin and cabin position {int}")
    public void shaftWithIdAndEngineHasEngineStateAndShaftHasCurrentPosition (Integer cabinIndex,
            Integer currentPosition) {

        getHouse().getShafts().get(cabinIndex).setSequenceOfStops(null);
        getHouse().getShafts().get(cabinIndex).setCabinPosition(currentPosition);
    }

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passengerOnFloorPressesUpFloorButtonWithDirection (Integer floorNumber, String direction) {
        String buttonDirection = (direction.equals("UPWARDS") ? "/up" : "/down");
        String url = "/api/floorButton/" + floorNumber + buttonDirection;
        response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
    }

    @Then("Shaft with index {int} should have sequence of stops with floor {int} and direction {string}")
    public void shaftShouldCreateSequenceOfStopsWithFloorAndIdAndDirection (Integer shaftIndex, Integer floorRequested,
            String direction) {
        Assertions.assertEquals(floorRequested, getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.valueOf(direction), getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());

        verify(getFloorService()).goUpCabinRequest(floorRequested);
        verify(getFindNearestCabinCommand()).execute(null);
        verify(getPressUpButtonCommand()).execute(Mockito.any());
        verify(getHouse()).moveSequenceToShaft(getElevatorDriver().getOrderSequenceOfStops());
        verify(getElevatorDriver()).addNewSequenceToOrder(floorRequested, Direction.valueOf(direction));
        verify(getElevatorDriver()).removeSequenceFromOrder();
    }

    @Then("Shaft with index {int} should not have sequence of stops")
    public void shaftShouldNotHaveSequenceOfStops (Integer shaftIndex) {
        Assertions.assertNull(getHouse().getShafts().get(shaftIndex).getSequenceOfStops());
    }
}
