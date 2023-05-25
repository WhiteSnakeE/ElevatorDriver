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
import com.sytoss.edu.elevator.utils.JsonUtil;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static com.sytoss.edu.elevator.commands.CommandManager.*;

@Slf4j
public class CallCabinWhenTest extends IntegrationTest {

    @When("passenger on floor {int} presses UpFloorButton with direction {string}")
    public void passengerOnFloorPressesUpFloorButtonWithDirection(Integer floorNumber, String direction) {
        String url = "/api/house/" + getHouseId(0) + "/floorButton/" + floorNumber + "/up";
        ResponseEntity<String> response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
        await(floorNumber);
    }

    @When("call process findNearestCabin for floor {int} with direction {string}")
    public void callProcessFindNearestCabinForFloor(int floor, String direction) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);
        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(getHouseId(0));
        House house = getHouseConverter().fromDTO(houseDTOOptional.get(), shaftDTOList);
        house.getElevatorDriver().addNewSequenceToOrder(floor, Direction.valueOf(direction));
        Shaft shaft = house.findNearestCabin();
        if (shaft != null) {
            shaft.addShaftListener(house.getElevatorDriver());
            shaft.updateSequence(house.getElevatorDriver());
            getShaftRepository().updateSequenceById(shaft.getId(), JsonUtil.sequenceToStringInJSON(shaft.getSequenceOfStops()));
        }
        getHouseRepository().updateOrderById(house.getId(), JsonUtil.orderSequenceToStringInJSON(house.getElevatorDriver().getOrderSequenceOfStops()));
    }

    @When("start cabin with index {int} moving sequence of stops to")
    public void startCabinWithIndexMovingSequenceOfStopsTo(Integer shaftIndex) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);
        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(getHouseId(0));
        House house = getHouseConverter().fromDTO(houseDTOOptional.get(), shaftDTOList);
        Shaft shaft = getShaftConverter().fromDTO(shaftDTOList.get(shaftIndex));
        shaft.addShaftListener(house.getElevatorDriver());

        HashMap<String, Object> paramsExec = new HashMap<>();
        paramsExec.put(SHAFT_PARAM, shaft);
        paramsExec.put(DIRECTION_PARAM, shaft.getSequenceOfStops().getDirection());
        paramsExec.put(FLOORS_PARAM, house.getFloors());
        getCommandManager().getCommand(Command.MOVE_CABIN_COMMAND).execute(paramsExec);

        await(house.getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(house.getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().size() - 1));
    }

    @When("passenger in house {int} presses UpFloorButton on floor {int}")
    public void passengerInHousePressesUpFloorButtonOnFloor(int houseIndex, int floorNumber) {
        String url = "/api/house/" + getHouseId(houseIndex) + "/floorButton/" + floorNumber + "/up";
        ResponseEntity<String> response = doPost(url, null, String.class);
        TestContext.getInstance().setResponse(response);
        await(floorNumber);
    }
}
