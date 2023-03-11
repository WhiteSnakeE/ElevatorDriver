package com.sytoss.edu.elevator.cucumberTests;

import com.sytoss.edu.elevator.ControllerIntegrationTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class HelloFeatureTest extends ControllerIntegrationTest {
    ResponseEntity<String> resp;

    @When("the user calls sayHello")
    public void theClientCallsSayHello () {
        String url = getBaseUrl() + "/api/helloWorld";
        resp = new TestRestTemplate().exchange(url, HttpMethod.GET, null, String.class);
    }

    @Then("operation is successful")
    public void operationSuccessful () {
        Assertions.assertEquals(HttpStatus.valueOf(200), resp.getStatusCode());
    }

    @Then("method sayHello should return {string}")
    public void returnedStringResp (String expect) {
        Assertions.assertEquals(expect, resp.getBody());
    }
}
