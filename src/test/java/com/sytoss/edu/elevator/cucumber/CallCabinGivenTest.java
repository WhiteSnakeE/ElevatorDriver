package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.params.HouseParams;
import com.sytoss.edu.elevator.utils.JsonUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.IntStream;

import static com.sytoss.edu.elevator.bom.enums.DoorState.CLOSED;
import static com.sytoss.edu.elevator.bom.enums.EngineState.STAYING;
import static com.sytoss.edu.elevator.bom.enums.OverWeightState.NOT_OVERWEIGHT;
import static com.sytoss.edu.elevator.commands.Command.MOVE_CABIN_COMMAND;
import static com.sytoss.edu.elevator.commands.CommandManager.HOUSE_PARAM;
import static com.sytoss.edu.elevator.commands.CommandManager.SHAFT_PARAM;

@Slf4j
public class CallCabinGivenTest extends IntegrationTest {

    @Given("shaft with index {int} has free cabin and cabin position {int}")
    public void shaftWithIdAndEngineHasEngineStateAndShaftHasCurrentPosition(Integer shaftIndex,
                                                                             Integer cabinPosition) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);

        shaftDTOList.get(shaftIndex).setSequenceOfStops(null);
        shaftDTOList.get(shaftIndex).setCabinPosition(cabinPosition);

        getShaftRepository().save(shaftDTOList.get(shaftIndex));
    }

    @Given("shaft with index {int} has sequence of stops with floor/floors {intList} and Direction {string} and cabin position {int}")
    public void shaftWithIndexAndSequenceOfStopsAndDirectionAndCabinPosition(Integer shaftIndex,List<Integer> floorsNumbers,
                                                                             String direction, Integer cabinPosition) {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setDirection(Direction.valueOf(direction));
        sequence.setStopFloors(floorsNumbers);

        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);

        shaftDTOList.get(shaftIndex).setSequenceOfStops(JsonUtil.sequenceToStringInJSON(sequence));
        shaftDTOList.get(shaftIndex).setCabinPosition(cabinPosition);

        getShaftRepository().save(shaftDTOList.get(shaftIndex));

    }

    @Given("All shaft are free and no sequence of stops in queue")
    public void allShaftFreeAndNoSequence() {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);
        shaftDTOList.forEach(shaftDTO -> shaftDTO.setSequenceOfStops(null));

        getShaftRepository().saveAll(shaftDTOList);
    }
//
//    @Given("shaft with index {int} has sequence of stops with floors {intList} and Direction {string} and cabin position {int}")
//    public void shaftWithIndexHasSequenceOfStopsWithFloorAndDirectionAndCabinPosition(Integer shaftIndex,
//                                                                                      List<Integer> floors, String direction, Integer cabinPosition) {
//        SequenceOfStops sequence = new SequenceOfStops();
//        sequence.setDirection(Direction.valueOf(direction));
//        sequence.setStopFloors(floors);
//
//        getHouse().getShafts().get(shaftIndex).setSequenceOfStops(sequence);
//        setCabinPositionTest(getHouse().getShafts().get(shaftIndex), cabinPosition);
//    }
//

    @Given("house {int} with numberOfFloors {int} and numberOfShafts {int}")
    public void houseWithNumberOfFloorsAndNumberOfShafts(int houseIndex, int numberOfFloors, int numberOfShafts) {
        HouseDTO houseDTO = HouseDTO.builder().numberOfFloors(numberOfFloors).numberOfShafts(numberOfShafts).build();

        List<ShaftDTO> shaftDTOList = new ArrayList<>();
        for (int i = 0; i < numberOfShafts; i++) {
            shaftDTOList.add(ShaftDTO.builder()
                    .houseDTO(houseDTO)
                    .overweightState(NOT_OVERWEIGHT)
                    .engineState(STAYING)
                    .doorState(CLOSED)
                    .cabinPosition(1).build());
        }
        houseDTO.setShafts(shaftDTOList);

        HouseDTO createdHouseDTO = getHouseRepository().save(houseDTO);
        TestContext.getInstance().getHousesId().put(houseIndex, createdHouseDTO.getId());
        getShaftRepository().saveAll(shaftDTOList);
    }

    @And("shaft {int} in house {int} has free cabin and cabin position {int}")
    public void shaftInHouseHasFreeCabinAndCabinPosition(int shaftIndex, int houseIndex, int cabinPosition) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseId(houseIndex);

        shaftDTOList.get(shaftIndex).setSequenceOfStops(null);
        shaftDTOList.get(shaftIndex).setCabinPosition(cabinPosition);

        getShaftRepository().save(shaftDTOList.get(shaftIndex));
    }

    @And("shaft {int} in house {int} has sequence of stops with floors {intList} and Direction {string} and cabin position {int}")
    public void shaftInHouseHasSequenceOfStopsWithFloorsAndDirectionAndCabinPosition(int shaftIndex, int houseIndex, List<Integer> floors, String direction, int cabinPosition) {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseId(houseIndex);

        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(floors);
        sequence.setDirection(Direction.valueOf(direction));
        shaftDTOList.get(shaftIndex).setSequenceOfStops(JsonUtil.sequenceToStringInJSON(sequence));
        shaftDTOList.get(shaftIndex).setCabinPosition(cabinPosition);
        getShaftRepository().save(shaftDTOList.get(shaftIndex));

        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(TestContext.getInstance().getHousesId().get(houseIndex));
        HashMap<String, Object> params = new HashMap<>();
        params.put(SHAFT_PARAM, getShaftConverter().fromDTO(shaftDTOList.get(shaftIndex)));
        params.put(HOUSE_PARAM, getHouseConverter().fromDTO(houseDTOOptional.get(), shaftDTOList));
        getCommandManager().getCommand(MOVE_CABIN_COMMAND).execute(params);
        await(floors.get(floors.size() - 1));
    }

    @Given("house {int} with numberOfFloors {int} and numberOfShafts {int} exists in database")
    public void houseExistsInDatabase(int houseIndex, int numberOfFloors, int numberOfShafts) {
        HouseDTO houseDTO = HouseDTO.builder().numberOfFloors(numberOfFloors).numberOfShafts(numberOfShafts).build();

        List<ShaftDTO> shaftDTOList = new ArrayList<>();
        for (int i = 0; i < numberOfShafts; i++) {
            shaftDTOList.add(ShaftDTO.builder()
                    .houseDTO(houseDTO)
                    .overweightState(NOT_OVERWEIGHT)
                    .engineState(STAYING)
                    .doorState(CLOSED)
                    .cabinPosition(1).build());
        }
        houseDTO.setShafts(shaftDTOList);

        HouseDTO createdHouseDTO = getHouseRepository().save(houseDTO);
        TestContext.getInstance().getHousesId().put(houseIndex, createdHouseDTO.getId());
        getShaftRepository().saveAll(shaftDTOList);
    }
}