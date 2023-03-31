package com.sytoss.edu.elevator.commands;

import java.util.HashMap;

public interface Command {

    String PRESS_UP_BUTTON = "PressUpButton";
    String FIND_NEAREST_CABIN_COMMAND = "FindNearestCabinCommand";
    String ACTIVATE_SHAFT_COMMAND="ActivateShaftCommand";
    String MOVE_CABIN_COMMAND="MoveCabinCommand";
    String STOP_CABIN_COMMAND="StopCabinCommand";
    String OPEN_DOOR_COMMAND="OpenDoorCommand";
    String CLOSE_DOOR_COMMAND="CloseDoorCommand";

    void execute (HashMap<String, Object> params);
}
