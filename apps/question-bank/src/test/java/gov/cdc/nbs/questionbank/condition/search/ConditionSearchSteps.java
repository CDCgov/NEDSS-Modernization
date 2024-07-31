package gov.cdc.nbs.questionbank.condition.search;

import com.google.common.collect.Comparators;
import com.jayway.jsonpath.JsonPath;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConditionSearchSteps {

  private final Active<Condition> activeCondition = new Active<>();
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
    Condition created = creator.createCondition(createConditionRequest(null, null), 999L);
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

  @When("i search for all available conditions")
  public void search_for_all_available() {
    response.active(request.available());
  }

  @When("i search for all available conditions and those related to a page")
  public void search_for_all_available_with_page() {
    Long page = mother.one().getId();
    response.active(request.available(page));
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

  @Given("a condition exists with {string} set to {string}")
  public void a_condition_exists_with_value(String field, String value) {
    Condition created = creator.createCondition(createConditionRequest(field, value), 999L);
    activeCondition.active(created);
  }

  @When("i search a condition with sort {string} {string}")
  public void i_search_with_sort(String field, String direction) {
    Direction dir = direction.toLowerCase().contains("asc") ? Direction.ASC : Direction.DESC;
    response.active(request.search(
        new ReadConditionRequest(),
        PageRequest.of(0, 50, Sort.by(dir, field))));
  }

  @Then("the condition is not in the available conditions")
  public void condition_is_not_in_available() throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.[*].id")
                .value(not(hasItem(this.activeCondition.active().id()))))
        .andExpect(jsonPath("$.[*].id").isNotEmpty());
  }

  @Then("the condition is in the available conditions")
  public void condition_is_in_available() throws Exception {
    response.active()
        .andExpect(status().isOk())
        .andExpect(
            jsonPath("$.[*].id")
                .value(hasItem(this.activeCondition.active().id())))
        .andExpect(jsonPath("$.[*].id").isNotEmpty());
  }

  @Then("the conditions are returned sorted by {string} {string}")
  public void conditions_are_sorted(String field, String direction) throws Exception {
    String content = response.active()
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
    String jsonField = switch (field) {
      case "id" -> "id";
      case "conditionShortNm" -> "conditionShortNm";
      case "progAreaCd" -> "progAreaCd";
      case "familyCd" -> "familyCd";
      case "coinfection_grp_cd" -> "coinfectionGrpCd";
      case "investigationFormCd" -> "investigationFormCd";
      case "nndInd" -> "nndInd";
      case "statusCd" -> "statusCd";
      default -> throw new IllegalArgumentException();
    };
    List<String> results = JsonPath.read(content, "$.content[*]." + jsonField);
    results = results.stream().filter(Objects::nonNull).toList();
    Comparator<String> comparator =
        (a, b) -> direction.contains("asc") ? a.compareToIgnoreCase(b) : b.compareToIgnoreCase(a);
    assertTrue(Comparators.isInOrder(results, comparator));
  }

  private CreateConditionRequest createConditionRequest(String field, String value) {
    UUID randomUuid = UUID.randomUUID();
    String name = randomUuid.toString().substring(0, 6);
    String id = name;
    String codeSystem = "Notifiable Event Code List";
    String progArea = "GCD";
    char nndInd = 'N';
    Character morbidityInd = 'N';
    Character summaryInd = 'N';
    Character contactTracing = 'N';
    String familyCd = "ARBO";
    String coinfectionGrp = null;

    if (Objects.equals(field, "conditionShortNm")) {
      name = value;
    } else if (Objects.equals(field, "id")) {
      id = value;
    } else if (Objects.equals(field, "progAreaCd")) {
      progArea = value;
    } else if (Objects.equals(field, "familyCd")) {
      familyCd = value;
    } else if (Objects.equals(field, "coinfection_grp_cd")) {
      coinfectionGrp = value;
    } else if (Objects.equals(field, "nndInd")) {
      nndInd = value.charAt(0);
    }

    return new CreateConditionRequest(
        id,
        codeSystem,
        name,
        progArea,
        nndInd,
        morbidityInd,
        summaryInd,
        contactTracing,
        familyCd,
        coinfectionGrp);
  }

}
