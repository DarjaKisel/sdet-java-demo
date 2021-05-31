package by.dzinevch;

import by.dzinevch.utils.TestBase;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"},
        features = {"classpath:by.dzinevich/features"},
        tags = "@smoke and not @extended",
        snippets = CucumberOptions.SnippetType.CAMELCASE)

public class BankAccountSmokeTest extends TestBase {
}
