package by.dzinevch.stepDefinitions;

import io.cucumber.java.en.Given;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.Header;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;

public class AuthenticationSteps {

    private static final String VALID_TOKEN = System.getProperty("authToken");

    @Given("^valid authentication token$")
    public void addValidAuthenticationToken() {
        final Header validToken = new Header("X-Auth-Key", VALID_TOKEN);
        requestSpecification = given().filter(new AllureRestAssured()).header(validToken);
    }

    @Given("^no authentication token$")
    public void noAuthenticationToken() {
        requestSpecification = given().filter(new AllureRestAssured());
    }

    @Given("^invalid authentication token$")
    public void addInvalidAuthenticationToken() {
        final Header invalidToken = new Header("X-Auth-Key", "Q7RRRRRRRRRRRRRRRRRRRRRRRRRRRReAFNoKY68L");
        requestSpecification = given().filter(new AllureRestAssured()).header(invalidToken);
    }
}
