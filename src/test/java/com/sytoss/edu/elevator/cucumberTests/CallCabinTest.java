package com.sytoss.edu.elevator.cucumberTests;

import com.sytoss.edu.elevator.ControllerIntegrationTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CallCabinTest extends ControllerIntegrationTest {
    @Given("cabin with id {int} and Engine has EngineState {string} and Shaft has current position {int}")
    public void cabin_with_id_and_engine_has_engine_state_and_shaft_has_current_position(Integer int1, String string, Integer int2) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passenger_on_floor_presses_up_floor_button_with_direction(Integer int1, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("controller should create sequence of stops with floor {int} and id {int} and Direction {string}")
    public void controller_should_create_sequence_of_stops_with_floor_and_id_and_direction(Integer int1, Integer int2, String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
