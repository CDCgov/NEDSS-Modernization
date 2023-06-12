package gov.cdc.nbs;

import gov.cdc.nbs.controller.CodeValueGeneralController;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.DropDownValuesController;
import gov.cdc.nbs.patient.KeyValuePair;
import gov.cdc.nbs.patient.KeyValuePairResults;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientDataEntryDropDownSteps {

    @Autowired
    private DropDownValuesController dropDownValuesController;
    @Autowired
    private CodeValueGeneralController codeValueGeneralController;
    private KeyValuePairResults actualKeyValuePairResults;

    private GraphQLPage page = new GraphQLPage(100, 0);

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
            case "Marital Status" ->  {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllMaritalStatus(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
                                .key(codeValueGeneral.getId().getCode())
                                .value(codeValueGeneral.getCodeShortDescTxt())
                                .build())
                        .toList();
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .content(keyValuePairs)
                        .total(keyValuePairs.size())
                        .build();
            }
            case "Primary Occupation" ->  {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllPrimaryOccupations(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
                                .key(codeValueGeneral.getId().getCode())
                                .value(codeValueGeneral.getCodeShortDescTxt())
                                .build())
                        .toList();
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .content(keyValuePairs)
                        .total(keyValuePairs.size())
                        .build();
            }
            case "Highest Level Of Education" ->  {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllHighestLevelOfEducation(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
                                .key(codeValueGeneral.getId().getCode())
                                .value(codeValueGeneral.getCodeShortDescTxt())
                                .build())
                        .toList();
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .content(keyValuePairs)
                        .total(keyValuePairs.size())
                        .build();
            }
            case "Primary Language" ->  {
                List<KeyValuePair> keyValuePairs = codeValueGeneralController.findAllPrimaryLanguages(page).getContent().stream().map(codeValueGeneral -> KeyValuePair.builder()
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
}
