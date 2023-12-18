package gov.cdc.nbs.questionbank.page.content.rule;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.pagerule.WaRuleMetadata;
import gov.cdc.nbs.questionbank.page.content.rule.exceptions.DeleteRuleException;
import gov.cdc.nbs.questionbank.pagerules.PageRuleRequest;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.junit.Assert.assertNull;


public class DeleteRuleSteps {


  @Autowired
  EntityManager entityManager;

  @Autowired
  Active<PageIdentifier> page;

  @Autowired
  private PageRuleRequest requester;

  private final Active<WaRuleMetadata> deleted = new Active<>();


  @Given("I send a delete rule request")
  @Transactional
  public void i_send_a_delete_rule_request() {
    PageIdentifier identifier = this.page.active();
    WaTemplate page = this.entityManager.find(WaTemplate.class, identifier.id());
    WaRuleMetadata lastRule = page.getWaRuleMetadata()
        .stream()
        .reduce((first, second) -> second)
        .orElseThrow(() -> new DeleteRuleException("no Page rule found"));
    this.deleted.active(lastRule);
    this.requester.deleteBusinessRule(this.page.active().id(), lastRule.getId());
  }

  @Then("the rule is deleted")
  public void the_rule_is_deleted() {
    WaRuleMetadata deleted = this.deleted.active();
    WaRuleMetadata rule = this.entityManager.find(WaRuleMetadata.class, deleted.getId());
    assertNull(rule);
  }
}
