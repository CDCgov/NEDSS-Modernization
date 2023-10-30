package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageMother;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class AddHyperLinkSteps {
    @Autowired
    private PageStaticController pageStaticController;

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Autowired
    private PageMother mother;

    @Autowired
    private Authenticated authenticated;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private Long hyperLinkId;

    @When("I send a hyperlink request with {string} and {string}")
    public void i_send_a_hyperlink_request(String label, String link) {
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        try {
            hyperLinkId = authenticated.using(user -> pageStaticController.addStaticHyperLink(
                    temp.getId(),
                    new StaticContentRequests.AddHyperlink(
                            label,
                            link,
                            null,
                            subsection.getId()),
                    user))
                    .componentId();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("a hyperlink is created with {string} and {string}")
    public void a_hyperlink_is_created(String label, String link) {
        assertNotNull(hyperLinkId);
        WaUiMetadata hyperLinkEnt = waUiMetaDataRepository.findById(hyperLinkId).orElseThrow();
        assertEquals(1003L, hyperLinkEnt.getNbsUiComponentUid().longValue());
        assertEquals(label, hyperLinkEnt.getQuestionLabel());
        assertEquals(link, hyperLinkEnt.getDefaultValue());
    }

}
