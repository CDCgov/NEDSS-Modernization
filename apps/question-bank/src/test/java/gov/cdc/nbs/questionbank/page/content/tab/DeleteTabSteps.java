package gov.cdc.nbs.questionbank.page.content.tab;


import static org.junit.Assert.assertNull;
import java.util.List;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
public class DeleteTabSteps {

    @Autowired
    private TabController tabController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    Active<PageIdentifier> page;

    @Autowired
    EntityManager entityManager;

    @Autowired
    private UserDetailsProvider user;

    private final Active<WaUiMetadata> deleted = new Active<>();

    @Before("@delete_tab")
    public void clean() {
        deleted.reset();
    }

    @Given("I send a delete tab request")
    public void i_send_a_delete_tab_request() {

        PageIdentifier identifier = this.page.active();

        WaTemplate page = this.entityManager.find(WaTemplate.class, identifier.id());

        List<WaUiMetadata> tabs = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1010L)
                .toList();

        // the last tab is empty, so it can be deleted
        WaUiMetadata tab = tabs.get(tabs.size() - 1);
        this.deleted.active(tab);

        try {
            tabController.deleteTab(page.getId(), tab.getId(), user.getCurrentUserDetails());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the tab is deleted")
    public void the_tab_is_deleted() {
        WaUiMetadata deleted = this.deleted.active();

        WaUiMetadata tab = this.entityManager.find(WaUiMetadata.class, deleted.getId());
        assertNull(tab);
    }
}
