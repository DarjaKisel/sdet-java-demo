package by.dzinevch.stepDefinitions;

import by.dzinevich.model.request.BankAccountRequest;
import by.dzinevich.model.response.ActionCode;
import by.dzinevich.model.response.BankAccountValidationResponse;
import by.dzinevich.model.response.RiskCheckMessage;
import by.dzinevich.model.response.Type;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;
import io.cucumber.java.DefaultDataTableCellTransformer;
import io.cucumber.java.DefaultDataTableEntryTransformer;
import io.cucumber.java.DefaultParameterTransformer;
import io.cucumber.java.Transpose;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.google.common.base.CaseFormat.LOWER_CAMEL;
import static com.google.common.base.CaseFormat.UPPER_UNDERSCORE;
import static io.restassured.RestAssured.requestSpecification;
import static io.restassured.RestAssured.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class BankAccountValidationSteps {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Response validationResponse;

    @Given("^the bank account validation request with a (?:valid|invalid) IBAN '(.*)'$")
    public void createBankAccountRequestWithGivenIban(String iban) {
        final BankAccountRequest bankAccountRequest = BankAccountRequest.builder().bankAccount(iban).build();
        requestSpecification.contentType(ContentType.JSON).body(bankAccountRequest);
    }

    @When("^bank account validation request is sent to the server$")
    public void sendBankAccountValidationRequest() {
        validationResponse = when().post(RestAssured.baseURI + "/api/v3/validate/bank-account");
    }

    @Then("^server responds with HTTP response code '(\\d+)'$")
    public void verifyResponseCode(Integer code) {
        validationResponse.then().statusCode(code);
    }

    @Then("^response body contains isValid = '(.*)'")
    public void verifyResponseBodyContainsIsValidFlag(Boolean value) {
        validationResponse.then().body("isValid", equalTo(value));
    }

    @Then("^response body contains message = '(.*)'")
    public void verifyResponseBodyContainsMessage(String value) {
        validationResponse.then().body("message", containsInAnyOrder(value));
    }

    @Then("^response body message = '(.*)'")
    public void verifyResponseBodyMessageIsEqualTo(String value) {
        validationResponse.then().body("message", equalTo(value));
    }

    @DefaultParameterTransformer
    @DefaultDataTableCellTransformer
    @DefaultDataTableEntryTransformer
    public Object transform(Object fromValue, java.lang.reflect.Type toValueType) {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper.convertValue(fromValue, objectMapper.constructType(toValueType));
    }

    /**
     * step uses default transformer method declared above
     */
    @Then("^pre-validation response body is$")
    public void verifyResponseBodyIs(@Transpose List<RiskCheckMessage> expectedMessages) {
        final RiskCheckMessage[] checkMessages = validationResponse.then().extract().body().as(RiskCheckMessage[].class);
        assertThat(Arrays.asList(checkMessages), equalTo(expectedMessages));
    }

    @DataTableType
    public BankAccountValidationResponse buildBankAccountValidationResponse(Map<String, String> entry) {
        RiskCheckMessage riskCheckMessage = RiskCheckMessage.builder()
                .type(Type.valueOf(LOWER_CAMEL.to(UPPER_UNDERSCORE, entry.get("type"))))
                .code(entry.get("code"))
                .message(entry.get("message"))
                .customerFacingMessage(entry.get("customerFacingMessage"))
                .actionCode(ActionCode.valueOf(LOWER_CAMEL.to(UPPER_UNDERSCORE, entry.get("actionCode"))))
                .fieldReference(entry.get("fieldReference"))
                .build();

        return BankAccountValidationResponse.builder()
                .isValid(Boolean.valueOf(entry.get("IsValid")))
                .riskCheckMessages(Collections.singletonList(riskCheckMessage))
                .build();
    }

    /**
     * step uses the @DataTableType declared above
     */
    @Then("^validation response body is$")
    public void verifyValidationResponseBodyIs(@Transpose BankAccountValidationResponse expectedResponse) {
        final BankAccountValidationResponse accountValidationResponse = validationResponse.then().extract().body()
                .as(BankAccountValidationResponse.class);
        assertThat(accountValidationResponse, equalTo(expectedResponse));
    }
}
