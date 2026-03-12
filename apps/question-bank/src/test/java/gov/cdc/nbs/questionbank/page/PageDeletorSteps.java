package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Optional;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PageDeletorSteps {

  private final ExceptionHolder exceptionHolder;

  private final PageMother pageMother;

  private final WaTemplateRepository waTemplateRepository;

  private final PageRequest request;

  private final Active<ResultActions> response = new Active<>();
  private final Active<WaTemplate> currPage = new Active<>();
  private final Active<WaTemplate> draftPage = new Active<>();

  PageDeletorSteps(
      final ExceptionHolder exceptionHolder,
      final PageMother pageMother,
      final WaTemplateRepository waTemplateRepository,
      final PageRequest request) {
    this.exceptionHolder = exceptionHolder;
    this.pageMother = pageMother;
    this.waTemplateRepository = waTemplateRepository;
    this.request = request;
  }

  @Given("I create a delete page request with published with draft page")
  public void i_create_a_delete_page_request_with_published_with_draft_page() {
    WaTemplate temp = pageMother.one();
    temp.setTemplateType(PageConstants.DRAFT);
    temp.setFormCd("PG_testing");
    WaTemplate publishWithDraft = pageMother.createPagePublishedWithDraft(temp);
    currPage.active(publishWithDraft);
    this.draftPage.active(temp);
  }

  @Given("I create a delete page request with single draft page")
  public void i_create_a_delete_page_request_with_single_draft_page() {
    WaTemplate temp = pageMother.one();
    temp.setTemplateType(PageConstants.DRAFT);
    draftPage.active(temp);
  }

  @When("I send a delete page request")
  public void i_send_a_delete_page_request() {
    try {
      this.response.active(request.deletePageRequest(draftPage.active().getId()));
    } catch (Exception e) {
      exceptionHolder.setException(e);
    }
  }

  @Then("the draft is deleted and the page changed to published")
  public void the_page_deleted_and_changed_to_published() {
    Optional<WaTemplate> temp = waTemplateRepository.findById(currPage.active().getId());
    assertEquals(PageConstants.PUBLISHED, temp.get().getTemplateType());
    assertEquals(Optional.empty(), waTemplateRepository.findById(draftPage.active().getId()));
  }

  @Then("the draft is deleted")
  public void the_page_is_deleted() {
    assertEquals(Optional.empty(), waTemplateRepository.findById(draftPage.active().getId()));
  }
}
