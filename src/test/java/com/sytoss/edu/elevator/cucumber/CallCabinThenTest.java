package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.utils.JsonUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

@Slf4j
public class CallCabinThenTest extends IntegrationTest {

    @Then("Shaft with index {int} should not have sequence of stops")
    public void shaftShouldNotHaveSequenceOfStops(Integer shaftIndex) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);
        Shaft shaft = getShaftConverter().fromDTO(shaftDTOList.get(shaftIndex));
        Assertions.assertNull(shaft.getSequenceOfStops());
    }

    @Then("ElevatorDriver has sequence of stops with floor {int}")
    public void elevatorDriverHasSequenceOfStopsWithFloor(Integer floorNumber) {
        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(TestContext.getInstance().getHousesId().get(0));
        HouseDTO houseDTO = houseDTOOptional.get();
        List<SequenceOfStops> order = JsonUtil.stringJSONToOrderSequence(houseDTO.getOrderSequenceOfStops());

        Assertions.assertEquals(floorNumber, order.get(0).getStopFloors().get(0));
    }

    @Then("Shaft with index {int} should have sequence of stops with floor/floors {intList} and direction {string}")
    public void shaftWithIndexShouldHaveSequence(Integer shaftIndex, List<Integer> floors, String direction) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);
        Shaft shaft = getShaftConverter().fromDTO(shaftDTOList.get(shaftIndex));

        for (int i = 0; i < floors.size(); ++i) {
            Assertions.assertEquals(floors.get(i), shaft.getSequenceOfStops().getStopFloors().get(i));
            Assertions.assertEquals(Direction.valueOf(direction), shaft.getSequenceOfStops().getDirection());
        }
    }

    @Then("commands should have be invoked for shaft with id {int}: {stringList} for floor/floors {intList}")
    public void commandsShouldHaveBeInvokedForShaftWithIndexOpenDoorCheckOverweightCloseDoorForFloor(
            Integer shaftIndex, List<String> commands, List<Integer> stopFloors) {
        for (String command : commands) {
            Mockito.verify(getCommandManager().getCommand(command), times(stopFloors.size())).execute(Mockito.any());
        }
    }

    @Then("commands should have be invoked for shaft {int} in house {int}: {stringList} for floor/floors {intList}")
    public void commandsShouldHaveBeInvokedForShaftInHouseMoveCabinCommandVisitFloorCommandStopCabinCommandOpenDoorCommandCloseDoorCommandForFloor(int shaftIndex, int houseIndex, List<String> commands, List<Integer> floors) {
        for (String command : commands) {
            Mockito.verify(getCommandManager().getCommand(command), times(floors.size())).execute(argThat((arg) -> {
                Shaft shaft = (Shaft) (arg.get(SHAFT_PARAM));
                log.info(shaft.getId().toString());
                return shaft.getId().equals(getShaftId(shaftIndex, houseIndex));
            }));
        }
    }

    @And("shaft {int} in house {int} has cabin position {int}")
    public void shaftInHouseHasCabinPosition(int shaftIndex, int houseIndex, int cabinPosition) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseId(houseIndex);
        Assertions.assertEquals(cabinPosition, shaftDTOList.get(shaftIndex).getCabinPosition());
    }

    @Then("commands should have be invoked for shaft with index {int}: {stringList} for floor/floors {intList}")
    public void commandsShouldHaveBeInvokedForShaftWithIndexOpenDoorCommandCloseDoorCommandForFloor
            (int shaftIndex, List<String> commands, List<Integer> floors) {
        for (String command : commands) {
            Mockito.verify(getCommandManager().getCommand(command), times(floors.size())).execute(any());
        }
    }
}
