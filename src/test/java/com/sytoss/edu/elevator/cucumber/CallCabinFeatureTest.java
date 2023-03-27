package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.enums.EngineState;
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


    @Given("cabin with index {int} and Engine has EngineState {string} and Shaft has current position {int}")
    public void cabinWithIdAndEngineHasEngineStateAndShaftHasCurrentPosition (Integer cabinId, String engineState,
            Integer currentPosition) {
        for (Shaft shaft : getHouse().getShafts()) {
            shaft.setSequenceOfStops(null);
        }

        getHouse().getShafts().get(cabinId).setCabinPosition(currentPosition);
        getHouse().getShafts().get(cabinId).getEngine().setEngineState(EngineState.valueOf(engineState));
    }

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passengerOnFloorPressesUpFloorButtonWithDirection (Integer floorNumber, String direction) {
        String buttonDirection = (direction.equals("UPWARDS") ? "/up" : "/down");
        String url = "/api/floorButton/" + floorNumber + buttonDirection;
        response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
    }

    @Then("controller should create sequence of stops with floor {int} for Cabin with index {int} and Direction {string}")
    public void controllerShouldCreateSequenceOfStopsWithFloorAndIdAndDirection (Integer floorRequested,
            Integer shaftIndex, String direction) {
        Assertions.assertEquals(floorRequested, getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.valueOf(direction), getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
        Assertions.assertNull(getHouse().getShafts().get(0).getSequenceOfStops());

        verify(getFloorService()).goUpCabinRequest(floorRequested);
        verify(getFindNearestCabinCommand()).execute(null);
        verify(getPressUpButtonCommand()).execute(Mockito.any());
        verify(getHouse()).moveSequenceToShaft(getElevatorDriver());
        verify(getElevatorDriver()).addNewSequenceToOrder(floorRequested, Direction.valueOf(direction));
        verify(getElevatorDriver()).runCommands();
        verify(getElevatorDriver()).removeSequenceFromOrder();

    }
}
