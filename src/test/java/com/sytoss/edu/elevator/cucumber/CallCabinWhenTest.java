package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.sytoss.edu.elevator.HouseThreadPool.*;
import static com.sytoss.edu.elevator.commands.CommandManager.HOUSE_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;

@Slf4j
public class CallCabinWhenTest extends IntegrationTest {

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passengerOnFloorPressesUpFloorButtonWithDirection(Integer floorNumber, String direction) {
        String buttonDirection = (direction.equals("UPWARDS") ? "/up" : "/down");
        String url = "/api/floorButton/" + floorNumber + buttonDirection;
        ResponseEntity<String> response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
        await(floorNumber);
    }

//    @When("call process findNearestCabin for floor {int} with direction {string}")
//    public void callProcessFindNearestCabinForFloor(int floor, String direction) {
//        getElevatorDriver().addNewSequenceToOrder(floor, Direction.valueOf(direction));
//        Shaft shaft = getHouse().findNearestCabin(getElevatorDriver().getOrderSequenceOfStops());
//        if (shaft != null) {
//            shaft.updateSequence(getElevatorDriver());
//        }
//    }
//
//    @When("start cabin with index {int} moving sequence of stops to")
//    public void startCabinWithIndexMovingSequenceOfStopsTo(Integer shaftIndex) {
//        HashMap<String, Object> paramsExec = new HashMap<>();
//        paramsExec.put("Shaft", getHouse().getShafts().get(shaftIndex));
//        paramsExec.put("Floors", getHouse().getFloors());
//        getCommandManager().getCommand(Command.MOVE_CABIN_COMMAND).execute(paramsExec);
//        await(getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().size() - 1));
//    }

    @When("passenger in house {int} presses UpFloorButton on floor {int}")
    public void passengerInHousePressesUpFloorButtonOnFloor(int houseIndex, int floorNumber) {
        String url = "/api/house/" + TestContext.getInstance().getHousesId().get(houseIndex) + "/floorButton/" + floorNumber + "/up";
        ResponseEntity<String> response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
        await(floorNumber);
    }
}
