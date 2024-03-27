package gov.cdc.nbs.questionbank.condition.read;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import gov.cdc.nbs.questionbank.condition.ConditionController;
import gov.cdc.nbs.questionbank.condition.model.Condition;
import gov.cdc.nbs.questionbank.condition.repository.ConditionCodeRepository;
import gov.cdc.nbs.questionbank.condition.util.ConditionHolder;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ReadConditionSteps {
    @Autowired
    ExceptionHolder exceptionHolder;

    @Autowired
    ConditionController conditionController;

    @Autowired
    private ConditionCodeRepository conditionRepository;

    @Autowired
    private ConditionHolder conditionHolder;


    @When("I request all conditions")
    public void i_request_all_conditions() {
        try {
            conditionHolder.setAllConditionsResponse(conditionController.findAllConditions());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I request to retrieve a page of conditions")
    public void i_request_to_retrieve_a_page_conditions() {
        try {
            Page<Condition> result =
                    conditionController.findConditions(PageRequest.ofSize(20));
            conditionHolder.setReadConditionResponse(result);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("Conditions successfully return")
    public void conditions_successfully_returned() {
        Page<Condition> result = conditionHolder.getReadConditionResponse();
        assertNotNull(result);
        assertTrue(result.getSize() > 0);
    }

    @Then("all conditions are returned")
    public void all_conditions_returned() {
        long totalAvailable = conditionRepository.count();
        assertEquals(totalAvailable, conditionHolder.getAllConditionsResponse().size());
    }
}
