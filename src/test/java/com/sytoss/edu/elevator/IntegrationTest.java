package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import com.sytoss.edu.elevator.bom.house.House;
import com.sytoss.edu.elevator.commands.FindNearestCabinCommand;
import com.sytoss.edu.elevator.commands.PressUpButtonCommand;
import com.sytoss.edu.elevator.services.FloorService;
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
@Getter
public class IntegrationTest extends AbstractControllerTest {

    @SpyBean
    @Autowired
    private House house;
    @Autowired
    @SpyBean
    private ElevatorDriver elevatorDriver;
    @Autowired
    @SpyBean
    private FloorService floorService;
    @Autowired
    @SpyBean
    private FindNearestCabinCommand findNearestCabinCommand;

    @SpyBean
    @Autowired
    private PressUpButtonCommand pressUpButtonCommand;
}
