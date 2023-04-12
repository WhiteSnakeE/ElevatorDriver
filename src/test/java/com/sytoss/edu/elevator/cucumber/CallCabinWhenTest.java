package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.commands.Command;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class CallCabinWhenTest extends IntegrationTest {

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passengerOnFloorPressesUpFloorButtonWithDirection (Integer floorNumber, String direction) {
        String buttonDirection = (direction.equals("UPWARDS") ? "/up" : "/down");
        String url = "/api/floorButton/" + floorNumber + buttonDirection;
        ResponseEntity<String> response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
    }
    @When("call process findNearestCabin for floor {int} with direction {string}")
    public void callProcessFindNearestCabinForFloor(int floor,String direction){
        getElevatorDriver().addNewSequenceToOrder(floor, Direction.valueOf(direction));
        getHouse().moveSequenceToShaft(getElevatorDriver());
    }

    @When("start cabin with index {int} moving sequence of stops to")
    public void startCabinWithIndexMovingSequenceOfStopsTo(Integer shaftIndex) {
        HashMap<String, Object> paramsExec = new HashMap<>();
        paramsExec.put("Shaft", getHouse().getShafts().get(shaftIndex));
        getCommandManager().getCommand(Command.MOVE_CABIN_COMMAND).execute(paramsExec);
    }
}
