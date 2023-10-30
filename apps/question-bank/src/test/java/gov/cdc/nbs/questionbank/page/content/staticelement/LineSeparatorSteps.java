package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Optional;
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
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class LineSeparatorSteps {

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

    private Long lineSeparatorId;

    @When("I send a delete line separator request")
    public void i_send_a_delete_line_separator_request() {
        WaTemplate temp = mother.one();

        pageStaticController.deleteStaticElement(temp.getId(), new StaticContentRequests.DeleteElement(lineSeparatorId));
    }

    @Then("a line separator is deleted")
    public void a_line_separator_is_deleted() {
        assertEquals(Optional.empty(), waUiMetaDataRepository.findById(lineSeparatorId));
    }


    @When("I send an add line separator request")
    public void i_send_an_add_tab_request() {
        // create request
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        try {
            lineSeparatorId = authenticated.using(user -> pageStaticController.addStaticLineSeparator(
                    temp.getId(),
                    new StaticContentRequests.AddDefault(
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

    @Then("a line separator is created")
    public void a_line_separator_is_created() {
        assertNotNull(lineSeparatorId);
        WaUiMetadata lineSeparatorEnt = waUiMetaDataRepository.findById(lineSeparatorId).orElseThrow();
        assertEquals(lineSeparatorId, lineSeparatorEnt.getId());
        assertEquals(1012L, lineSeparatorEnt.getNbsUiComponentUid().longValue());
    }

    public Long getId() {
        return this.lineSeparatorId;
    }
}
