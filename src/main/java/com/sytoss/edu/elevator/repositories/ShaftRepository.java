package com.sytoss.edu.elevator.repositories;

import com.sytoss.edu.elevator.dto.ShaftDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ShaftRepository extends JpaRepository<ShaftDTO, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE SHAFT SET DOOR_STATE = :doorState WHERE ID = :id", nativeQuery = true)
    void updateDoorStateById(@Param("id") Long id, @Param("doorState") String doorState);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SHAFT SET ENGINE_STATE = :engineState WHERE ID = :id", nativeQuery = true)
    void updateEngineStateById(@Param("id") Long id, @Param("engineState") String engineState);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SHAFT SET SEQUENCE_OF_STOPS = :sequenceOfStops WHERE ID = :id", nativeQuery = true)
    void updateSequenceById(@Param("id") Long id, @Param("sequenceOfStops") String sequenceOfStops);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SHAFT SET CABIN_POSITION = :cabinPosition WHERE ID = :id", nativeQuery = true)
    void updateCabinPositionById(@Param("id") Long id, @Param("cabinPosition") int cabinPosition);

    @Transactional
    @Modifying
    @Query(value = "UPDATE SHAFT SET OVERWEIGHT_STATE = :overweightState WHERE ID = :id", nativeQuery = true)
    void updateOverweightStateById(@Param("id") Long id, @Param("overweightState") String overweightState);
}
