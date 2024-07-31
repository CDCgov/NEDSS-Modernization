package gov.cdc.nbs.questionbank.page.content.tab;


import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import jakarta.persistence.EntityManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNull;

@Transactional
public class DeleteTabSteps {


    private final TabController tabController;

    private final ExceptionHolder exceptionHolder;

    private final Active<PageIdentifier> activePage;

    private final EntityManager entityManager;

    private final UserDetailsProvider user;

    private final Active<WaUiMetadata> activeDeleted = new Active<>();

    DeleteTabSteps(
        final TabController tabController,
        final ExceptionHolder exceptionHolder,
        final Active<PageIdentifier> activePage,
        final EntityManager entityManager,
        final UserDetailsProvider user
    ) {
        this.tabController = tabController;
        this.exceptionHolder = exceptionHolder;
        this.activePage = activePage;
        this.entityManager = entityManager;
        this.user = user;
    }

    @Before("@delete_tab")
    public void clean() {
        activeDeleted.reset();
    }

    @Given("I send a delete tab request")
    public void i_send_a_delete_tab_request() {

        PageIdentifier identifier = this.activePage.active();

        WaTemplate page = this.entityManager.find(WaTemplate.class, identifier.id());

        List<WaUiMetadata> tabs = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1010L)
                .toList();

        // the last tab is empty, so it can be deleted
        WaUiMetadata tab = tabs.getLast();
        this.activeDeleted.active(tab);

        try {
            tabController.deleteTab(page.getId(), tab.getId(), user.getCurrentUserDetails());
        } catch (AccessDeniedException | AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the tab is deleted")
    public void the_tab_is_deleted() {
        WaUiMetadata deleted = this.activeDeleted.active();

        WaUiMetadata tab = this.entityManager.find(WaUiMetadata.class, deleted.getId());
        assertNull(tab);
    }
}
