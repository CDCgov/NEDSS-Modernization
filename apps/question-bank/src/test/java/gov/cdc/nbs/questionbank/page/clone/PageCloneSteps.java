package gov.cdc.nbs.questionbank.page.clone;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.ResultActions;

public class PageCloneSteps {

  private final String classicUrl;
  private final MockRestServiceServer server;
  private final Active<PageIdentifier> page;

  private final PageCloneRequester requester;
  private final Active<ResultActions> response;

  PageCloneSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final Active<PageIdentifier> page,
      final PageCloneRequester requester) {
    this.classicUrl = classicUrl;
    this.page = page;
    this.server = server;
    this.requester = requester;
    this.response = new Active<>();
  }

  @Before("@page-clone")
  public void clean() {
    server.reset();
    response.reset();
  }

  @When("the page is cloned from Page Preview")
  public void the_page_is_cloned_from_Page_Preview() {
    this.page.maybeActive().map(found -> clone(found.id())).ifPresent(this.response::active);
  }

  private ResultActions clone(final long page) {
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
        .expect(
            requestTo(classicUrl + "/nbs/ManagePage.do?method=viewPageDetailsLoad&initLoad=true"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    return requester.request(page);
  }

  @Then("I am redirected to Classic NBS to clone the page")
  public void i_am_redirected_to_classic_nbs_to_view_the_simplified_page() throws Exception {
    server.verify();

    String returning =
        this.page.maybeActive().map(PageIdentifier::id).map(String::valueOf).orElse("NOPE");

    response
        .active()
        .andExpect(status().isTemporaryRedirect())
        .andExpect(header().string(HttpHeaders.LOCATION, "/nbs/ManagePage.do?method=clonePageLoad"))
        .andExpect(cookie().value("Return-Page", returning));
  }
}
