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
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.question.exception.UniqueQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.UnitType;
import gov.cdc.nbs.questionbank.question.request.QuestionType;
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
                    response = controller.createTextQuestion((CreateQuestionRequest.Text) request);
                    break;
                case "date":
                    request = QuestionRequestMother.dateRequest();
                    response = controller.createDateQuestion((CreateQuestionRequest.Date) request);
                    break;
                case "numeric":
                    request = QuestionRequestMother.numericRequest();
                    response = controller.createNumericQuestion((CreateQuestionRequest.Numeric) request);
                    break;
                case "coded":
                    request = QuestionRequestMother.codedRequest(4150L); // Yes, No, Unknown Value set in test db
                    response = controller.createCodedQuestion((CreateQuestionRequest.Coded) request);
                    break;
                default:
                    throw new NotYetImplementedException();
            }
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I send a create question request with duplicate {string}")
    public void create_with_duplicate_field(String field) {
        WaQuestion existing = questionRepository.findAll().get(0);
        CreateQuestionRequest.Text request;
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
        CreateQuestionRequest.Text textRequest = (CreateQuestionRequest.Text) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(textRequest.defaultValue(), question.getDefaultValue());
        assertEquals(textRequest.mask(), question.getMask());
        assertEquals(textRequest.fieldLength(), question.getFieldSize());
        assertEquals(QuestionType.TEXT, textRequest.type());
    }

    private void validateDateQuestion() {
        assertNotNull(response);
        DateQuestionEntity question =
                (DateQuestionEntity) questionRepository.findById(response.id()).orElseThrow();
        CreateQuestionRequest.Date dateRequest = (CreateQuestionRequest.Date) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(dateRequest.mask(), question.getMask());
        assertEquals(dateRequest.allowFutureDates() ? 'T' : 'F', question.getFutureDateIndCd().charValue());
        assertEquals(QuestionType.DATE, dateRequest.type());
    }

    private void validateNumericQuestion() {
        assertNotNull(response);
        NumericQuestionEntity question = (NumericQuestionEntity) questionRepository.findById(response.id())
                .orElseThrow();
        CreateQuestionRequest.Numeric numericRequest = (CreateQuestionRequest.Numeric) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(numericRequest.mask(), question.getMask());
        assertEquals(numericRequest.fieldLength(), question.getFieldSize());
        assertEquals(numericRequest.defaultValue(), question.getDefaultValue());
        assertEquals(numericRequest.minValue(), question.getMinValue());
        assertEquals(numericRequest.maxValue(), question.getMaxValue());
        if (numericRequest.relatedUnitsLiteral() != null) {
            assertEquals(UnitType.LITERAL.toString(), question.getUnitTypeCd());
            assertEquals(numericRequest.relatedUnitsLiteral(), question.getUnitValue());
        } else if (numericRequest.relatedUnitsValueSet() != null) {
            assertEquals(UnitType.CODED.toString(), question.getUnitTypeCd());
            assertEquals(numericRequest.relatedUnitsValueSet().toString(), question.getUnitValue());
        }
        assertEquals(QuestionType.NUMERIC, numericRequest.type());
    }

    private void validateCodedQuestion() {
        assertNotNull(response);
        CodedQuestionEntity question =
                (CodedQuestionEntity) questionRepository.findById(response.id()).orElseThrow();
        CreateQuestionRequest.Coded codedRequest = (CreateQuestionRequest.Coded) request;
        assertEquals(question.getId().longValue(), response.id());
        assertEquals(codedRequest.valueSet(), question.getCodeSetGroupId());
        assertEquals(codedRequest.defaultValue(), question.getDefaultValue());
        assertEquals('F', question.getOtherValueIndCd().charValue());
        assertEquals(QuestionType.CODED, codedRequest.type());
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
