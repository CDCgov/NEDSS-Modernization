package gov.cdc.nbs.questionbank.page;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.request.PagePublishRequest;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PagePublisherSteps {

  private final String classicUrl;

  private final MockRestServiceServer server;

  private final EntityManager entityManager;

  private final PageRequest pageRequest;

  private final Active<ActiveUser> user;
  private final Active<ResultActions> activeResponse = new Active<>();
  private final Active<PagePublishRequest> request = new Active<>();
  private final Active<PageIdentifier> page;

  PagePublisherSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final EntityManager entityManager,
      final PageRequest pageRequest,
      final Active<PageIdentifier> page,
      final Active<ActiveUser> user) {
    this.classicUrl = classicUrl;
    this.server = server;
    this.entityManager = entityManager;
    this.pageRequest = pageRequest;
    this.page = page;
    this.user = user;
  }

  @Given("the publish page request has version notes of {string}")
  public void the_publish_page_request_has(String value) {
    request.active(new PagePublishRequest(value));
  }

  @When("I send the publish page request")
  public void i_send_the_publish_page_request() {
    activeResponse.active(classic(page.active().id(), request.active()));
  }

  private ResultActions classic(final long page, final PagePublishRequest request) {

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
        .expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=publishPopUpLoad"))
        .andExpect(method(HttpMethod.POST))
        .andRespond(withSuccess());

    Map<String, String> form = new HashMap<>();

    if (request.versionNotes() != null) {
      form.put("selection.versionNote", request.versionNotes());
    }

    server
        .expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=publishPage"))
        .andExpect(method(HttpMethod.POST))
        .andExpect(content().formDataContains(form))
        .andRespond(
            req -> {
              WaTemplate tempPage = entityManager.find(WaTemplate.class, page);
              tempPage.publish(new PageCommand.Publish(user.active().id(), Instant.now()));
              // need to flush to mock behavior done by classic
              entityManager.flush();
              DefaultResponseCreator response = withStatus(HttpStatus.FOUND);
              return response.createResponse(req);
            });

    return this.pageRequest.publishPage(page, request);
  }

  @Then("the response of request is success")
  public void the_response_of_request_is_success() throws Exception {
    this.activeResponse.active().andExpect(status().isOk());
  }
}
