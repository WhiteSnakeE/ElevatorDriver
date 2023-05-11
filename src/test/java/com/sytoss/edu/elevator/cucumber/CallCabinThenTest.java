package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CallCabinThenTest extends IntegrationTest {

    //    @Then("Shaft with index {int} should have sequence of stops with floor {int} and direction {string}")
    //    public void shaftShouldCreateSequenceOfStopsWithFloorAndIdAndDirection(Integer shaftIndex, Integer floorRequested,
    //                                                                           String direction) {
    //        Assertions.assertEquals(floorRequested, getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(0));
    //        Assertions.assertEquals(Direction.valueOf(direction), getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
    //    }
    //
    //    @Then("Shaft with index {int} should not have sequence of stops")
    //    public void shaftShouldNotHaveSequenceOfStops(Integer shaftIndex) {
    //        Assertions.assertNull(getHouse().getShafts().get(shaftIndex).getSequenceOfStops());
    //    }
    //
    //    @Then("ElevatorDriver has sequence of stops with floor {int}")
    //    public void elevatorDriverHasSequenceOfStopsWithFloor(Integer floorNumber) {
    //        Assertions.assertEquals(floorNumber, getElevatorDriver().getOrderSequenceOfStops().get(0).getStopFloors().get(0));
    //    }
    //
    //    @Then("Shaft with index {int} should have sequence of stops with floors {intList} and direction {string}")
    //    public void shaftWithIndexShouldHaveSequence(Integer shaftIndex, List<Integer> floors, String direction) {
    //        for (int i = 0; i < floors.size(); ++i) {
    //            Assertions.assertEquals(floors.get(i), getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getStopFloors().get(i));
    //            Assertions.assertEquals(Direction.valueOf(direction), getHouse().getShafts().get(shaftIndex).getSequenceOfStops().getDirection());
    //        }
    //    }

    //    @Then("commands should have be invoked for shaft with id {int}: {stringList} for floor/floors {intList}")
    //    public void commandsShouldHaveBeInvokedForShaftWithIndexOpenDoorCheckOverweightCloseDoorForFloor(
    //            Integer shaftIndex, List<String> commands, List<Integer> stopFloors) {
    //        for (String command : commands) {
    //            Mockito.verify(getCommandManager().getCommand(command), times(stopFloors.size())).execute(Mockito.any());
    //        }
    //    }

    @And("shaft with id {long} of house with id {long} has cabin position {int}")
    public void shaftWithIndexOfHouseWithIdHasCabinPosition (Long shaftId, Long houseId, int cabinPosition) {
        HashMap<String, Object> params = getShaftAndHouse(houseId, shaftId);
        Shaft shaft = (Shaft) params.get(CommandManager.SHAFT_PARAM);
        House house = (House) params.get(CommandManager.HOUSE_PARAM);

        Assertions.assertEquals(shaftId, shaft.getId());
        Assertions.assertEquals(houseId, house.getId());
        Assertions.assertEquals(cabinPosition, shaft.getCabinPosition());
    }

    @Then("commands should have be invoked for house with id {long} and shaft with id {long}: {stringList} for floor/floors {intList}")
    public void commandsShouldHaveBeInvokedForHouseWithIdAndShaftWithIndexMoveCabinCommandVisitFloorCommandStopCabinCommandOpenDoorCommandCloseDoorCommandForFloor (
            Long houseId, Long shaftId, List<String> commands, List<Integer> floors) {
        for (String command : commands) {
            Mockito.verify(getCommandManager().getCommand(command), times(floors.size())).execute(any());
        }
    }

    private HashMap<String, Object> getShaftAndHouse (Long houseId, Long shaftId) {
        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(houseId);
        Optional<ShaftDTO> shaftDTOOptional = getShaftRepository().findById(shaftId);
        ShaftDTO shaftDTO = shaftDTOOptional.get();
        HouseDTO houseDTO = houseDTOOptional.get();
        Shaft shaft = getShaftConverter().fromDTO(shaftDTO);
        House house = getHouseConverter().fromDTO(houseDTO, List.of(shaftDTO));
        HashMap<String, Object> params = new HashMap<>();
        params.put(CommandManager.HOUSE_PARAM, house);
        params.put(CommandManager.SHAFT_PARAM, shaft);
        return params;
    }
}
