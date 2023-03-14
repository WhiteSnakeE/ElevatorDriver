package com.sytoss.edu.elevator.interfaces;

import java.util.HashMap;

public interface Command {
    void execute(HashMap<String,String> params);
}
