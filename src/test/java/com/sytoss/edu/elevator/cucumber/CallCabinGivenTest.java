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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static com.sytoss.edu.elevator.bom.enums.DoorState.CLOSED;
import static com.sytoss.edu.elevator.bom.enums.EngineState.STAYING;
import static com.sytoss.edu.elevator.bom.enums.OverWeightState.NOT_OVERWEIGHT;

@Slf4j
public class CallCabinGivenTest extends IntegrationTest {

//    @Given("shaft with index {int} has free cabin and cabin position {int}")
//    public void shaftWithIdAndEngineHasEngineStateAndShaftHasCurrentPosition(Integer cabinIndex,
//                                                                             Integer currentPosition) {
//        getHouse().getShafts().get(cabinIndex).setSequenceOfStops(null);
//        setCabinPositionTest(getHouse().getShafts().get(cabinIndex), currentPosition);
//    }
//
//    @Given("shaft with index {int} has sequence of stops with floor {int} and Direction {string} and cabin position {int}")
//    public void shaftWithIndexAndSequenceOfStopsAndDirectionAndCabinPosition(Integer shaftIndex, Integer floorNumber,
//                                                                             String direction, Integer cabinPosition) {
//        SequenceOfStops sequence = new SequenceOfStops();
//        sequence.setDirection(Direction.valueOf(direction));
//        sequence.setStopFloors(List.of(floorNumber));
//
//        setCabinPositionTest(getHouse().getShafts().get(shaftIndex), cabinPosition);
//        getHouse().getShafts().get(shaftIndex).setSequenceOfStops(sequence);
//    }
//
//    @Given("All shaft are free and no sequence of stops in queue")
//    public void allShaftFreeAndNoSequence() {
//        for (Shaft shaft : getHouse().getShafts()) {
//            shaft.setSequenceOfStops(null);
//        }
//        getElevatorDriver().getOrderSequenceOfStops().clear();
//    }
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
//    private void setCabinPositionTest(Shaft shaft, int currentPosition) {
//        shaft.getShaftListeners().clear();
//        shaft.setCabinPosition(currentPosition);
//        shaft.addShaftListener(getElevatorDriver());
//    }

//    @Given("house with id {int} is created in database")
//    public void houseWithIdIsCreatedInDatabase (int houseId) {
//        HouseParams houseParams = HouseParams.builder().numberOfFloors(16).numberOfShafts(2).build();
//        getHouseService().saveRequest(houseParams);
//        log.info("houseWithIdIsCreatedInDatabase {} ",houseParams);
//    }
//
//    @And("house with id {long} has shaft with id {long} and this shaft has free cabin and cabin position {int}")
//    public void houseWithIdHasShaftWithIndexAndThisShaftHasFreeCabinAndCabinPosition (Long houseId, Long shaftId, int cabinPosition) {
//        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(houseId);
//        HouseDTO houseDTO = houseDTOOptional.get();
//        getShaftRepository().updateShaftByShaftIdAndHouseId(shaftId,houseDTO,cabinPosition);
//
//    }

    @Given("house with {int} floors and {int} shafts exists in database")
    public void new_house_with_floors_and_shafts_exists_in_database(Integer numberOfFloors, Integer numberOfShafts) {
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
        TestContext.getInstance().setHouseId(createdHouseDTO.getId());
        getShaftRepository().saveAll(shaftDTOList);
    }

    @Given("shaft with index {int} in this house has free cabin and cabin position {int}")
    public void shaft_with_index_in_this_house_has_free_cabin_and_cabin_position(Integer shaftIndex, Integer cabinPosition) {
        List<ShaftDTO> shaftDTOList = getShaftRepository().findByHouseDTOId(TestContext.getInstance().getHouseId());
        shaftDTOList.sort(Comparator.comparingLong(ShaftDTO::getId));
        shaftDTOList.get(shaftIndex).setSequenceOfStops(null);
        shaftDTOList.get(shaftIndex).setCabinPosition(cabinPosition);
        getShaftRepository().save(shaftDTOList.get(shaftIndex));
    }

    @Given("All shaft in this house are free and no sequence of stops in queue")
    public void allShaftInThisHouseAreFreeAndNoSequenceOfStopsInQueue() {
        List<ShaftDTO> shaftDTOList = getShaftRepository().findByHouseDTOId(TestContext.getInstance().getHouseId());
        shaftDTOList.sort(Comparator.comparingLong(ShaftDTO::getId));
        shaftDTOList.forEach(shaftDTO -> shaftDTO.setSequenceOfStops(null));
        getShaftRepository().saveAll(shaftDTOList);
    }

    @And("shaft with index {int} in this house has sequence of stops with floor/floors {intList} and Direction {string} and cabin position {int}")
    public void shaftWithIndexInThisHouseHasSequenceOfStopsWithFloorsAndDirectionAndCabinPosition(int shaftIndex, List<Integer> floors, String direction, Integer cabinPosition) {
        SequenceOfStops sequence = new SequenceOfStops();
        sequence.setStopFloors(floors);
        sequence.setDirection(Direction.valueOf(direction));

        List<ShaftDTO> shaftDTOList = getShaftRepository().findByHouseDTOId(TestContext.getInstance().getHouseId());
        shaftDTOList.sort(Comparator.comparingLong(ShaftDTO::getId));
        shaftDTOList.get(shaftIndex).setSequenceOfStops(JsonUtil.sequenceToStringInJSON(sequence));
        shaftDTOList.get(shaftIndex).setCabinPosition(cabinPosition);

        getShaftRepository().save(shaftDTOList.get(shaftIndex));
    }
}
