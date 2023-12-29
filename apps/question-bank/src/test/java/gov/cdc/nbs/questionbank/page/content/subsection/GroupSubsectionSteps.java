package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.exception.BadRequestException;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.question.PageQuestionController;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.content.staticelement.PageStaticController;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.page.content.staticelement.response.AddStaticResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class GroupSubsectionSteps {

    @Autowired
    private SubSectionController subsectionController;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private UserDetailsProvider user;

    ResponseEntity<String> response;

    @Autowired
    PageQuestionController pageQuestionController;

    @Autowired
    PageStaticController pageStaticController;

    @Autowired
    private QuestionMother questionMother;

    @Autowired
    private WaUiMetadataRepository repository;

    private List<Long> questionsIds = new ArrayList<>();
    private Long staticElementId;

    @Given("i add a list of questions to a subsection")
    public void i_add_a_list_of_questions_to_a_subsection() {
        WaTemplate page = pageMother.one();
        WaUiMetadata section = getSection(page);
        List<WaQuestion> questionsList = questionMother.list(2);
        try {
            for (WaQuestion question : questionsList) {
                AddQuestionResponse response = pageQuestionController.addQuestionToPage(page.getId(),
                    new AddQuestionRequest(question.getId(), section.getId()),
                    user.getCurrentUserDetails());
                questionsIds.add(response.componentId());
            }
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @Given("i add a list of questions and a static element to a subsection")
    public void i_add_a_list_of_questions_and_a_static_element_to_a_subsection() {
        WaTemplate page = pageMother.one();
        WaUiMetadata section = getSection(page);
        WaQuestion question = questionMother.one();
        try {
            AddQuestionResponse addQuestionResponse = pageQuestionController.addQuestionToPage(page.getId(),
                new AddQuestionRequest(question.getId(), section.getId()), user.getCurrentUserDetails());
            questionsIds.add(addQuestionResponse.componentId());

            StaticContentRequests.AddDefault request = new StaticContentRequests.AddDefault("test_comment",
                section.getId());
            AddStaticResponse addStaticResponse =
                pageStaticController.addStaticLineSeparator(page.getId(), request, user.getCurrentUserDetails());
            staticElementId = addStaticResponse.componentId();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }


    }


    @When("I send a group subsection request")
    public void i_send_a_group_subsection_request() {
        WaTemplate page = pageMother.one();
        WaUiMetadata section = getSection(page);
        try {
            response = subsectionController.groupSubSection(
                page.getId(),
                new GroupSubSectionRequest(
                    section.getId(),
                    "BLOCK_NAME",
                    getBatchList()),
                user.getCurrentUserDetails());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        } catch (BadRequestException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the subsection is grouped")
    public void the_subsection_is_grouped() {
        assertNotNull(response);
        assertTrue(response.getBody().contains("Grouped Successfully"));
        assertEquals(200, response.getStatusCodeValue());
        for (Long questionId : questionsIds) {
            assertEquals("BLOCK_NAME", repository.findById(questionId).get().getBlockNm());
        }
    }

    @Then("An Update SubSection Exception is thrown")
    public void the_subsection_is_not_grouped() {
        assertNull(response);
        assertTrue(exceptionHolder.getException() instanceof UpdateSubSectionException);
    }

    List<GroupSubSectionRequest.Batch> getBatchList() {
        List<GroupSubSectionRequest.Batch> batchList = new ArrayList<>();
        if (staticElementId != null) {
            batchList.add(new GroupSubSectionRequest.Batch(staticElementId, 'Y', "header_" + staticElementId, 50));
            batchList.add(new GroupSubSectionRequest.Batch(questionsIds.get(0), 'Y', "header_" + questionsIds.get(0), 50));
        } else {
            if (!questionsIds.isEmpty()) {
                batchList.add(new GroupSubSectionRequest.Batch(questionsIds.get(0), 'Y', "header_" + questionsIds.get(0), 50));
                batchList.add(new GroupSubSectionRequest.Batch(questionsIds.get(1), 'Y', "header_" + questionsIds.get(1), 50));
            }
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
