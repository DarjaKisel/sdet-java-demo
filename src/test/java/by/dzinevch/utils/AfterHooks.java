package by.dzinevch.utils;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;

import static io.restassured.RestAssured.requestSpecification;

public class AfterHooks {

    @After
    public void afterTest(Scenario scenario) {
        requestSpecification = null;
    }
}
