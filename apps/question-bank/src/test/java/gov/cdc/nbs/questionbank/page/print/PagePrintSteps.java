package gov.cdc.nbs.questionbank.page.print;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

public class PagePrintSteps {

  private final String classicUrl;
  private final MockRestServiceServer server;
  private final Active<PageIdentifier> page;

  private final PagePrintRequester requester;
  private final Active<ResultActions> response;

  PagePrintSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final Active<PageIdentifier> page,
      final PagePrintRequester requester) {
    this.classicUrl = classicUrl;
    this.page = page;
    this.server = server;
    this.requester = requester;
    this.response = new Active<>();
  }

  @Before("@page-print")
  public void clean() {
    server.reset();
    response.reset();
  }

  @When("the page is printed from Page Preview")
  public void the_page_is_printed_from_Page_Preview() {

    server
        .expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=list&initLoad=true"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    this.page
        .maybeActive()
        .map(PageIdentifier::id)
        .map(this.requester::request)
        .ifPresent(this.response::active);
  }

  @Then("I am redirected to Classic NBS to view the simplified Page")
  public void i_am_redirected_to_classic_nbs_to_view_the_simplified_page() throws Exception {
    server.verify();

    long expected = this.page.maybeActive().map(PageIdentifier::id).orElse(0L);

    response
        .active()
        .andExpect(status().isTemporaryRedirect())
        .andExpect(
            header()
                .string(
                    HttpHeaders.LOCATION,
                    "/nbs/PreviewPage.do?method=viewPageLoad&mode=print&waTemplateUid=%d"
                        .formatted(expected)));
  }
}
