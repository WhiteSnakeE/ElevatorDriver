package com.sytoss.edu.elevator.repositories;

import com.sytoss.edu.elevator.converters.ShaftConverter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShaftRepository extends JpaRepository<ShaftConverter, Long> {

}
