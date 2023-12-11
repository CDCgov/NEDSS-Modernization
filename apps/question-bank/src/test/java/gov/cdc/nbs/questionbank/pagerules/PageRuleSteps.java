package gov.cdc.nbs.questionbank.pagerules;

import gov.cdc.nbs.questionbank.model.CreateRuleRequest;
import gov.cdc.nbs.questionbank.model.CreateRuleRequest.SourceValues;
import gov.cdc.nbs.questionbank.pagerules.response.CreateRuleResponse;
import gov.cdc.nbs.questionbank.support.ExceptionHolder;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Transactional
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

    @Before("@create_business_rule")
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
    public void the_business_rule_has(final String property, List<List<String>> values) {
        List<String> actValues = new ArrayList<>();
        for(List<String> val : values) {
            actValues.add(val.get(0));
        }
        switch (property.toLowerCase()) {
            case "target values list" -> this.request
                    .active(PageRuleCreateRequestHelper.withTargetValues(this.request.active(), actValues));
            case "target identifiers list" -> this.request
                    .active(PageRuleCreateRequestHelper.withTargetIdentifiers(this.request.active(), actValues));
            case "source value ids" -> this.request
                    .active(PageRuleCreateRequestHelper.withSourceValueId(this.request.active(), actValues));
            case "source value texts" -> this.request
                    .active(PageRuleCreateRequestHelper.withSourceValueText(this.request.active(), actValues));
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
                        test.ruleId()));
    }

    @Then("the business rule should have {string} of {string}")
    public void the_business_rule_should_have_of(final String property, final String value) throws Exception {
        switch (property.toLowerCase()) {
            case "source identifier" -> this.detailResponse.active()
                    .andExpect(jsonPath("$.sourceIdentifier", is(value)));
            case "rule description" -> this.detailResponse.active()
                    .andExpect(jsonPath("$.ruleDescription", is(value)));
            case "function" -> this.detailResponse.active()
                    .andExpect(jsonPath("$.ruleFunction", is(value)));
            case "comparator" -> this.detailResponse.active()
                    .andExpect(jsonPath("$.comparator", is(value)));
            case "target type" -> this.detailResponse.active()
                    .andExpect(jsonPath("$.targetType", is(value)));
        }
    }

    @Then("the business rule should have {string} of:")
    public void the_business_rule_should_have_of(final String property, final List<String> values) throws Exception {
        switch (property.toLowerCase()) {
            case "target identifiers list" -> this.detailResponse.active()
                    .andExpect(jsonPath("$.targetValueIdentifier", is(values)));
            case "source values" -> this.detailResponse.active()
                    .andExpect(jsonPath("$.sourceValue", is(values)));
        }
    }

    @Then("A no credentials found exception is thrown")
    public void a_no_credentials_found_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AuthenticationCredentialsNotFoundException);
    }

    @Then("an access denied exception is thrown")
    public void a_access_denied_exception_is_thrown() {
        assertNotNull(exceptionHolder.getException());
        assertTrue(exceptionHolder.getException() instanceof AccessDeniedException);
    }
}
