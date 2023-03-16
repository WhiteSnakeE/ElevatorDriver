package com.sytoss.edu.elevator.bom;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Cabin {
    private int id;

}
