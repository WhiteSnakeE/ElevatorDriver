package com.sytoss.edu.elevator.cucumber;

import com.sytoss.edu.elevator.IntegrationTest;
import com.sytoss.edu.elevator.TestContext;
import com.sytoss.edu.elevator.bom.SequenceOfStops;
import com.sytoss.edu.elevator.bom.Shaft;
import com.sytoss.edu.elevator.bom.enums.Direction;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.Command;
import com.sytoss.edu.elevator.commands.CommandManager;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import com.sytoss.edu.elevator.utils.JsonUtil;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sytoss.edu.elevator.bom.enums.DoorState.CLOSED;
import static com.sytoss.edu.elevator.bom.enums.EngineState.STAYING;
import static com.sytoss.edu.elevator.bom.enums.OverWeightState.NOT_OVERWEIGHT;

@Slf4j
public class CallCabinGivenTest extends IntegrationTest {

    @Given("shaft with index {int} has free cabin and cabin position {int}")
    public void shaftHasFreeCabinAndCabinPosition(Integer shaftIndex,
                                                  Integer cabinPosition) {
        shaftInHouseHasFreeCabinAndCabinPosition(shaftIndex, 0, cabinPosition);
    }

    @Given("shaft with index {int} has sequence of stops with floor/floors {intList} and Direction {string} and cabin position {int}")
    public void shaftWithIndexAndSequenceOfStopsAndDirectionAndCabinPosition(Integer shaftIndex, List<Integer> floorsNumbers,
                                                                             String direction, Integer cabinPosition) {
        shaftInHouseHasSequenceOfStopsWithFloorsAndDirectionAndCabinPosition(shaftIndex, 0, floorsNumbers, direction, cabinPosition);

    }

    @Given("All shaft are free and no sequence of stops in queue")
    public void allShaftFreeAndNoSequence() {
        List<ShaftDTO> shaftDTOList = getSortedShaftsByHouseIndex(0);
        shaftDTOList.forEach(shaftDTO -> shaftDTO.setSequenceOfStops(null));

        getShaftRepository().saveAll(shaftDTOList);
    }

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
        TestContext.getInstance().getHouseIds().put(houseIndex, createdHouseDTO.getId());

        List<ShaftDTO> createdShaftDTOs = getShaftRepository().saveAll(houseDTO.getShafts());
        TestContext.getInstance().getShaftIds().put(houseIndex, createdShaftDTOs.stream().map(ShaftDTO::getId).toList());
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
        TestContext.getInstance().getHouseIds().put(houseIndex, createdHouseDTO.getId());

        List<ShaftDTO> createdShaftDTOs = getShaftRepository().saveAll(houseDTO.getShafts());
        TestContext.getInstance().getShaftIds().put(houseIndex, createdShaftDTOs.stream().map(ShaftDTO::getId).toList());
    }

    @And("all shafts in house {int} is moving")
    public void shaftInHouseIsMoving(int houseIndex) {
        House house = getHouseService().getHouseById(getHouseId(houseIndex));
        for (Shaft shaft : house.getShafts()) {
            if (shaft.getSequenceOfStops() != null) {
                getHouseThreadPool().getFixedThreadPool().submit(() -> {
                    HashMap<String, Object> paramsActivateCommand = new HashMap<>();
                    paramsActivateCommand.put(CommandManager.SHAFT_PARAM, shaft);
                    paramsActivateCommand.put(CommandManager.FLOORS_PARAM, house.getFloors());
                    getCommandManager().getCommand(Command.MOVE_CABIN_COMMAND).execute(paramsActivateCommand);
                });
            }
        }
    }
}