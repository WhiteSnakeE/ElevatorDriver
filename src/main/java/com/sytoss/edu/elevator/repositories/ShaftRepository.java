package com.sytoss.edu.elevator.repositories;

import com.sytoss.edu.elevator.bom.enums.DoorState;
import com.sytoss.edu.elevator.bom.enums.EngineState;
import com.sytoss.edu.elevator.bom.enums.OverWeightState;
import com.sytoss.edu.elevator.dto.HouseDTO;
import com.sytoss.edu.elevator.dto.ShaftDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShaftRepository extends JpaRepository<ShaftDTO, Long> {

    ShaftDTO getShaftDTOById(Long id);



    @Modifying
    @Query("UPDATE ShaftDTO Shaft SET Shaft.doorState = :doorState WHERE Shaft.id = :id")
    void updateDoorStateById(
            @Param("id") Long id,
            @Param("doorState") DoorState doorState);

    @Modifying
    @Query("UPDATE ShaftDTO Shaft SET Shaft.engineState = :engineState WHERE Shaft.id = :id")
    void updateEngineStateById(
            @Param("id") Long id,
            @Param("engineState") EngineState engineState);

    @Modifying
    @Query("UPDATE ShaftDTO Shaft SET Shaft.sequenceOfStops = :sequenceOfStops WHERE Shaft.id = :id")
    void updateSequenceById(
            @Param("id") Long id,
            @Param("sequenceOfStops") String sequenceOfStops);

    @Modifying
    @Query("UPDATE ShaftDTO Shaft SET Shaft.cabinPosition = :cabinPosition WHERE Shaft.id = :id")
    void updateCabinPositionById(
            @Param("id") Long id,
            @Param("cabinPosition") int cabinPosition);

    @Modifying
    @Query("UPDATE ShaftDTO Shaft SET Shaft.overweightState = :overweightState WHERE Shaft.id = :id")
    void updateOverweightStateById(
            @Param("id") Long id,
            @Param("overweightState") OverWeightState overweightState);

    @Modifying
    @Query("UPDATE ShaftDTO Shaft set Shaft.cabinPosition =:cabinPosition where Shaft.id =:id and Shaft.houseDTO =:houseDTO ")
    void updateShaftByShaftIdAndHouseId(
            @Param("id") Long shaftId,
            @Param("houseDTO")HouseDTO houseDTO,
            @Param("cabinPosition") int cabinPosition);

    List<ShaftDTO> findByHouseDTOId(Long id);

    interface ShaftHouseId {
        HouseDTO getHouseDTO();
    }

    ShaftHouseId getAllById(Long id);
}
