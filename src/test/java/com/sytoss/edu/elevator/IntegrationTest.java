package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.bom.ElevatorDriver;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.Getter;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@CucumberContextConfiguration
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class IntegrationTest extends AbstractControllerTest {
    @LocalServerPort
    private int applicationPort;
    @Autowired
    @Getter
    @SpyBean
    private ElevatorDriver elevatorDriver;

    public String getBaseUrl () {
        return "http://localhost:" + applicationPort;
    }
}
