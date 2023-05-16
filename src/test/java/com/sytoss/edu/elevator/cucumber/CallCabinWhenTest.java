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
        String url = "/api/house/" + TestContext.getInstance().getHousesId().get(0) + "/floorButton/" + floorNumber + "/up";
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
    @When("start cabin with index {int} moving sequence of stops to")
    public void startCabinWithIndexMovingSequenceOfStopsTo(Integer shaftIndex) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);
        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(TestContext.getInstance().getHousesId().get(0));
        House house = getHouseConverter().fromDTO(houseDTOOptional.get(),shaftDTOList);
        house.setElevatorDriver(new ElevatorDriver(getCommandManager()));
        Shaft shaft = getShaftConverter().fromDTO(shaftDTOList.get(shaftIndex));
        shaft.addShaftListener(house.getElevatorDriver());

        HashMap<String, Object> paramsExec = new HashMap<>();
        paramsExec.put("Shaft", shaft);

        paramsExec.put("Floors", house.getFloors());
        getCommandManager().getCommand(Command.MOVE_CABIN_COMMAND).execute(paramsExec);

        await(house.getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(house.getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().size() - 1));
    }

    @When("passenger in house {int} presses UpFloorButton on floor {int}")
    public void passengerInHousePressesUpFloorButtonOnFloor(int houseIndex, int floorNumber) {
        String url = "/api/house/" + TestContext.getInstance().getHousesId().get(houseIndex) + "/floorButton/" + floorNumber + "/up";
        ResponseEntity<String> response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
        await(floorNumber);
    }
}
