package com.sytoss.edu.elevator.cucumberTests;

import com.sytoss.edu.elevator.ControllerIntegrationTest;
import com.sytoss.edu.elevator.bom.Direction;
import com.sytoss.edu.elevator.bom.EngineState;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;

public class CallCabinTest extends ControllerIntegrationTest  {
    private ResponseEntity<String> response;

    @Given("cabin with id {int} and Engine has EngineState {string} and Shaft has current position {int}")
    public void cabin_with_id_and_engine_has_engine_state_and_shaft_has_current_position(Integer cabinId, String engineState, Integer currentPosition) {
        getController().getShafts().get(cabinId).getEngine().setEngineState(EngineState.valueOf(engineState));
        getController().getShafts().get(cabinId).setCabinPosition(currentPosition);

    }

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passenger_on_floor_presses_up_floor_button_with_direction(Integer floorNumber, String string) {
        String url = "/api/floorButton/"+floorNumber+"/up";
        response = doPost(url ,null, String.class);
    }

    @Then("controller should create order with sequence of stops with floor {int} with index {int} and Direction {string}")
    public void controller_should_create_sequence_of_stops_with_floor_and_id_and_direction(Integer floorRequested, Integer shaftIndex, String direction) {
        Assertions.assertEquals(floorRequested,getController().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(0));
        Assertions.assertEquals(Direction.valueOf(direction),getController().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
    }
}
