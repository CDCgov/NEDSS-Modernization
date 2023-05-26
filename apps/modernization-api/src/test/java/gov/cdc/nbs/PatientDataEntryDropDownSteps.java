package gov.cdc.nbs;

import gov.cdc.nbs.controller.CodeValueGeneralController;
import gov.cdc.nbs.entity.srte.CodeValueGeneral;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.patient.DropDownValuesController;
import gov.cdc.nbs.patient.KeyValuePair;
import gov.cdc.nbs.patient.KeyValuePairResults;
import gov.cdc.nbs.repository.NaicsIndustryCodeRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PatientDataEntryDropDownSteps {

    @Mock
    private NaicsIndustryCodeRepository naicsIndustryCodeRepository;
    private final DropDownValuesController dropDownValuesController = new DropDownValuesController(naicsIndustryCodeRepository);
    private KeyValuePairResults actualKeyValuePairResults;

    @Autowired
    private CodeValueGeneralController codeValueGeneralController;
    private GraphQLPage page = new GraphQLPage(100, 0);
    ;

    @When("I want to retrieve {string}")
    public void iWantToRetrieve(String fieldName) {
        switch (fieldName) {
            case "Name Suffix" -> actualKeyValuePairResults = dropDownValuesController.findNameSuffixes();
            case "Gender" -> actualKeyValuePairResults = dropDownValuesController.findGenders();
            case "Yes/No/Unknown" -> actualKeyValuePairResults = dropDownValuesController.findYesNoUnk();
            case "Name Type" -> {
                Page<CodeValueGeneral> allNameTypes = codeValueGeneralController.findAllNameTypes(page);
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .total(allNameTypes.getNumberOfElements())
                        .content(allNameTypes.map(codeValueGeneral ->
                                        KeyValuePair.builder()
                                                .key(codeValueGeneral.getId().getCode())
                                                .value(codeValueGeneral.getCodeShortDescTxt())
                                                .build())
                                .toList())
                        .build();
            }
            case "Name Prefix" -> {
                Page<CodeValueGeneral> allNamePrefixes = codeValueGeneralController.findAllNamePrefixes(page);
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .total(allNamePrefixes.getNumberOfElements())
                        .content(allNamePrefixes.map(codeValueGeneral ->
                                        KeyValuePair.builder()
                                                .key(codeValueGeneral.getId().getCode())
                                                .value(codeValueGeneral.getCodeShortDescTxt())
                                                .build())
                                .toList())
                        .build();
            }
            case "Degree" -> {
                Page<CodeValueGeneral> allDegrees = codeValueGeneralController.findAllDegrees(page);
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .total(allDegrees.getNumberOfElements())
                        .content(allDegrees.map(codeValueGeneral ->
                                        KeyValuePair.builder()
                                                .key(codeValueGeneral.getId().getCode())
                                                .value(codeValueGeneral.getCodeShortDescTxt())
                                                .build())
                                .toList())
                        .build();
            }
            case "Address Type" -> {
                Page<CodeValueGeneral> allAddressTypes = codeValueGeneralController.findAllAddressTypes(page);
                actualKeyValuePairResults = KeyValuePairResults.builder()
                        .total(allAddressTypes.getNumberOfElements())
                        .content(allAddressTypes.map(codeValueGeneral ->
                                        KeyValuePair.builder()
                                                .key(codeValueGeneral.getId().getCode())
                                                .value(codeValueGeneral.getCodeShortDescTxt())
                                                .build())
                                .toList())
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
