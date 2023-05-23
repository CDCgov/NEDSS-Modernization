package gov.cdc.nbs.questionbank.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.awaitility.Awaitility;
import org.awaitility.Durations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest.TextQuestionData;
import gov.cdc.nbs.questionbank.kafka.message.question.QuestionResponse;
import gov.cdc.nbs.questionbank.question.repository.DisplayElementRepository;
import gov.cdc.nbs.questionbank.question.repository.TextQuestionRepository;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateQuestionSteps {

    @Autowired
    private UserMother userMother;

    @Autowired
    private CreateQuestionResolver resolver;

    @Autowired
    private DisplayElementRepository repository;

    @Autowired
    private TextQuestionRepository textQuestionRepository;

    private Long userId;

    private QuestionResponse response;

    private Exception exception;

    @Given("No questions exist")
    public void no_questions_exist() {
        repository.deleteAll();;
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

    @When("I submit a create text question request")
    public void send_create_text_question() {
        try {
            response = resolver.createTextQuestion(textQuestionData());
            assertNotNull(response);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("the text question is created")
    public void text_question_is_created() {
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .pollDelay(Durations.ONE_SECOND)
                .until(() -> repository.findAll().size() > 0);

        List<TextQuestionEntity> results = textQuestionRepository.findAll();
        assertEquals(1, results.size());
        var created = results.get(0);
        var actual = textQuestionData();

        assertEquals(actual.label(), created.getLabel());
        assertEquals(actual.tooltip(), created.getTooltip());
        assertEquals(actual.maxLength(), created.getMaxLength());
        assertEquals(actual.placeholder(), created.getPlaceholder());

        var thirtySecondsAgo = Instant.now().minusSeconds(30).getEpochSecond();

        assertEquals(userId, created.getAudit().getAddUserId());
        assertTrue(created.getAudit().getAddTime().getEpochSecond() > thirtySecondsAgo);
        assertEquals(userId, created.getAudit().getLastUpdateUserId());
        assertTrue(created.getAudit().getLastUpdate().getEpochSecond() > thirtySecondsAgo);
    }

    @Then("I am not authorized")
    public void i_am_not_authorized() {
        assertNotNull(exception);
        assertNull(response);
    }

    private TextQuestionData textQuestionData() {
        return new TextQuestionData(
                "test label",
                "test tooltip",
                13,
                "test placeholder");
    }

}
