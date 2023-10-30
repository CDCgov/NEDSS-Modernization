package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class AddReadOnlyCommentsSteps {
    @Autowired
    private PageStaticController pageStaticController;

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private Long readOnlyCommentsId;

    @When("I send a read only comments element request with {string}")
    public void i_send_a_read_only_comments_element_request(String comments) {
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        try {
            readOnlyCommentsId = pageStaticController.addStaticReadOnlyComments(
                    temp.getId(),
                    new StaticContentRequests.AddReadOnlyComments(
                            comments,
                            null,
                            subsection.getId()))
                    .componentId();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("a read only comments element is created with {string}")
    public void a_read_only_comments_element_is_created(String comments) {
        assertNotNull(readOnlyCommentsId);
        WaUiMetadata readOnlyCommentsEnt = waUiMetaDataRepository.findById(readOnlyCommentsId).orElseThrow();
        assertEquals(1014L, readOnlyCommentsEnt.getNbsUiComponentUid().longValue());
        assertEquals(comments, readOnlyCommentsEnt.getQuestionLabel());
    }
}
