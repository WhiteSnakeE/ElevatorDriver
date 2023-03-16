package com.sytoss.edu.elevator.bom;

import java.util.HashMap;

public interface Command {
    void execute(HashMap<String,Object> params);
}
