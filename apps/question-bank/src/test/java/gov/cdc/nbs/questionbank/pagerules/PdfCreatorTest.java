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

class PdfCreatorTest {

  private final PdfCreator creator = new PdfCreator();

  @Test
  void should_generate() {
    byte[] pdf = creator.create(rules());

    assertThat(pdf).isNotNull();
  }

  private List<Rule> rules() {
    return Arrays.asList(
        new Rule(
            1l,
            2l,
            RuleFunction.DATE_COMPARE,
            "description",
            new SourceQuestion("identifier", "label", "codeSetNm"),
            false,
            Arrays.asList("sv1", "sv2"),
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList(new Target("tId", "tLabel"))),
        new Rule(
            2l,
            2l,
            RuleFunction.ENABLE,
            "description",
            new SourceQuestion("identifier", "label", "codeSetNm"),
            true,
            null,
            Comparator.EQUAL_TO,
            TargetType.QUESTION,
            Arrays.asList(new Target("tId", "tLabel"))));
  }
}
