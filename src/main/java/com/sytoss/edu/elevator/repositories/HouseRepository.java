package com.sytoss.edu.elevator.repositories;

import com.sytoss.edu.elevator.dto.HouseDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<HouseDTO, Long> {

}
