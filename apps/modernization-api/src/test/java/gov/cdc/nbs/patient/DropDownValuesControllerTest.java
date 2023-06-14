package gov.cdc.nbs.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;

class DropDownValuesControllerTest {

    private final DropDownValuesController classUnderTest = new DropDownValuesController();

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
                                .value("V / The Fifth")
                                .build()
                ))
                .build();
        KeyValuePairResults actualResults = classUnderTest.findNameSuffixes();
        assertEquals(expectedResults.getTotal(), actualResults.getTotal());

        expectedResults.getContent().forEach(expectedKeyValue -> assertTrue(actualResults
                        .getContent()
                        .stream()
                        .anyMatch(expectedKeyValue::equals),
                String.format("Expected: (%s, %s), Actual: %s",
                        expectedKeyValue.getKey(),
                        expectedKeyValue.getValue(), actualResults.getContent())));

    }

}