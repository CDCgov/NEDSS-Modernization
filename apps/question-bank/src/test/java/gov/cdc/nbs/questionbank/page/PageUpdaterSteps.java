package gov.cdc.nbs.questionbank.page;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.ResponseCreator;
import org.springframework.test.web.client.response.DefaultResponseCreator;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.request.PagePublishRequest;
import gov.cdc.nbs.questionbank.page.util.PageConstants;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

@Transactional
public class PageUpdaterSteps {

    @Value("${nbs.wildfly.url:http://wildfly:7001}")
    String classicUrl;

    @Autowired
    @Qualifier("classic")
    MockRestServiceServer server;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PageMother pageMother;

    @Autowired
    private PageRequest pageRequest;

    private Active<ResultActions> response = new Active<>();
    private Active<PagePublishRequest> request = new Active<>();
    private Active<PageIdentifier> page;

    public PageUpdaterSteps(final Active<PageIdentifier> page) {
        this.page = page;
    }

    @Given("the publish page request has version notes of {string}")
    public void the_publish_page_request_has(String value) {
        request.active(new PagePublishRequest(value));
    }

    @When("I send the publish page request")
    public void i_send_the_publish_page_request() throws Exception {
        response.active(classic(page.active().id(), request.active()));
    }

    private ResultActions classic(final long page, final PagePublishRequest request) {

        server.expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=list&initLoad=true"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        server.expect(requestTo(classicUrl + "/nbs/PreviewPage.do?method=viewPageLoad&waTemplateUid=" + page))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess());

        server.expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=publishPopUpLoad"))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess());

        Map<String, String> form = new HashMap<>();

        if (request.versionNotes() != null) {
            form.put("selection.versionNote", request.versionNotes());
        }
        
        server.expect(requestTo(classicUrl + "/nbs/ManagePage.do?method=publishPage"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(content().formDataContains(form))
                .andRespond(req -> {
                    WaTemplate tempPage = entityManager.find(WaTemplate.class, page);
                    tempPage.setTemplateType(PageConstants.PUBLISHED);
                    // need to flush to mock behavior done by classic
                    entityManager.flush();
                    DefaultResponseCreator response = withStatus(HttpStatus.FOUND);
                    return response.createResponse(req);
                });

        return this.pageRequest.publishPage(page, request);
    }

    @Then("the response of request is success")
    public void the_response_of_request_is_success() throws Exception {
        this.response.active().andExpect(jsonPath("$.message").value("success"));
    }

}
