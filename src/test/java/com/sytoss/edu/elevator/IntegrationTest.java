package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.*;
import com.sytoss.edu.elevator.converters.HouseConverter;
import com.sytoss.edu.elevator.converters.ShaftConverter;
import com.sytoss.edu.elevator.repositories.HouseRepository;
import com.sytoss.edu.elevator.repositories.ShaftRepository;
import com.sytoss.edu.elevator.services.FloorService;
import com.sytoss.edu.elevator.services.HouseService;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.Getter;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@CucumberContextConfiguration
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
@CucumberOptions(features = "src/test/resources/features", glue = "com.sytoss.edu.elevator", tags = "not @SingleRun")
@Getter
public class IntegrationTest extends AbstractControllerTest {

    //    @Autowired
    //    @SpyBean
    //    private House house;
    //
    //    @Autowired
    //    private ElevatorDriver elevatorDriver;

    @Autowired
    private FloorService floorService;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ShaftRepository shaftRepository;

    @Autowired
    private HouseConverter houseConverter;

    @Autowired
    private ShaftConverter shaftConverter;

    @Autowired
    private FindNearestCabinCommand findNearestCabinCommand;

    @Autowired
    private PressUpButtonCommand pressUpButtonCommand;

    @Autowired
    @SpyBean
    private CommandManager commandManager;

    @Autowired
    @SpyBean
    private CloseDoorCommand closeDoorCommand;

    @Autowired
    @SpyBean
    private OpenDoorCommand openDoorCommand;

    @Autowired
    @SpyBean
    private StartEngineCommand startEngineCommand;

    @Autowired
    @SpyBean
    private StopEngineCommand stopEngineCommand;

    @Autowired
    @SpyBean
    private MoveCabinCommand moveCabinCommand;

    @Autowired
    @SpyBean
    private VisitFloorCommand visitFloorCommand;

    @Autowired
    private HouseThreadPool houseThreadPool;

    @Autowired
    private HouseService houseService;
}
