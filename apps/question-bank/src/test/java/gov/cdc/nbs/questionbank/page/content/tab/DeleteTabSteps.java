package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
public class DeleteTabSteps {

    @Autowired
    private TabController tabController;

    @Autowired
    private WaUiMetaDataRepository waUiMetadataRepository;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    Active<PageIdentifier> page;

    @Autowired
    EntityManager entityManager;

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
        this.deleted.active(tabs.get(tabs.size() - 1));

        try {
            tabController.deleteTab(page.getId(), tabToDelete.getId());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @Then("the tab is deleted")
    public void the_tab_is_deleted() {

        assertTrue(waUiMetadataRepository.findById(tabToDelete.getId()).isEmpty());
    }
}
