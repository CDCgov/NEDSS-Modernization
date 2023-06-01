package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.questionbank.entities.AuditInfo;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entities.ValueSet;
import gov.cdc.nbs.questionbank.question.repository.DateQuestionRepository;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;
import gov.cdc.nbs.questionbank.question.repository.DropdownQuestionRepository;
import gov.cdc.nbs.questionbank.question.repository.NumericQuestionRepository;
import gov.cdc.nbs.questionbank.question.repository.TextQuestionRepository;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;
import gov.cdc.nbs.questionbank.support.QuestionDataMother;
import gov.cdc.nbs.questionbank.support.UserMother;
import gov.cdc.nbs.questionbank.support.ValueSetMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateQuestionSteps {

    @Autowired
    private UserMother userMother;

    @Autowired
    private ValueSetMother valueSetMother;

    @Autowired
    private CreateQuestionResolver resolver;

    @Autowired
    private DisplayElementRepository repository;

    @Autowired
    private TextQuestionRepository textQuestionRepository;

    @Autowired
    private DropdownQuestionRepository dropdownQuestionRepository;

    @Autowired
    private DateQuestionRepository dateQuestionRepository;

    @Autowired
    private NumericQuestionRepository numericQuestionRepository;

    private Long userId;

    private ValueSet valueSet;

    private Questionnaire.Question response;

    private Exception exception;

    @Given("No questions exist")
    public void no_questions_exist() {
        repository.deleteAll();
    }

    @Given("A value set exists")
    public void a_value_set_exists() {
        valueSet = valueSetMother.yesNoUnknown();
    }

    @Given("I am not logged in")
    public void i_am_not_logged_in() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Given("I am an admin user")
    public void i_am_an_admin_user() {
        AuthUser adminUser = userMother.adminUser();
        userId = adminUser.getId();
    }

    @When("I submit a create {string} question request")
    public void send_create_question(String requestType) {
        try {
            switch (requestType) {
                case "text": {
                    response = resolver.createTextQuestion(QuestionDataMother.textQuestionData());
                    break;
                }
                case "numeric": {
                    response = resolver.createNumericQuestion(QuestionDataMother.numericQuestionData(valueSet.getId()));
                    break;
                }
                case "date": {
                    response = resolver.createDateQuestion(QuestionDataMother.dateQuestionData());
                    break;
                }
                case "dropdown": {
                    response = resolver.createDropdownQuestion(
                            QuestionDataMother.dropdownQuestionData(
                                    valueSet.getId(),
                                    valueSet.getValues().get(0).getId()));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unsupported request type : " + requestType);
                }
            }
            assertNotNull(response);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exception = e;
        }
    }

    @Then("the {string} question is created")
    public void is_created(String requestType) {

        // verify proper entity was created
        switch (requestType) {
            case "text": {
                validateTextQuestion();
                break;
            }
            case "numeric": {
                validateNumericQuestion();
                break;
            }
            case "date": {
                validateDateQuestion();
                break;
            }
            case "dropdown": {
                validateDropDownQuestion();
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported request type : " + requestType);
            }
        }

    }

    @Then("I am not authorized")
    public void i_am_not_authorized() {
        assertNotNull(exception);
        assertNull(response);
    }

    private void validateTextQuestion() {
        List<TextQuestionEntity> results = textQuestionRepository.findAll();
        assertEquals(1, results.size());
        var created = results.get(0);
        var actual = QuestionDataMother.textQuestionData();

        assertEquals(actual.label(), created.getLabel());
        assertEquals(actual.tooltip(), created.getTooltip());
        assertEquals(actual.maxLength(), created.getMaxLength());
        assertEquals(actual.placeholder(), created.getPlaceholder());
        assertEquals(actual.defaultValue(), created.getDefaultTextValue());
        assertEquals(actual.codeSet(), created.getCodeSet());

        validateAudit(created.getAudit());
    }

    private void validateNumericQuestion() {
        List<NumericQuestionEntity> results = numericQuestionRepository.findAll();
        assertEquals(1, results.size());
        var created = results.get(0);
        var actual = QuestionDataMother.numericQuestionData(valueSet.getId());

        assertEquals(actual.label(), created.getLabel());
        assertEquals(actual.tooltip(), created.getTooltip());
        assertEquals(actual.minValue(), created.getMinValue());
        assertEquals(actual.maxValue(), created.getMaxValue());
        assertEquals(actual.defaultValue(), created.getDefaultNumericValue());
        assertEquals(actual.unitValueSet(), created.getUnitsSet().getId());
        assertEquals(actual.codeSet(), created.getCodeSet());

        validateAudit(created.getAudit());
    }

    private void validateDropDownQuestion() {
        List<DropdownQuestionEntity> results = dropdownQuestionRepository.findAll();
        assertEquals(1, results.size());
        var created = results.get(0);
        var actual = QuestionDataMother.dropdownQuestionData(
                valueSet.getId(),
                valueSet.getValues().get(0).getId());

        assertEquals(actual.label(), created.getLabel());
        assertEquals(actual.tooltip(), created.getTooltip());
        assertEquals(actual.defaultValue(), created.getDefaultAnswer().getId());
        assertEquals(actual.isMultiSelect(), created.isMultiSelect());
        assertEquals(actual.valueSet(), created.getValueSet().getId());
        assertEquals(actual.codeSet(), created.getCodeSet());

        validateAudit(created.getAudit());
    }

    private void validateDateQuestion() {
        List<DateQuestionEntity> results = dateQuestionRepository.findAll();
        assertEquals(1, results.size());
        var created = results.get(0);
        var actual = QuestionDataMother.dateQuestionData();

        assertEquals(actual.label(), created.getLabel());
        assertEquals(actual.tooltip(), created.getTooltip());
        assertEquals(actual.allowFutureDates(), created.isAllowFuture());
        assertEquals(actual.codeSet(), created.getCodeSet());

        validateAudit(created.getAudit());
    }

    private void validateAudit(AuditInfo auditInfo) {
        var thirtySecondsAgo = Instant.now().minusSeconds(30).getEpochSecond();

        assertEquals(userId, auditInfo.getAddUserId());
        assertTrue(auditInfo.getAddTime().getEpochSecond() > thirtySecondsAgo);
        assertEquals(userId, auditInfo.getLastUpdateUserId());
        assertTrue(auditInfo.getLastUpdate().getEpochSecond() > thirtySecondsAgo);
    }

}
