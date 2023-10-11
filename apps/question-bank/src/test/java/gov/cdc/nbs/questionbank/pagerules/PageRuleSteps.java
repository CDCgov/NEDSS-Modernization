package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.RuleRequestMother;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;

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

    Long ruleId;

    @Given("I make a request to create page rule without rule request")
    public void i_create_page_rule_without_rule_request() {
        try {
            ruleRequest = null; 

        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A page rule is not created")
    public void a_page_rule_is_not_created() {
        assertNull(ruleResponse);
    }
    @Given("I make a request to add page rule request")
    public void i_create_page_rule_request() {
        try {
            ruleRequest = ruleRequest();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }
    @When("I make a request to page rule add")
    public void i_make_a_request_to_create_a_page_rule() {

        try {
            ruleResponse = pageRuleController.createBusinessRule(ruleRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A page rule is created")
    public void a_business_rule_is_created() {
        assertNotNull(ruleResponse);
    }
    @Given("I make a request to update page rule request")
    public void i_update_page_rule() {
        try {
            Long ruleId = ruleResponse.ruleId();
            ruleRequest = ruleRequest();

        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @When("I make a request to page rule update")
    public void iMakeARequestToPageRuleUpdate() {
        try{
            ruleResponse = pageRuleController.updatePageRule(ruleId,ruleRequest);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A page rule is updated")
    public void aPageRuleIsUpdated() {
        assertNotNull(ruleResponse);
    }
    @Given("I make a request to update page rule without rule request")
    public void i_update_page_rule_with_non_exist_rule_id() {
        try {
            Long ruleId = 99999L;
            ruleRequest = RuleRequestMother.ruleRequest();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A page rule is not updated")
    public void aPageRuleIsNotUpdated() {
        assertNull(ruleResponse);
    }
    @Given("I make a request to delete page rule request")
    public void i_delete_page_rule() {
        try {
            ruleId = ruleResponse.ruleId();
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }


    @When("I make a request to page rule delete")
    public void iMakeARequestToPageRuleDelete() {
        try{
            ruleResponse = pageRuleController.deletePageRule(ruleId);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A page rule is deleted")
    public void aPageRuleIsDeleted() {
        assertNotNull(ruleResponse);
    }
    @Given("I make a request to delete page rule without rule request")
    public void i_delete_page_rule_with_non_exist_rule_id() {
        try {
            ruleId = 99999L;
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Then("A page rule is not deleted")
    public void aPageRuleIsNotDeleted() {
        assertNull(ruleResponse);
    }
    @Given("I make a request to view page rule")
    public void i_view_page_rule_response() {
        try {
            Long ruleId = 99L;
            ViewRuleResponse ruleResponse = pageRuleController.viewRuleResponse(ruleId);
            assertNotNull(ruleResponse);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I make a request to view page rule with non exist rule id")
    public void i_view_page_rule_response_with_non_exist_rule_id() {
        try {
            Long ruleId = 99999L;
            ViewRuleResponse ruleResponse = pageRuleController.viewRuleResponse(ruleId);
            assertNull(ruleResponse);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I make a request to search for a page rule that exists")
    public void i_search_for_a_page_rule_that_exists() {
        try {
            int page = 0;
            int size =1;
            String sort ="id";
            Pageable pageRequest = PageRequest.of(page,size, Sort.by(sort));
            Page<ViewRuleResponse> response= pageRuleController.getAllPageRule(pageRequest);
            assertNotNull(response);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    @Given("I make a request to search for a page rule that does not exists")
    public void i_search_for_a_page_rule_that_does_not_exists() {
        try {
            int page = 12000;
            int size =1;
            String sort ="id";
            Pageable pageRequest = PageRequest.of(page,size, Sort.by(sort));
            Page<ViewRuleResponse> response= pageRuleController.getAllPageRule(pageRequest);
            assertNull(response);
        } catch (AccessDeniedException e) {
            exceptionHolder.setException(e);
        } catch (AuthenticationCredentialsNotFoundException e) {
            exceptionHolder.setException(e);
        }
    }

    private static CreateRuleRequest ruleRequest() {
        List<String> targetValuesList = new ArrayList<>();
        targetValuesList.add("Admission Date");
        List<String> targetIdentifiers = new ArrayList<>();
        targetIdentifiers.add("404400");

        List<CreateRuleRequest.SourceValues> sourceValues = new ArrayList<>();
        List<String> sourceIds = new ArrayList<>();
        sourceIds.add("Test Id");

        List<String> sourceValueText = new ArrayList<>();
        sourceValueText.add("Dengue virus");
        CreateRuleRequest.SourceValues sourceValue = new CreateRuleRequest.SourceValues(sourceIds, sourceValueText);
        sourceValues.add(sourceValue);
        return new CreateRuleRequest(
                1000274L,
                "Enable",
                "Rule Desc",
                "testSource",
                "ARB001",
                sourceValues,
                true,
                "=",
                "QUESTION",
                targetValuesList,
                targetIdentifiers);
    }
}
