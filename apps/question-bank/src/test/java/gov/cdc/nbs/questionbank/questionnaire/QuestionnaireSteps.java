package gov.cdc.nbs.questionbank.questionnaire;

import static org.junit.Assert.assertNotNull;
import org.springframework.beans.factory.annotation.Autowired;
import gov.cdc.nbs.questionbank.entities.Questionnaire;
import gov.cdc.nbs.questionbank.support.QuestionnaireMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class QuestionnaireSteps {

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private QuestionnaireResolver resolver;

    private Questionnaire results;

    @Given("a questionnaire exists")
    public void a_questionnaire_exists() {
        questionnaireRepository.save(QuestionnaireMother.questionnaire(-100L, "initial interview", null));
    }

    @When("i search for a questionnaire")
    public void i_search_for_a_questionnaire() {
        results = resolver.findQuestionnaire(new QuestionnaireContext(-100L, "initial interview"));

    }

    @Then("the questionnaire is returned")
    public void the_questionnaire_is_returned() {
        assertNotNull(results);

    }

}
