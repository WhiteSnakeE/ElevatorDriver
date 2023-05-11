package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.params.HouseParams;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
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

    @Given("house with id {int} is created in database")
    public void houseWithIdIsCreatedInDatabase (int houseId) {
        HouseParams houseParams = HouseParams.builder().numberOfFloors(16).numberOfShafts(2).build();
        getHouseService().saveRequest(houseParams);
        log.info("houseWithIdIsCreatedInDatabase {} ",houseParams);
    }

    @And("house with id {long} has shaft with id {long} and this shaft has free cabin and cabin position {int}")
    public void houseWithIdHasShaftWithIndexAndThisShaftHasFreeCabinAndCabinPosition (Long houseId, Long shaftId, int cabinPosition) {
        Optional<HouseDTO> houseDTOOptional = getHouseRepository().findById(houseId);
        HouseDTO houseDTO = houseDTOOptional.get();
        getShaftRepository().updateShaftByShaftIdAndHouseId(shaftId,houseDTO,cabinPosition);

    }
}
