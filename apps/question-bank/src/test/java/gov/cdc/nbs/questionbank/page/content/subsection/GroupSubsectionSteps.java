package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.question.PageQuestionController;
import gov.cdc.nbs.questionbank.page.content.staticelement.PageStaticController;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class GroupSubsectionSteps {

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    ResponseEntity<String> response;

    @Autowired
    PageQuestionController pageQuestionController;

    @Autowired
    PageStaticController pageStaticController;

    @Autowired
    private SubsectionRequester requester;

    private final Active<ResultActions> groupReponse = new Active<>();

    @When("I send a group subsection request")
    public void i_send_a_group_subsection_request() {
        WaTemplate temp = pageMother.one();
        WaUiMetadata subsection = temp.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1016L)
                .findFirst()
                .orElseThrow();
        try {
            groupReponse.active(requester.subsectionGroup(
                    temp.getId(), new GroupSubSectionRequest(
                            subsection.getId(),
                            "BLOCK_NAME",
                            getBatchList(temp),
                            2)));
        } catch (Exception e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the subsection is grouped")
    public void the_subsection_is_grouped() throws Exception {
        groupReponse.active().andExpect(status().isOk());
    }

    @Then("An Update SubSection Exception is thrown")
    public void the_subsection_is_not_grouped() {
        assertNull(response);
        assertTrue(exceptionHolder.getException() instanceof UpdateSubSectionException);
    }

    List<GroupSubSectionRequest.Batch> getBatchList(WaTemplate page) {
        WaUiMetadata question = page.getUiMetadata().stream()
                .filter(ui -> ui.getNbsUiComponentUid() == 1011L)
                .findFirst()
                .orElseThrow();
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();

        batchList.add(new GroupSubSectionRequest.Batch(question.getId(), 'Y', "header_", 100));
        return batchList;
    }

    WaUiMetadata getSection(WaTemplate page) {
        WaUiMetadata section = page.getUiMetadata().stream()
                .filter(u -> u.getNbsUiComponentUid() == 1016l)
                .findFirst()
                .orElseThrow();
        return section;
    }
}
