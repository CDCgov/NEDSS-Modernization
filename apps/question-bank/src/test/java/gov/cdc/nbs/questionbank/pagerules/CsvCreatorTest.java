package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceQuestion;
import gov.cdc.nbs.questionbank.pagerules.Rule.Target;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class CsvCreatorTest {

  private final CsvCreator creator = new CsvCreator();

  @Test
  void should_generate() {
    byte[] csv = creator.create(rules());
    assertThat(csv).isNotNull();
    String csvString = new String(csv);
    String expectedHeaders = "Function,Source Field,Logic,Value(s),Target Field(s),ID";
    assertThat(csvString.lines().toList().get(0)).isEqualTo(expectedHeaders);

    String expectedRow =
        "Date Compare,Date of Birth (DEM115),=,\"sv1,sv2\",Admission Date (INV132),1";
    assertThat(csvString.lines().toList().get(1)).isEqualTo(expectedRow);

    String expectedRow2 =
        "Enable,Suffix (DEM107),=,Any Source Value,\"Administrative Information (NBS_UI_29),Binational Reporting (NBS_INV_GENV2_UI_5)\",2";
    assertThat(csvString.lines().toList().get(2)).isEqualTo(expectedRow2);
  }

  private List<Rule> rules() {
    return Arrays.asList(
        new Rule(
            1l,
            2l,
            RuleFunction.DATE_COMPARE,
            "description",
            new SourceQuestion("DEM115", "Date of Birth", "codeSetNm"),
            false,
            Arrays.asList("sv1", "sv2"),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList(new Target("INV132", "Admission Date"))),
        new Rule(
            2l,
            2l,
            RuleFunction.ENABLE,
            "description",
            new SourceQuestion("DEM107", "Suffix", "codeSetNm"),
            true,
            null,
            Comparator.EQUAL_TO,
            TargetType.SUBSECTION,
            Arrays.asList(
                new Target("NBS_UI_29", "Administrative Information"),
                new Target("NBS_INV_GENV2_UI_5", "Binational Reporting"))));
  }
}
