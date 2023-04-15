package com.sytoss.edu.elevator.repositories;

import com.sytoss.edu.elevator.dto.HouseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface HouseRepository extends JpaRepository<HouseDTO, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE HOUSE SET ORDER_SEQUENCE_OF_STOPS = :orderSequenceOfStops WHERE ID = :id", nativeQuery = true)
    void updateOrderById(@Param("id") Long id, @Param("orderSequenceOfStops") String orderSequenceOfStops);
}
