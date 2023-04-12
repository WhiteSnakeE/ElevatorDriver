package com.sytoss.edu.elevator.repositories;

import com.sytoss.edu.elevator.converters.HouseConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<HouseConverter, Long> {

}
