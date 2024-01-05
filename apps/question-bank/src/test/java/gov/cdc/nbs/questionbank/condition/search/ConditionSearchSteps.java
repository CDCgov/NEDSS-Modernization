package gov.cdc.nbs.questionbank.condition.search;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.ResultActions;
import gov.cdc.nbs.questionbank.condition.ConditionCreator;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConditionSearchSteps {

  private Active<Condition> activeCondition = new Active<>();
  private final Active<ResultActions> response;

  private final ConditionCreator creator;
  private final ConditionSearchRequest request;
  private final PageMother mother;


  public ConditionSearchSteps(
      final ConditionCreator creator,
      final ConditionSearchRequest request,
      final Active<ResultActions> response,
      final PageMother mother) {
    this.creator = creator;
    this.request = request;
    this.response = response;
    this.mother = mother;
  }

  @Given("a condition exists")
  public void a_condition_exists() {
    Condition created = creator.createCondition(createConditionRequest(), 999l);
    activeCondition.active(created);
  }

  @Given("the page is related to the condition")
  public void relate_condition_to_page() {
    Condition condition = activeCondition.active();
    WaTemplate page = mother.one();
    mother.withCondition(new PageIdentifier(page.getId(), page.getTemplateNm()), condition.id());
  }

  @When("i search for the condition {string} in use")
  public void search_for_condition_including_in_use(String excludeInUse) {
    response.active(request.search(
        new ReadConditionRequest(activeCondition.active().id(), excludeInUse.contains("exclude")),
        Pageable.ofSize(20)));
  }

  @Then("the condition is returned")
  public void the_condition_is_returned() throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.content[*].id")
                .value(hasItem(this.activeCondition.active().id())));
  }

  @Then("the condition is not returned")
  public void the_condition_is_not_returned() throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.content[*].id")
                .value(not(hasItem(this.activeCondition.active().id()))));
  }

  private CreateConditionRequest createConditionRequest() {
    UUID randomUuid = UUID.randomUUID();
    String name = randomUuid.toString().substring(0, 6);
    return new CreateConditionRequest(
        name,
        "Notifiable Event Code List",
        name,
        "GCD",
        'N',
        'N',
        'N',
        'N',
        "ARBO",
        null);
  }

}
