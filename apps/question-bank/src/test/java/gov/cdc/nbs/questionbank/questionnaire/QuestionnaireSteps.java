package gov.cdc.nbs.questionbank.questionnaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;
import gov.cdc.nbs.questionbank.support.QuestionnaireMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;



@Transactional
public class QuestionnaireSteps {

    @Autowired
    private QuestionnaireResolver resolver;

    @Autowired
    private QuestionnaireMother questionnaireMother;

    private QuestionnaireEntity search;
    private Questionnaire results;

    @Given("a {string} questionnaire exists")
    public void a_questionnaire_exists(String condition) {
        questionnaireMother.clean();
        search = questionnaireMother.questionnaire(Collections.singletonList("123"));
    }

    @When("I search for a questionnaire")
    public void i_search_for_a_questionnaire() {
        String conditionCd = search.getConditionCodes().get(0);
        results = resolver.findQuestionnaire(new QuestionnaireContext(conditionCd));
    }

    @Then("the questionnaire is returned")
    public void the_questionnaire_is_returned() {
        assertNotNull(results);
        assertEquals(search.getId().longValue(), results.id());
        assertEquals(1, results.conditions().size());
        assertEquals("123", results.conditions().get(0));
    }

}
