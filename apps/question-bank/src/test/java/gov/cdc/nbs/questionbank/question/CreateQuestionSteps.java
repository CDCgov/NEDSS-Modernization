package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.UnitType;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.QuestionMother;
import gov.cdc.nbs.questionbank.support.QuestionRequestMother;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateQuestionSteps {
    @Autowired
    private UserMother userMother;

    @Autowired
    private QuestionMother questionMother;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private QuestionController controller;

    @Autowired
    private WaQuestionRepository questionRepository;

    private CreateQuestionRequest request;

    private Question response;


    @Given("No questions exist")
    public void no_questions_exist() {
        questionMother.clean();
    }

    @Given("I am an admin user")
    public void i_am_admin_user() {
        userMother.adminUser();
    }

    @Given("I am a user without permissions")
    public void I_am_user_without_permissions() {
        userMother.noPermissions();
    }

    @Given("I am not logged in")
    public void i_am_not_logged_in() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @When("I send a create {string} question request")
    public void i_send_a_create_question_request(String questionType) {
        try {
            switch (questionType) {
                case "text":
                    request = QuestionRequestMother.localTextRequest();
                    response = controller.createTextQuestion((CreateTextQuestionRequest) request);
                    break;
                case "date":
                    request = QuestionRequestMother.dateRequest();
                    response = controller.createDateQuestion((CreateDateQuestionRequest) request);
                    break;
                case "numeric":
                    request = QuestionRequestMother.numericRequest();
                    response = controller.createNumericQuestion((CreateNumericQuestionRequest) request);
                    break;
                case "coded":
                    request = QuestionRequestMother.codedRequest(4150L); // Yes, No, Unknown Value set in test db
                    response = controller.createCodedQuestion((CreateCodedQuestionRequest) request);
                    break;
                default:
                    throw new NotYetImplementedException();
            }
            questionMother.addManaged(response.id());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I send a create question request with duplicate {string}")
    public void create_with_duplicate_field(String field) {
        WaQuestion existing = questionRepository.findAll().get(0);
        CreateTextQuestionRequest request;
        switch (field) {
            case "question name":
                request = QuestionRequestMother.custom(
                        existing.getQuestionNm(),
                        "testsomeIdentifier",
                        "test_custom_col_name",
                        "CUSTOM_RDB_TABLE_NAME",
                        "custom_rdb_col_name");
                break;
            case "question identifier":
                request = QuestionRequestMother.custom(
                        "test custom question nm",
                        existing.getQuestionIdentifier(),
                        "test_custom_col_name",
                        "CUSTOM_RDB_TABLE_NAME",
                        "custom_rdb_col_name");
                break;
            case "data mart column name":
                request = QuestionRequestMother.custom(
                        "testcustom question nm",
                        "testsomeIdentifier",
                        existing.getUserDefinedColumnNm(),
                        "CUSTOM_RDB_TABLE_NAME",
                        "custom_rdb_col_name");
                break;
            case "rdb column name":
                request = QuestionRequestMother.custom(
                        existing.getQuestionNm(),
                        "testsomeIdentifier",
                        "test_custom_col_name",
                        "CUSTOM_RDB_TABLE_NAME",
                        existing.getRdbColumnNm().replace(existing.getRdbTableNm() + "_", ""));
                break;
            default:
                throw new IllegalArgumentException();
        }
        try {
            controller.createTextQuestion(request);
        } catch (UniqueQuestionException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("the {string} question is created")
    public void the_question_is_created(String questionType) {
        switch (questionType) {
            case "text":
                validateTextQuestion();
                break;
            case "date":
                validateDateQuestion();
                break;
            case "numeric":
                validateNumericQuestion();
                break;
            case "coded":
                validateCodedQuestion();
                break;
            default:
                throw new NotYetImplementedException();
        }
    }

    private void validateTextQuestion() {
        assertNotNull(response);
        TextQuestionEntity question =
                (TextQuestionEntity) questionRepository.findById(response.id()).orElseThrow();
        CreateTextQuestionRequest textRequest = (CreateTextQuestionRequest) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(textRequest.getDefaultValue(), question.getDefaultValue());
        assertEquals(textRequest.getMask().toString(), question.getMask());
        assertEquals(textRequest.getFieldLength().toString(), question.getFieldSize());
    }

    private void validateDateQuestion() {
        assertNotNull(response);
        DateQuestionEntity question =
                (DateQuestionEntity) questionRepository.findById(response.id()).orElseThrow();
        CreateDateQuestionRequest dateRequest = (CreateDateQuestionRequest) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(dateRequest.getMask().toString(), question.getMask());
        assertEquals(dateRequest.isAllowFutureDates() ? 'T' : 'F', question.getFutureDateIndCd().charValue());
    }

    private void validateNumericQuestion() {
        assertNotNull(response);
        NumericQuestionEntity question = (NumericQuestionEntity) questionRepository.findById(response.id())
                .orElseThrow();
        CreateNumericQuestionRequest numericRequest = (CreateNumericQuestionRequest) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(numericRequest.getMask().toString(), question.getMask());
        assertEquals(numericRequest.getFieldLength().toString(), question.getFieldSize());
        assertEquals(numericRequest.getDefaultValue().toString(), question.getDefaultValue());
        assertEquals(numericRequest.getMinValue(), question.getMinValue());
        assertEquals(numericRequest.getMaxValue(), question.getMaxValue());
        if (numericRequest.getRelatedUnitsLiteral() != null) {
            assertEquals(UnitType.LITERAL.toString(), question.getUnitTypeCd());
            assertEquals(numericRequest.getRelatedUnitsLiteral(), question.getUnitValue());
        } else if (numericRequest.getRelatedUnitsValueSet() != null) {
            assertEquals(UnitType.CODED.toString(), question.getUnitTypeCd());
            assertEquals(numericRequest.getRelatedUnitsValueSet().toString(), question.getUnitValue());
        }
    }

    private void validateCodedQuestion() {
        assertNotNull(response);
        CodedQuestionEntity question =
                (CodedQuestionEntity) questionRepository.findById(response.id()).orElseThrow();
        CreateCodedQuestionRequest codedRequest = (CreateCodedQuestionRequest) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(codedRequest.getValueSet(), question.getCodeSetGroupId());
        assertEquals(codedRequest.getDefaultValue(), question.getDefaultValue());
        assertEquals('F', question.getOtherValueIndCd().charValue());
    }

    @Then("a no credentials found exception is thrown")
    public void a_not_authorized_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AuthenticationCredentialsNotFoundException);
    }

    @Then("an accessdenied exception is thrown")
    public void a_access_denied_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AccessDeniedException);
    }

    @Then("a question creation exception is thrown")
    public void an_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof CreateQuestionException);
    }

    @Then("a unique question exception is thrown")
    public void an_unique_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof UniqueQuestionException);
    }

}
