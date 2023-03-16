package com.sytoss.edu.elevator.bom;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

//@Component
//@Scope("prototype")
@Slf4j
@Getter
@Setter
public class Shaft {

    private int id;
    private static int index=0;
    private int cabinPosition;
    private ArrayList<Floor> floors;
    private Cabin cabin;
    private Engine engine;
    public Shaft () {
        this.id=index++;
        log.info(String.valueOf(this.id));
//        cabin=new Cabin();
//        engine=new Engine();
        log.info("Shafts initializing");
        floors = new ArrayList<>();
        floors.add(new FirstFloor(1));
        for (int id = 2; id <= 10; ++id) {
            floors.add(id == 10 ? new LastFloor(id) : new MiddleFloor(id));
        }
    }

    public void activate () {

    }


}
