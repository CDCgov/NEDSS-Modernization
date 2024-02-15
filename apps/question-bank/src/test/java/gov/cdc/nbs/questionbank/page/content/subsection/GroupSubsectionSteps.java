package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.question.PageQuestionController;
import gov.cdc.nbs.questionbank.page.content.staticelement.PageStaticController;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GroupSubsectionSteps {

    private final PageMother pageMother;
    private final ExceptionHolder exceptionHolder;
    ResponseEntity<String> response;
    PageQuestionController pageQuestionController;
    PageStaticController pageStaticController;
    private final SubSectionController subSectionController;
    private final SubsectionRequester requester;
    private final Active<ResultActions> groupReponse;
    private UserDetailsProvider user;

    GroupSubsectionSteps(PageMother pageMother, ExceptionHolder exceptionHolder,
            SubsectionRequester requester, SubSectionController subSectionController, UserDetailsProvider user) {
        this.pageMother = pageMother;
        this.exceptionHolder = exceptionHolder;
        this.requester = requester;
        groupReponse = new Active<>();
        this.subSectionController = subSectionController;
        this.user = user;
    }


    @When("I send a group subsection request")
    public void i_send_a_group_subsection_request() throws Exception {
        WaTemplate temp = pageMother.one();
        WaUiMetadata subsection = pageMother.pageContent()
                .stream()
                .filter(m -> m.getNbsUiComponentUid().equals(1016l)) // find subsection
                .findFirst()
                .orElseThrow();

        groupReponse.active(requester.subsectionGroup(
                temp.getId(), new GroupSubSectionRequest(
                        subsection.getId(),
                        "BLOCK_NAME",
                        getBatchList(temp),
                        2)));
    }

    @Then("the subsection is grouped")
    public void the_subsection_is_grouped() throws Exception {
        groupReponse.active().andExpect(status().isOk());
    }

    @Then("a bad request exception is thrown")
    public void the_subsection_is_not_grouped() throws Exception {
        groupReponse.active().andExpect(status().isBadRequest());
    }

    @Then("a redirect is initiated")
    public void a_redirect_is_initiated() throws Exception {
        groupReponse.active().andExpect(status().isFound());
    }

    List<GroupSubSectionRequest.Batch> getBatchList(WaTemplate page) {
        WaUiMetadata question = pageMother.pageContent().stream()
                .filter(ui -> ui.getNbsUiComponentUid().equals(1008l))
                .findFirst()
                .orElse(null);
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();

        if (question != null) {
            batchList.add(new GroupSubSectionRequest.Batch(question.getId(), 'Y', "header_", 100));
        }
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
