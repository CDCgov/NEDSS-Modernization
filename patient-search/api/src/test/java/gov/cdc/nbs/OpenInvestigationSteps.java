package gov.cdc.nbs;

import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class OpenInvestigationSteps {

    @Given("a patient has open investigations")
    public void a_patient_has_open_investigations() {

    }

    @Given("a patient does not have open investigations")
    public void a_patient_does_not_have_open_investigations() {

    }

    @When("I search for open investigations for a patient")
    public void i_search_for_open_investigations_for_a_patient() {

    }

    @Then("I receive a list of open investigations")
    public void i_receive_a_list_of_open_investigations() {

    }

    @Then("no open investigations are returned")
    public void no_open_investigations_are_returned() {

    }
}
