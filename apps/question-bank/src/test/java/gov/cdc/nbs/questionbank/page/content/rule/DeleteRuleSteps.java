package gov.cdc.nbs.questionbank.page.content.rule;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaRuleMetadatum;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.pagerules.PageRuleController;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.junit.Assert.assertNull;


public class DeleteRuleSteps {

    @Autowired
    private PageRuleController ruleController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    EntityManager entityManager;

    @Autowired
    private UserDetailsProvider user;

    @Autowired
    Active<PageIdentifier> page;


    private final Active<WaRuleMetadatum> deleted = new Active<>();


    @Given("I send a delete rule request")
    @Transactional
    public void i_send_a_delete_rule_request() {
        PageIdentifier identifier = this.page.active();
        WaTemplate page = this.entityManager.find(WaTemplate.class, identifier.id());
        WaRuleMetadatum lastRule = page.getWaRuleMetadatums()
                .stream()
                .reduce((first, second) -> second)
                .orElse(null);
        this.deleted.active(lastRule);
        try {
            ruleController.deletePageRule(page.getId(), lastRule.getId(), user.getCurrentUserDetails());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the rule is deleted")
    public void the_rule_is_deleted() {
        WaRuleMetadatum deleted = this.deleted.active();
        WaRuleMetadatum rule = this.entityManager.find(WaRuleMetadatum.class, deleted.getId());
        assertNull(rule);
    }
}
