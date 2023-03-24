package com.sytoss.edu.elevator.commands;

import java.util.HashMap;

public interface Command {
    void execute (HashMap<String, Object> params);
}
