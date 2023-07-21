package gov.cdc.nbs.questionbank.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.condition.ConditionMother;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import gov.cdc.nbs.questionbank.condition.controller.ConditionController;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;
import gov.cdc.nbs.questionbank.condition.request.CreateConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.CreateConditionResponse;
import gov.cdc.nbs.questionbank.question.exception.CreateQuestionException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.access.AccessDeniedException;


public class CreateConditionSteps {
    @Autowired
    private ConditionController conditionController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    @Autowired
    private ConditionMother conditionMother;

    private CreateConditionRequest request;
    private CreateConditionResponse response;

    private long result;


    @Getter
    @Setter
    public class ConditionHolder {
        Page<CreateConditionResponse> condition;
        ConditionCode conditionCode;
    }

    @Given("ConditionCd already exists")
    public void a_conditioncd_already_exists(){
        try {
            ConditionCode val = conditionMother.createCondition();
            ConditionHolder conditionHolder = new ConditionHolder();
            conditionHolder.setCondition((Page<CreateConditionResponse>) val);
            result = 0L;
        } catch(AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch(AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("A condition name already exists")
    public void a_conditionnm_already_exists(){
        try {
            ConditionCode val = conditionMother.createCondition();
            ConditionHolder conditionHolder = new ConditionHolder();
            conditionHolder.setCondition((Page<CreateConditionResponse>) val);
            result = 0L;
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e){
            exceptionHolder.setException(e);
        }
    }

    @Given("I am an admin and a condition does not exist")
    public void i_am_an_admin_and_a_condition_does_not_exist() {
        ConditionHolder conditionHolder = new ConditionHolder();
        conditionHolder.setCondition(null);
        result = 0L;
    }
    
    @When("Create condition")
    public void create_condition() {
        ConditionHolder conditionHolder = new ConditionHolder();
        try {
            if(conditionHolder.getCondition() != null) {
                request.setId(conditionHolder.getCondition().toString());
            } else{
                request.setId("1L");
            }

            ResponseEntity<CreateConditionResponse> val = conditionController.createCondition(request);
        } catch (AccessDeniedException e){
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("The Condition is created")
    public void the_condition_is_created(){
        ConditionHolder conditionHolder = new ConditionHolder();
        assertNull(conditionHolder.getCondition());
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatus());
    }

    @Then("A condition creation exception is thrown")
    public void a_condition_creation_exception_is_thrown(){
        assertNotNull(response);
        if(result == 0L || result == 1L) {
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
        }
    }
}
