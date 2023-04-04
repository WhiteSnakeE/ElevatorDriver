package com.sytoss.edu.elevator.commands;

import com.sytoss.edu.elevator.bom.Shaft;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OpenDoorCommand implements Command {

    @Override
    public void execute (HashMap<String, Object> params) {
        Shaft shaft = (Shaft) params.get("Shaft");
        shaft.getCabin().openDoor();
    }
}
