package gov.cdc.nbs.questionbank.page;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AddQuestionToPageSteps {

  @Autowired private AddQuestionToPageRequester requester;

  @Autowired Active<ResultActions> activeResponse;

  @Autowired private WaUiMetadataRepository repository;

  @Autowired private QuestionMother questionMother;

  @Autowired private ObjectMapper mapper;

  @Autowired private PageMother pageMother;

  @Given("I add a question to a page")
  public void i_add_a_question_to_a_page() throws Exception {
    WaQuestion question = questionMother.one();
    WaTemplate page = pageMother.one();
    WaUiMetadata subsection =
        page.getUiMetadata().stream()
            .filter(ui -> ui.getNbsUiComponentUid() == 1016l)
            .findFirst()
            .orElseThrow();
    AddQuestionRequest request =
        new AddQuestionRequest(Collections.singletonList(question.getId()));

    activeResponse.active(requester.request(page.getId(), subsection.getId(), request));
  }

  @Given("I add the {string} question to a page")
  public void i_add_question_page(String questionUniqueId) throws Exception {
    WaQuestion question = questionMother.findByUniqueId(questionUniqueId);
    WaTemplate page = pageMother.one();
    WaUiMetadata subsection =
        page.getUiMetadata().stream()
            .filter(ui -> ui.getNbsUiComponentUid() == 1016l)
            .findFirst()
            .orElseThrow();
    AddQuestionRequest request =
        new AddQuestionRequest(Collections.singletonList(question.getId()));

    activeResponse.active(requester.request(page.getId(), subsection.getId(), request));
  }

  @Then("the question is added to the page at order number {int}")
  public void the_question_is_added_to_the_page(Integer order) throws Exception {
    MockHttpServletResponse result =
        activeResponse.active().andExpect(status().isOk()).andReturn().getResponse();
    AddQuestionResponse response =
        mapper.readValue(result.getContentAsByteArray(), AddQuestionResponse.class);
    assertNotNull(response);
    assertEquals(1, response.ids().size());
    WaUiMetadata metadata = repository.findById(response.ids().get(0)).orElseThrow();
    assertEquals(order, metadata.getOrderNbr());
    assertEquals(questionMother.one().getQuestionIdentifier(), metadata.getQuestionIdentifier());
  }
}
