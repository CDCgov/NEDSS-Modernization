package gov.cdc.nbs;

import gov.cdc.nbs.controller.CodeValueGeneralController;
import gov.cdc.nbs.entity.srte.StateCountyCodeValue;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.DropDownValuesController;
import gov.cdc.nbs.patient.KeyValuePair;
import gov.cdc.nbs.patient.KeyValuePairResults;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientDataEntryDropDownSteps {

    @Autowired
    private DropDownValuesController dropDownValuesController;
    @Autowired
    private CodeValueGeneralController codeValueGeneralController;
    private KeyValuePairResults actualKeyValuePairResults;

    private GraphQLPage page = new GraphQLPage(600, 0);
    private Page<StateCountyCodeValue> actualStateCountyCodeValues;

    @When("I want to retrieve {string}")
    public void iWantToRetrieve(String fieldName) {
        switch (fieldName) {
            case "Name Suffix" -> actualKeyValuePairResults = dropDownValuesController.findNameSuffixes();
            case "Gender" -> actualKeyValuePairResults = dropDownValuesController.findGenders();
            case "Yes/No/Unknown" -> actualKeyValuePairResults = dropDownValuesController.findYesNoUnk();
            case "Name Type" -> {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllNameTypes(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
                                .key(codeValueGeneral.getId().getCode())
                                .value(codeValueGeneral.getCodeShortDescTxt())
                                .build())
                        .toList();
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .content(keyValuePairs)
                        .total(keyValuePairs.size())
                        .build();
            }
            case "Name Prefix" -> {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllNamePrefixes(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
                                .key(codeValueGeneral.getId().getCode())
                                .value(codeValueGeneral.getCodeShortDescTxt())
                                .build())
                        .toList();
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .content(keyValuePairs)
                        .total(keyValuePairs.size())
                        .build();
            }
            case "Degree" -> {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllDegrees(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
                                .key(codeValueGeneral.getId().getCode())
                                .value(codeValueGeneral.getCodeShortDescTxt())
                                .build())
                        .toList();
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .content(keyValuePairs)
                        .total(keyValuePairs.size())
                        .build();
            }
            case "Address Type" ->  {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllAddressTypes(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
                                .key(codeValueGeneral.getId().getCode())
                                .value(codeValueGeneral.getCodeShortDescTxt())
                                .build())
                        .toList();
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .content(keyValuePairs)
                        .total(keyValuePairs.size())
                        .build();
            }
        }
    }

    @Then("I get these key-value pairs:")
    public void iGetTheseKeyValuePairs(DataTable expectedDataTable) {
        List<Map<String, String>> expectedDataTableMaps = expectedDataTable.asMaps();
        List<KeyValuePair> expectedResults = expectedDataTableMaps.stream()
                .map(row -> KeyValuePair.builder()
                        .key(row.get("Key"))
                        .value(row.get("Value"))
                        .build())
                .toList();

        expectedResults.forEach(expectedKeyValue -> assertTrue(actualKeyValuePairResults
                        .getContent()
                        .stream()
                        .anyMatch(expectedKeyValue::equals),
                String.format("Expected: (%s, %s), Actual: %s",
                        expectedKeyValue.getKey(),
                        expectedKeyValue.getValue(), actualKeyValuePairResults.getContent())));
    }

    @When("I want to retrieve counties from state {string}")
    public void iWantToRetrieveCountiesFromState(String stateCode) {
        if (stateCode.equals("Alabama")) {
            actualStateCountyCodeValues = codeValueGeneralController.findAllStateCountyCodeValues("01", page);
        } else {
            Assertions.fail("Unknown state code: " + stateCode);
        }
    }

    @Then("I get these counties:")
    public void iGetTheseCounties(DataTable expectedDataTable) {
        assertEquals(expectedDataTable.asLists().size(),actualStateCountyCodeValues.getContent().size()) ;
    }

}
