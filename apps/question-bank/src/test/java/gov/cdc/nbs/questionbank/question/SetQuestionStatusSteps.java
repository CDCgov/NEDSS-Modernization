package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.question.WaQuestionHist;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.QuestionStatusRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SetQuestionStatusSteps {

    @Autowired
    private QuestionController controller;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private WaQuestionRepository repository;

    @Autowired
    private QuestionMother questionMother;

    @Autowired
    private WaQuestionHistRepository histRepository;


    @Given("A text question exists")
    public void a_question_exists() {
        questionMother.clean();
        questionMother.textQuestion();
    }

    @When("I update a question's status to {string}")
    public void I_update_a_questions_status(String status) {
        WaQuestion question = questionMother.one();
        try {
            controller.setQuestionStatus(question.getId(),
                    new QuestionStatusRequest("Active".equals(status) ? true : false));
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the question's status is set to {string}")
    public void the_question_status_is_set_to(String status) {
        WaQuestion originalQuestion = questionMother.one();
        WaQuestion updatedQuestion = repository.findById(originalQuestion.getId()).get();
        assertEquals(status, updatedQuestion.getRecordStatusCd());
        assertTrue(originalQuestion.getRecordStatusTime().compareTo(updatedQuestion.getRecordStatusTime()) < 0);
    }

    @Then("a question history is created")
    public void a_question_history_is_created() {
        WaQuestion originalQuestion = questionMother.one();
        WaQuestionHist history = histRepository.findAll()
                .stream()
                .filter(h -> h.getWaQuestionUid().getId().equals(originalQuestion.getId()))
                .findFirst()
                .orElseThrow();

        assertEquals(originalQuestion.getVersionCtrlNbr(), history.getVersionCtrlNbr());
        assertEquals(originalQuestion.getDataType(), history.getDataType());
        assertEquals(originalQuestion.getDataCd(), history.getDataCd());
        assertEquals(originalQuestion.getDataLocation(), history.getDataLocation());
        assertEquals(originalQuestion.getQuestionIdentifier(), history.getQuestionIdentifier());
        assertEquals(originalQuestion.getQuestionOid(), history.getQuestionOid());
        assertEquals(originalQuestion.getQuestionOidSystemTxt(), history.getQuestionOidSystemTxt());
        assertEquals(originalQuestion.getQuestionUnitIdentifier(), history.getQuestionUnitIdentifier());
        assertEquals(originalQuestion.getDataUseCd(), history.getDataUseCd());
        assertEquals(originalQuestion.getQuestionLabel(), history.getQuestionLabel());
        assertEquals(originalQuestion.getQuestionToolTip(), history.getQuestionToolTip());
        assertEquals(originalQuestion.getRdbColumnNm(), history.getRdbColumnNm());
        assertEquals(originalQuestion.getPartTypeCd(), history.getPartTypeCd());
        assertEquals(originalQuestion.getVersionCtrlNbr(), history.getVersionCtrlNbr());
        assertEquals(originalQuestion.getUnitParentIdentifier(), history.getUnitParentIdentifier());
        assertEquals(originalQuestion.getQuestionGroupSeqNbr(), history.getQuestionGroupSeqNbr());
        assertEquals(originalQuestion.getFutureDateIndCd(), history.getFutureDateIndCd());
        assertEquals(originalQuestion.getLegacyDataLocation(), history.getLegacyDataLocation());
        assertEquals(originalQuestion.getRepeatsIndCd(), history.getRepeatsIndCd());
        assertEquals(originalQuestion.getLocalId(), history.getLocalId());
        assertEquals(originalQuestion.getQuestionNm(), history.getQuestionNm());
        assertEquals(originalQuestion.getGroupNm(), history.getGroupNm());
        assertEquals(originalQuestion.getSubGroupNm(), history.getSubGroupNm());
        assertEquals(originalQuestion.getDescTxt(), history.getDescTxt());
        assertEquals(originalQuestion.getRptAdminColumnNm(), history.getRptAdminColumnNm());
        assertEquals(originalQuestion.getNndMsgInd(), history.getNndMsgInd());
        assertEquals(originalQuestion.getQuestionIdentifierNnd(), history.getQuestionIdentifierNnd());
        assertEquals(originalQuestion.getQuestionLabelNnd(), history.getQuestionLabelNnd());
        assertEquals(originalQuestion.getQuestionRequiredNnd(), history.getQuestionRequiredNnd());
        assertEquals(originalQuestion.getQuestionDataTypeNnd(), history.getQuestionDataTypeNnd());
        assertEquals(originalQuestion.getHl7SegmentField(), history.getHl7SegmentField());
        assertEquals(originalQuestion.getOrderGroupId(), history.getOrderGroupId());
        assertEquals(originalQuestion.getRecordStatusCd(), history.getRecordStatusCd());
        assertEquals(
                originalQuestion.getRecordStatusTime().truncatedTo(ChronoUnit.SECONDS),
                history.getRecordStatusTime().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(originalQuestion.getNbsUiComponentUid(), history.getNbsUiComponentUid());
        assertEquals(originalQuestion.getStandardQuestionIndCd(), history.getStandardQuestionIndCd());
        assertEquals(originalQuestion.getEntryMethod(), history.getEntryMethod());
        assertEquals(originalQuestion.getQuestionType(), history.getQuestionType());
        assertEquals(originalQuestion.getAdminComment(), history.getAdminComment());
        assertEquals(originalQuestion.getRdbColumnNm(), history.getRdbColumnNm());
        assertEquals(originalQuestion.getUserDefinedColumnNm(), history.getUserDefinedColumnNm());
        assertEquals(originalQuestion.getStandardNndIndCd(), history.getStandardNndIndCd());
        assertEquals(originalQuestion.getLegacyQuestionIdentifier(), history.getLegacyQuestionIdentifier());
        assertEquals(originalQuestion.getOtherValueIndCd(), history.getOtherValueIndCd());
        assertEquals(originalQuestion.getSourceNm(), history.getSourceNm());
        assertEquals(originalQuestion.getCoinfectionIndCd(), history.getCoinfectionIndCd());
        assertEquals(originalQuestion.getAddUserId(), history.getAddUserId());
        assertEquals(
                originalQuestion.getAddTime().truncatedTo(ChronoUnit.SECONDS),
                history.getAddTime().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(
                originalQuestion.getLastChgTime().truncatedTo(ChronoUnit.SECONDS),
                history.getLastChgTime().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(originalQuestion.getLastChgUserId(), history.getLastChgUserId());
    }
}
