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

//    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
//    public void passengerOnFloorPressesUpFloorButtonWithDirection(Integer floorNumber, String direction) {
//        String buttonDirection = (direction.equals("UPWARDS") ? "/up" : "/down");
//        String url = "/api/floorButton/" + floorNumber + buttonDirection;
//        ResponseEntity<String> response = doPost(url, null, String.class);
//        TestContext.getInstance().setResponse(response);
//        await(floorNumber);
//    }

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

    public void await(int num) {
        int time = num * OPEN_DOOR_TIME_SLEEP + CLOSE_DOOR_TIME_SLEEP + VISIT_FLOOR_TIME_SLEEP + MOVE_CABIN_TIME_SLEEP + 20 * num;
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

//    @When("passenger in house with id {int} on floor {int} presses UpFloorButton")
//    public void passengerInHouseWithIdOnFloorPressesUpFloorButton (int houseId, int floor) {
//        String url = "/api/house/" + houseId + "/floorButton/" + floor +  "/up";
//        ResponseEntity<String> response = doPost(url, null, String.class);
//        TestContext.getInstance().setResponse(response);
//        await(floor);
//
//    }

    @When("passenger in this house on floor {int} presses UpFloorButton")
    public void passenger_in_this_house_on_floor_presses_up_floor_button(Integer floorNumber) {
        String url = "/api/house/" + TestContext.getInstance().getHouseId() + "/floorButton/" + floorNumber +  "/up";
        ResponseEntity<String> response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
        await(floorNumber);
    }

    @When("start cabin in this house with index {int} moving sequence of stops to")
    public void startCabinInThisHouseWithIndexMovingSequenceOfStopsTo(int shaftIndex) {
        List<ShaftDTO> shaftDTOList = getShaftRepository().findByHouseDTOId(TestContext.getInstance().getHouseId());
        shaftDTOList.sort(Comparator.comparingLong(ShaftDTO::getId));
        Shaft shaft = getShaftConverter().fromDTO(shaftDTOList.get(shaftIndex));
        shaft.addShaftListener(new ElevatorDriver(getCommandManager()));

        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(TestContext.getInstance().getHouseId());
        House house = getHouseConverter().fromDTO(houseDTOOptional.get(), shaftDTOList);

        HashMap<String, Object> params = new HashMap<>();
        params.put(SHAFT_PARAM, shaft);
        params.put(HOUSE_PARAM, house);
        getCommandManager().getCommand(Command.MOVE_CABIN_COMMAND).execute(params);
        await(house.getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(house.getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().size() - 1));
    }
}
