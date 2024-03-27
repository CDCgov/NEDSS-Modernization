package gov.cdc.nbs.configuration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConfigurationSteps {

    @Autowired
    private ConfigurationController controller;

    private Configuration configuration;


    @When("I request the current configuration")
    public void i_request_the_current_config() {
        configuration = controller.getConfiguration();
    }

    @Then("the current configuration is returned")
    public void the_current_configuration_is_returned() {
        // Values from application-test.yml
        assertTrue(configuration.features().address().autocomplete());
        assertFalse(configuration.features().address().verification());
        assertTrue(configuration.features().pageBuilder().enabled());
        assertTrue(configuration.features().pageBuilder().page().library().enabled());
        assertTrue(configuration.features().pageBuilder().page().management().create().enabled());
        assertFalse(configuration.features().pageBuilder().page().management().edit().enabled());
    }
}
