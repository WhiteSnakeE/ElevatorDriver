package com.sytoss.edu.elevator;

import com.sytoss.edu.elevator.bom.Controller;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.Getter;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.cucumber.core.options.Constants.PLUGIN_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@CucumberContextConfiguration
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty")
public class ControllerIntegrationTest extends AbstractControllerTest {
    @LocalServerPort
    private int applicationPort;
    @Autowired
    @Getter
    private Controller controller;

    public String getBaseUrl () {
        return "http://localhost:" + applicationPort;
    }
}
