package com.sytoss.edu.elevator.cucumberTests;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.EngineState;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@Slf4j
public class CallCabinFeatureTest extends IntegrationTest {
    private ResponseEntity<String> response;

    @Given("cabin with id {int} and Engine has EngineState {string} and Shaft has current position {int}")
    public void cabin_with_id_and_engine_has_engine_state_and_shaft_has_current_position (Integer cabinId,
            String engineState, Integer currentPosition) {
        getController().getShafts().get(cabinId).getEngine().setEngineState(EngineState.valueOf(engineState));
        getController().getShafts().get(cabinId).setCabinPosition(currentPosition);
    }

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passenger_on_floor_presses_up_floor_button_with_direction (Integer floorNumber, String string) {
        String url = "/api/floorButton/" + floorNumber + "/up";
        response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);

    }

    @Then("controller should create order with sequence of stops with floor {int} with index {int} and Direction {string}")
    public void controller_should_create_sequence_of_stops_with_floor_and_id_and_direction (Integer floorRequested,
            Integer shaftIndex, String direction) {

        log.info("responseIs called with " + response);
        assertEquals(200, TestContext.getInstance().getResponse().getStatusCode().value());

        verify(getController(), times(1)).addSequenceToOrder(Mockito.any());
        verify(getController(),times(1)).runCommands();

        Assertions.assertEquals(floorRequested, getController().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.valueOf(direction), getController().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
    }
}
