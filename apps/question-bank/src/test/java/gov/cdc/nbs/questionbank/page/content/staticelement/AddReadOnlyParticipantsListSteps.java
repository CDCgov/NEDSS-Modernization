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
public class AddReadOnlyParticipantsListSteps {
    @Autowired
    private PageStaticController pageStaticController;

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Autowired
    private PageMother mother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    private Long readOnlyParticipantsListId;


    @When("I send a read only participants list request")
    public void i_send_a_read_only_participants_list_request() {
        // create request
        WaTemplate temp = mother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();

        try {
            readOnlyParticipantsListId = pageStaticController.addStaticReadOnlyParticipantsList(
                    temp.getId(),
                    new StaticContentRequests.AddDefault(null, subsection.getId()))
                    .componentId();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("a read only participants list element is created")
    public void a_read_only_participants_list_element_is_created() {
        assertNotNull(readOnlyParticipantsListId);
        WaUiMetadata readOnlyParticipantsEnt = waUiMetaDataRepository.findById(readOnlyParticipantsListId).orElseThrow();
        assertEquals(readOnlyParticipantsListId, readOnlyParticipantsEnt.getId());
        assertEquals(1030L, readOnlyParticipantsEnt.getNbsUiComponentUid().longValue());
    }
}
