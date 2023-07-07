package gov.cdc.nbs.questionbank.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import gov.cdc.nbs.questionbank.condition.controller.ConditionController;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class CreateConditionSteps {
    @Autowired
    private UserMother userMother;

    @Autowired
    private ConditionController controller;

    @Autowired
    private ConditionCodeRepository conditionCodeRepository;
    private ConditionCodeRequest requestCodeRequest;
    private ConditionCodeResponse responseCodeResponse;
    private Exception exception;

    @Given("No conditions exist")
    public void no_conditions_exist(){
        conditionCodeRepository.deleteAll();
    }

    @Given("I am an admin user")
    public void admin_user(){
        userMother.adminUser();
    }
    
    @When("I send a create {string} condition request")
    public void create_conditions_request(String condition){
        try{
            response = controller.createCondition((CreateConditionRequest.Text), request);
        } catch (Exception e){
            exception = e;
        }
    }


    @Then("The {string} condition is created")
    public void the_condition_is_created(){
        assertNotNull(response);
        ConditionCode condition =
                (ConditionCode) conditionCodeRepository.findById(response.conditionId()).orElseThrow();
        CreateConditionRequest.Text textRequest = (CreateConditionRequest.Text) request;
        assertEquals(condition.getId().longValue(), response.conditionId());
    }

    @Then("A condition creation exception is thrown")
    public void condition_creation_exception(){
        assertNotNull(exception);
        assertTrue(exception instanceof CreateQuestionException);
        //using the question exception file for this
    }
}
