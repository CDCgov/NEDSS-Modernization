package gov.cdc.nbs.questionbank.page.content.rule;

import static org.junit.jupiter.api.Assertions.assertNull;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.page.content.rule.exceptions.DeleteRuleException;
import gov.cdc.nbs.questionbank.pagerules.PageRuleRequester;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

public class DeleteRuleSteps {

  private final EntityManager entityManager;

  private final Active<PageIdentifier> activePage;

  private final PageRuleRequester requester;

  private final Active<WaRuleMetadata> activeDeleted = new Active<>();

  DeleteRuleSteps(
      final EntityManager entityManager,
      final Active<PageIdentifier> activePage,
      final PageRuleRequester requester) {
    this.entityManager = entityManager;
    this.activePage = activePage;
    this.requester = requester;
  }

  @Given("I send a delete rule request")
  @Transactional
  public void i_send_a_delete_rule_request() {
    PageIdentifier identifier = this.activePage.active();
    WaTemplate page = this.entityManager.find(WaTemplate.class, identifier.id());
    WaRuleMetadata lastRule =
        page.getWaRuleMetadata().stream()
            .reduce((first, second) -> second)
            .orElseThrow(() -> new DeleteRuleException("no Page rule found"));
    this.activeDeleted.active(lastRule);
    this.requester.deleteBusinessRule(this.activePage.active().id(), lastRule.getId());
  }

  @Then("the rule is deleted")
  public void the_rule_is_deleted() {
    WaRuleMetadata deleted = this.activeDeleted.active();
    WaRuleMetadata rule = this.entityManager.find(WaRuleMetadata.class, deleted.getId());
    assertNull(rule);
  }
}
