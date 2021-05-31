package by.dzinevch.utils;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.BeforeClass;

public class TestBase {

    private static final String BASE_URL = System.getProperty("baseUrl");

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = Parser.JSON;
    }
}
