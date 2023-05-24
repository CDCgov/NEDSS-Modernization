package gov.cdc.nbs.patient;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DropDownValuesControllerTest {

    private DropDownValuesController classUnderTest = new DropDownValuesController();

    @Test
    void findNameSuffixes() {
        KeyValuePairResults expectedResults = KeyValuePairResults.builder()
                .total(7)
                .content(List.of(
                        KeyValuePair.builder()
                                .key("ESQ")
                                .value("Esquire")
                                .build(),
                        KeyValuePair.builder()
                                .key("II")
                                .value("II / The Second")
                                .build(),
                        KeyValuePair.builder()
                                .key("III")
                                .value("III / The Third")
                                .build(),
                        KeyValuePair.builder()
                                .key("IV")
                                .value("IV / The Fourth")
                                .build(),
                        KeyValuePair.builder()
                                .key("JR")
                                .value("Jr.")
                                .build(),
                        KeyValuePair.builder()
                                .key("SR")
                                .value("Sr.")
                                .build(),
                        KeyValuePair.builder()
                                .key("V")
                                .value("V / Fifth")
                                .build()
                ))
                .build();
        KeyValuePairResults actualResults = classUnderTest.findNameSuffixes();
        assertEquals(expectedResults.getTotal(), actualResults.getTotal());
        expectedResults.getContent().forEach( expectedKeyValue -> assertTrue(actualResults.getContent().contains(expectedKeyValue), String.format("Expected: (%s, %s), Actual: %s", expectedKeyValue.getKey(), expectedKeyValue.getValue(), actualResults.getContent().toString())));

    }

    @Test
    void findGenders() {
    }
}