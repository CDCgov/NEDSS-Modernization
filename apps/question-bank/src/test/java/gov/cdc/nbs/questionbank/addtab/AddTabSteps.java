package gov.cdc.nbs.questionbank.addtab;

import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.messages.types.DataTable;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class AddTabSteps {

    @Autowired
    CreateTabService createTabService;

    @Autowired
    WaUiMetaDataRepository waUiMetadataRepository;

    @Given("a tab creation request:")
    public CreateTabRequest a_valid_tab_creation_request(DataTable dataTable) {
        List<String> requestValues = new ArrayList<>();
        dataTable.getRows().get(1).getCells().stream().map(fields ->
                requestValues.add(fields.getValue()));

        CreateTabRequest createTabRequest = new CreateTabRequest(Long.parseLong(requestValues.get(0)),
                requestValues.get(1), requestValues.get(2));

        return createTabRequest;
    }

    @Then("the service should create the tab successfully")
    public void the_tab_created_successfully() {
        CreateTabRequest createTabRequest = new CreateTabRequest(123L, "Some Message", "T");

        try {
            CreateUiResponse response = createTabService.createTab(123L, createTabRequest);
            Assert.assertEquals( "tab created succesfully", response.message());
        } catch(Exception exception) {
            throw  new AddTabException("Some Exception", 1010);
        }
    }

    @Then("the service should throw an AddTabException")
    public void the_tab_creat_exception() {

        try {
            CreateUiResponse response = createTabService.createTab(123L, null);

        } catch(Exception exception) {
            throw  new AddTabException("Some Exception", 1010);
        }
    }


}