package gov.cdc.nbs.questionbank.condition.read;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.condition.controller.ConditionController;
import gov.cdc.nbs.questionbank.condition.request.ReadConditionRequest;
import gov.cdc.nbs.questionbank.condition.response.ReadConditionResponse;
import gov.cdc.nbs.questionbank.condition.util.ConditionHolder;

public class ReadConditionSteps {
    @Autowired
    ExceptionHolder exceptionHolder;

    @Autowired
    ConditionController conditionController;

    @Autowired
    private ConditionHolder conditionHolder;


    private ReadConditionRequest readConditionRequest;



    @Given("I request to retrieve all conditions")
    public void i_request_to_retrieve_all_conditions() {
        try {
            Page<ReadConditionResponse.GetCondition> result = conditionController.findConditions(PageRequest.ofSize(20));
            conditionHolder.setReadConditionResponse(result);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I search for a condition that exists")
    public void i_search_for_a_condition_that_exists() {
        try {
            readConditionRequest = new ReadConditionRequest();
            Page<ReadConditionResponse.GetCondition> result = conditionController.searchConditions(readConditionRequest, PageRequest.ofSize(20));
            conditionHolder.setReadConditionResponse(result);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I search for a condition that does not exist")
    public void i_search_for_a_condition_that_does_not_exist() {
        try {
            readConditionRequest = new ReadConditionRequest();
            Page<ReadConditionResponse.GetCondition> result = conditionController.searchConditions(readConditionRequest, PageRequest.ofSize(20));
            conditionHolder.setReadConditionResponse(result);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("Conditions successfully return")
    public void conditions_successfully_returned() {
        Page<ReadConditionResponse.GetCondition> result = conditionHolder.getReadConditionResponse();
        assertNotNull(result);
        assertTrue(result.getSize() > 0);
    }

    @Then("A condition should be returned")
    public void a_condition_should_be_returned() {
        Page<ReadConditionResponse.GetCondition> result = conditionHolder.getReadConditionResponse();
        assertNotNull(result);
        assertTrue(result.getSize() > 0);
    }

    @Then("A condition should not be returned")
    public void a_condition_should_not_be_returned() {
        Page<ReadConditionResponse.GetCondition> result = conditionHolder.getReadConditionResponse();
        assertNotNull(result);
        assertEquals(0, result.getSize());
    }
}
