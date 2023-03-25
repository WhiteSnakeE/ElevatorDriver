//package com.sytoss.edu.elevator.cucumber;
//
//import com.sytoss.edu.elevator.IntegrationTest;
//import com.sytoss.edu.elevator.TestContext;
//import com.sytoss.edu.elevator.bom.enums.Direction;
//import com.sytoss.edu.elevator.bom.enums.EngineState;
//import com.sytoss.edu.elevator.bom.SequenceOfStops;
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Assertions;
//import org.mockito.Mockito;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//
//@Slf4j
//public class CallCabinFeatureTest extends IntegrationTest {
//    private ResponseEntity<String> response;
//
//    @Given("cabin with index {int} and Engine has EngineState {string} and Shaft has current position {int}")
//    public void cabinWithIdAndEngineHasEngineStateAndShaftHasCurrentPosition (Integer cabinId, String engineState,
//            Integer currentPosition) {
//        getElevatorDriver().getShafts().get(cabinId).getEngine().setEngineState(EngineState.valueOf(engineState));
//        getElevatorDriver().getShafts().get(cabinId).setCabinPosition(currentPosition);
//    }
//
//    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
//    public void passengerOnFloorPressesUpFloorButtonWithDirection (Integer floorNumber, String direction) {
//        String buttonDirection = (direction.equals("UPWARDS") ? "/up" : "/down");
//        String url = "/api/floorButton/" + floorNumber + buttonDirection;
//        response = doPost(url, null, String.class);
//        TestContext.getInstance().setResponse(response);
//
//    }
//
//    @Then("controller should create sequence of stops with floor {int} for Cabin with index {int} and Direction {string}")
//    public void controllerShouldCreateSequenceOfStopsWithFloorAndIdAndDirection (Integer floorRequested,
//            Integer shaftIndex, String direction) {
//
//        log.info("responseIs called with " + response);
//        assertEquals(200, TestContext.getInstance().getResponse().getStatusCode().value());
//
//        SequenceOfStops expectedSequenceOfStops = new SequenceOfStops();
//        expectedSequenceOfStops.getStopFloors().add(floorRequested);
//        expectedSequenceOfStops.setDirection(Direction.UPWARDS);
//
//        verify(getElevatorDriver()).addSequenceToOrder(Mockito.any());
//        verify(getElevatorDriver()).runCommands();
//        verify(getElevatorDriver()).executeCommand("findNearestCabin", null);
//
//        Assertions.assertEquals(floorRequested, getElevatorDriver().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(0));
//        Assertions.assertEquals(Direction.valueOf(direction), getElevatorDriver().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
//
//        Assertions.assertNull(getElevatorDriver().getShafts().get(0).getSequenceOfStops());
//    }
//}
