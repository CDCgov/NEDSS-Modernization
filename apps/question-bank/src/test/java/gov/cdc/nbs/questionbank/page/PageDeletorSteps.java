package gov.cdc.nbs.questionbank.page;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import gov.cdc.nbs.questionbank.entity.WaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        temp.setFormCd("PG_testing");
        WaTemplate publishWithDraft = pageMother.createPagePublishedWithDraft(temp);
        currPage.active(publishWithDraft);
        this.draftPage.active(temp);
    }

    @When("I send a delete page request")
    public void i_send_a_delete_page_request() {
        try {
            this.response.active(request.deletePageRequest(
                draftPage.active().getId()));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }


    @Then("the draft is deleted")
    public void the_page_is_deleted() {
        assertEquals(Optional.empty(), waTemplateRepository.findById(draftPage.active().getId()));
    }
}
