package gov.cdc.nbs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PatientSearchStepDefinitions {
    
    @Given("there are 10 patients")
    public void there_are_10_patients() {
    }

    @Given("I am looking for one of them")
    public void i_am_looking_for_one_of_them() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
    }

    @When("I search patients by {string} {string} {string}")
    public void i_search_patients_by_field_john(String field, String value, String qualifier) {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
    }

    @Then("I find the patient")
    public void i_find_the_patient() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
    }

    @Then("I have the option to create a new patient")
    public void i_have_the_option_to_create_a_new_patient() {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
    }
    
}
