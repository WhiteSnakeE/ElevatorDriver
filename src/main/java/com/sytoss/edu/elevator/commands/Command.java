package com.sytoss.edu.elevator.commands;

import java.util.HashMap;

public interface Command {
    String FIND_NEAREST_CABIN_COMMAND = "FindNearestCabinCommand";

    String PRESS_UP_BUTTON = "PressUpButton";

    void execute (HashMap<String, Object> params);
}
