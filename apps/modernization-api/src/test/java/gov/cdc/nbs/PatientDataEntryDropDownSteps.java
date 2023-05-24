package gov.cdc.nbs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ActiveProfiles("test")
public class PatientDataEntryDropDownSteps {

    @When("I want to retrieve {string}")
    public void iWantToRetrieve(String fieldName) {
        System.out.println("fieldName: " + fieldName);
    }

    @Then("I get these key-value pairs:")
    public void iGetTheseKeyValuePairs(DataTable dataTable  ) {
        dataTable.asList().forEach(x -> System.out.println("Key value pair: " +x.toString() ));
    }
}
