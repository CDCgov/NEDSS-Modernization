package gov.cdc.nbs.questionbank.page.classic.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

public class ClassicCreatePageSteps {

  private final String classicUrl;
  private final MockRestServiceServer server;
  private final MockMvc mvc;
  private final Active<ResultActions> response;
  private final Authenticated authenticated;

  public ClassicCreatePageSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") String classicUrl,
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final MockMvc mvc,
      final Authenticated authenticated) {
    this.classicUrl = classicUrl;
    this.server = server;
    this.mvc = mvc;
    this.authenticated = authenticated;
    this.response = new Active<>();
  }

  @When("I am redirected to the classic create page")
  public void i_view_the_page_preview() throws Exception {

    server
        .expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=list&initLoad=true"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    response.active(mvc.perform(this.authenticated.withUser(get("/api/v1/pages/create"))));
  }

  @Then("the NBS Classic create page is loaded")
  public void the_nbs_classic_page_preview_is_loaded() throws Exception {
    server.verify();
    MockHttpServletResponse servletResponse =
        response.active().andExpect(status().is3xxRedirection()).andReturn().getResponse();
    String location = servletResponse.getHeader("Location");
    String expectedUrl = "/nbs/ManagePage.do?method=addPageLoad";
    assertEquals(expectedUrl, location);
  }
}
