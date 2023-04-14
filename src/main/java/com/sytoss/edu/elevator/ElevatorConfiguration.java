package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.bom.house.HouseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElevatorConfiguration {

    @Autowired
    private HouseBuilder houseBuilder;

    @Value("${house.elevatorCountCabin}")
    private int shaftsCount;

    @Value("${house.floorsCount}")
    private int floorsCount;

    @Bean("house")
    public House getHouse () {
        return houseBuilder.build(shaftsCount, floorsCount);
    }
}
