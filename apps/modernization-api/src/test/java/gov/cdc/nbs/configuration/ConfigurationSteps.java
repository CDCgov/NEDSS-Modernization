package gov.cdc.nbs.configuration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
    }
}
