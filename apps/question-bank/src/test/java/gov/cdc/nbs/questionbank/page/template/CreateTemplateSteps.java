package gov.cdc.nbs.questionbank.page.template;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.ResultActions;

public class CreateTemplateSteps {

  @Value("${nbs.wildfly.url:http://wildfly:7001}")
  String classicUrl;

  @Autowired Active<PageIdentifier> page;

  @Autowired CreateTemplateRequester requester;

  @Autowired
  @Qualifier("classicRestService") MockRestServiceServer server;

  private final Active<ResultActions> response = new Active<>();

  private final Active<CreateTemplateRequest> request = new Active<>();

  @Before("@create-template")
  public void clean() {
    request.active(new CreateTemplateRequest());
    server.reset();
  }

  @Given("I want to create a template named {string}")
  public void i_want_to_create_a_template_named(final String name) {
    this.request.active(existing -> existing.withName(name));
  }

  @Given("I want to create a template described as {string}")
  public void i_want_to_create_a_template_described_as(final String description) {
    this.request.active(existing -> existing.withDescription(description));
  }

  @When("I create a template from the page")
  public void i_create_a_template_from_the_page_named() {

    CreateTemplateRequest activeRequest = this.request.active();

    this.page
        .maybeActive()
        .map(found -> classic(found.id(), activeRequest))
        .ifPresent(this.response::active);
  }

  private ResultActions classic(final long page, final CreateTemplateRequest request) {

    server
        .expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=list&initLoad=true"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server
        .expect(
            requestTo(classicUrl + "/nbs/PreviewPage.do?method=viewPageLoad&waTemplateUid=" + page))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server
        .expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=saveAsTemplateLoad"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    Map<String, String> form = new HashMap<>();

    if (request.name() != null) {
      form.put("selection.templateNm", request.name());
    }

    if (request.description() != null) {
      form.put("selection.descTxt", request.description());
    }

    server
        .expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=saveAsTemplate"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().formDataContains(form))
        .andRespond(withStatus(HttpStatus.FOUND));

    return this.requester.request(page, request);
  }

  @Then("the page template is created using NBS Classic")
  public void the_page_template_is_created_using_nbs_classic() throws Exception {
    server.verify();

    this.response.active().andDo(print()).andExpect(status().isAccepted());
  }

  @Then("the template cannot be changed because {string}")
  public void the_template_cannot_be_created_because(final String error) throws Exception {
    this.response
        .active()
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$.message", equalToIgnoringCase(error)));
  }
}
