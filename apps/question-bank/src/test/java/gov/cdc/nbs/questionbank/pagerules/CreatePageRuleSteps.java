package gov.cdc.nbs.questionbank.pagerules;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.pagerules.Rule.Comparator;
import gov.cdc.nbs.questionbank.pagerules.Rule.RuleFunction;
import gov.cdc.nbs.questionbank.pagerules.Rule.SourceValue;
import gov.cdc.nbs.questionbank.pagerules.Rule.TargetType;
import gov.cdc.nbs.questionbank.pagerules.request.RuleRequest;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.test.web.servlet.ResultActions;

public class CreatePageRuleSteps {

  private final PageRuleRequester requester;
  private final Active<ResultActions> response;
  private final PageMother pageMother;

  public CreatePageRuleSteps(
      final PageRuleRequester requester,
      final Active<ResultActions> response,
      final PageMother pageMother) {
    this.requester = requester;
    this.response = response;
    this.pageMother = pageMother;
  }

  private RuleRequest ruleRequest;

  @Given("I have the following create rule request:")
  public void i_have_the_create_rule_request(DataTable dataTable) {
    Map<String, String> map = dataTable.asMap();
    ruleRequest =
        new RuleRequest(
            RuleFunction.valueOf(map.get("ruleFunction")),
            map.get("description"),
            map.get("sourceIdentifier"),
            "true".equals(map.get("anySourceValue").toLowerCase()),
            parseSourceValues(map.get("sourceValues")),
            Comparator.valueOf(map.get("comparator")),
            TargetType.valueOf(map.get("targetType")),
            Arrays.asList(map.get("targetIdentifiers").split(",")),
            map.get("sourceText"),
            Arrays.asList(map.get("targetValueText").split(",")));
  }

  private List<SourceValue> parseSourceValues(String text) {
    String[] split = text.split(",");
    List<SourceValue> sourceValues = new ArrayList<>();
    for (int i = 0; i < split.length - 1; i += 2) {
      sourceValues.add(new SourceValue(split[i], split[i + 1]));
    }
    return sourceValues;
  }

  @When("I create a rule")
  public void i_create_a_rule() throws Exception {
    long page = pageMother.one().getId();
    response.active(requester.createBusinessRule(page, ruleRequest));
  }

  @Then("the rule is created")
  public void rule_is_created() throws Exception {
    response.active().andExpect(status().isCreated());
  }
}
