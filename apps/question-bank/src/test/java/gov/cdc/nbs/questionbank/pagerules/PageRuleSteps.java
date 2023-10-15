package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.UserMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class PageRuleSteps {

    @Autowired
    private PageRuleController pageRuleController;

    @Autowired
    private ExceptionHolder exceptionHolder;

    CreateRuleRequest ruleRequest;
    CreateRuleResponse ruleResponse;

    @Autowired
    private UserMother userMother;
    Long ruleId;

    @Given("I am not logged in user")
    public void i_am_not_logged_in() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @When("I make a request to add a rule to a page")
    public void i_make_a_request_to_add_a_rule_to_a_page() {

        try {
            ruleResponse = pageRuleController.createBusinessRule(ruleRequest());
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }
    @Then("A rule is created")
    public void a_rule_is_created() {
        assertNotNull(ruleResponse);
    }

    @Then("A no credentials found exception is thrown")
    public void a_no_credentials_found_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AuthenticationCredentialsNotFoundException);
    }


    @When("I make a request to update a rule to a page")
    public void i_make_a_request_to_update_a_rule_to_a_page() {
        try{
            ruleId = 6405L;
            ruleResponse = pageRuleController.updatePageRule(ruleId,ruleRequest());
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A rule is updated")
    public void a_rule_is_updated() {
        assertNotNull(ruleResponse);
    }



    @When("I make a request to delete a rule to a page")
    public void i_make_a_request_to_delete_a_rule_to_a_page() {
        try{
            ruleResponse = pageRuleController.createBusinessRule(ruleRequest());
            ruleResponse = pageRuleController.deletePageRule(ruleResponse.ruleId());
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A rule is deleted")
    public void a_rule_is_deleted() {
        assertNotNull(ruleResponse);
    }


    private static CreateRuleRequest ruleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("string");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("string");

        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        List<String> sourceIds = new ArrayList<>();
        sourceIds.add("string");

        List<String> sourceValueText = new ArrayList<>();
        sourceValueText.add("string");
        CreateRuleRequest.SourceValues sourceValue = new CreateRuleRequest.SourceValues(sourceIds, sourceValueText);
        sourceValues.add(sourceValue);
        return new CreateRuleRequest(
                1000273L,
                "Enable",
                "string",
                "string",
                "string",
                sourceValues,
                true,
                "string",
                "string",
                targetValuesList,
                targetIdentifiers);
    }
}
