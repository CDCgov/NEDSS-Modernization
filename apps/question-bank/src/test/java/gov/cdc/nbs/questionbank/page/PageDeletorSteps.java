package gov.cdc.nbs.questionbank.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class PageDeletorSteps {
    @Autowired
    private PageController pageController;

     @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private WaTemplateRepository watemplateRepo;

    private final Active<ResultActions> response = new Active<>();
    private final Active<WaTemplate> currPage = new Active<>();

    @Given("I create a delete page request with draft page")
    void i_create_a_delete_page_request_with_draft_page() {
        WaTemplate temp = pageMother.one();
        temp.setTemplateType(PageConstants.DRAFT);
        currPage.active(temp);
    }

    @When("I send a delete page request")
    void i_send_a_delete_page_request() {
        
    }

}
