package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.ViewRuleResponse;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest.SourceValues;
import gov.cdc.nbs.questionbank.page.PageMother;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.StaticContentRequests;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class PageRuleSteps {

    @Autowired
    private PageRuleRequest requester;

    @Autowired
    private Active<PageIdentifier> page;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private ExceptionHolder exceptionHolder;

    private final Active<ResultActions> response = new Active<>();

    private final Active<CreateRuleRequest> request = new Active<>();

    private final Active<ResultActions> detailResponse = new Active<>();

    @BeforeEach
    public void reset() {
        request.active(new CreateRuleRequest(
                null,
                null,
                null,
                null,
                new SourceValues(null, null),
                false,
                null,
                null,
                null,
                null));
    }

    @Given("the business rule has {string} of {string}")
    public void the_business_rule_has(final String property, final String value) {
        switch (property.toLowerCase()) {
            case "source text" -> this.request
                    .active(PageRuleCreateRequestHelper.withSourceText(this.request.active(), value));
            case "source identifier" -> this.request
                    .active(PageRuleCreateRequestHelper.withSourceIdentifier(this.request.active(), value));
            case "rule description" -> this.request
                    .active(PageRuleCreateRequestHelper.withRuleDescription(this.request.active(), value));
            case "function" -> this.request
                    .active(PageRuleCreateRequestHelper.withFunction(this.request.active(), value));
            case "comparator" -> this.request
                    .active(PageRuleCreateRequestHelper.withComparator(this.request.active(), value));
            case "any source value" -> this.request.active(PageRuleCreateRequestHelper
                    .withAnySourceValue(this.request.active(), value.toLowerCase().equals("true") ? true : false));
            case "target type" -> this.request
                    .active(PageRuleCreateRequestHelper.withTargetType(this.request.active(), value));
        }
    }

    @Given("the business rule has {string} of:")
    public void the_business_rule_has(final String property, List<String> values) {
        switch (property.toLowerCase()) {
            case "target values list" -> this.request
                    .active(PageRuleCreateRequestHelper.withTargetValues(this.request.active(), values));
            case "target identifiers list" -> this.request
                    .active(PageRuleCreateRequestHelper.withTargetIdentifiers(this.request.active(), values));
            case "source value ids" -> this.request
                    .active(PageRuleCreateRequestHelper.withSourceValueId(this.request.active(), values));
            case "source value texts" -> this.request
                    .active(PageRuleCreateRequestHelper.withSourceValueText(this.request.active(), values));
        }
    }

    @When("I send the page rule create request")
    public void i_send_the_page_rule_create_request() {
        this.response.active(
                this.requester.createBusinessRule(
                        this.page.active().id(),
                        this.request.active()));
    }

    @Then("I retrieve the information of the page rule")
    public void i_retrieve_the_information_of_the_page_rule() throws Exception {
        CreateRuleResponse test = objectMapper.readValue(
            this.response.active().andReturn().getResponse().getContentAsString(), 
            CreateRuleResponse.class);

        this.detailResponse.active(
            this.requester.request(
                this.page.active().id(), 
                test.ruleId())
        );
    }

    @Then("the business rule should have {string} of {string}")
    public void the_business_rule_should_have_of() {
        
    }

    @Then("A no credentials found exception is thrown")
    public void a_no_credentials_found_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AuthenticationCredentialsNotFoundException);
    }


    @When("I make a request to update a rule to a page")
    public void i_make_a_request_to_update_a_rule_to_a_page() {

    }

    @Then("an access denied exception is thrown")
    public void a_access_denied_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AccessDeniedException);
    }
}
