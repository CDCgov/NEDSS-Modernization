package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class PageDeletorSteps {
    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private WaTemplateRepository waTemplateRepository;

    @Autowired
    private PageRequest request;

    private final Active<ResultActions> response = new Active<>();
    private final Active<WaTemplate> currPage = new Active<>();
    private final Active<WaTemplate> draftPage = new Active<>();

    @Given("I create a delete page request with draft page")
    public void i_create_a_delete_page_request_with_draft_page() {
        WaTemplate temp = pageMother.one();
        temp.setTemplateType(PageConstants.DRAFT);
        currPage.active(temp);
    }

    @Given("I create a delete page request with published with draft page")
    public void i_create_a_delete_page_request_with_published_with_draft_page() {
        WaTemplate temp = pageMother.one();
        temp.setTemplateType(PageConstants.PUBLISHED_WITH_DRAFT);
        temp.setFormCd("PG_testing");
        WaTemplate draftPage = pageMother.createPageDraft(temp);
        currPage.active(temp);
        this.draftPage.active(draftPage);
    }

    @When("I send a delete page request")
    public void i_send_a_delete_page_request() {
        try {
            this.response.active(request.deletePageRequest(
                    currPage.active().getId()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the page is deleted")
    public void the_page_is_deleted() {
        assertEquals(Optional.empty(), waTemplateRepository.findById(currPage.active().getId()));
    }

    @Then("the page is deleted and changed to published")
    public void the_page_deleted_and_changed_to_published() {
        Optional<WaTemplate> temp = waTemplateRepository.findById(currPage.active().getId());
        assertEquals(PageConstants.PUBLISHED, temp.get().getTemplateType());
        assertEquals(Optional.empty(), waTemplateRepository.findById(draftPage.active().getId()));
    }
}
