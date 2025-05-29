package gov.cdc.nbs.patient.profile.investigation;

import gov.cdc.nbs.patient.TestPatients;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientProfileAddInvestigationSteps {

  @Value("${nbs.wildfly.url:http://wildfly:7001}")
  String classicUrl;

  @Autowired
  TestPatients patients;

  @Autowired
  Authenticated authenticated;

  @Autowired
  MockMvc mvc;

  @Autowired
  Active<MockHttpServletResponse> activeResponse;

  @Autowired
  @Qualifier("classicRestService")
  MockRestServiceServer server;

  @Before
  public void clearServer() {
    server.reset();
  }

  @When("an investigation is added from a Patient Profile")
  public void an_investigation_is_added_from_a_patient_profile() throws Exception {
    long patient = patients.one();

    server.expect(
        requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    activeResponse.active(
        mvc.perform(
            authenticated.withUser(MockMvcRequestBuilders.get("/nbs/api/profile/{patient}/investigation", patient)))
            .andReturn()
            .getResponse());
  }

  @When("the user clicks the add investigation button")
  public void the_user_clicks_the_add_investigation_button() throws Exception {
    long patient = patients.one();

    activeResponse.active(
        mvc.perform(
            authenticated.withUser(MockMvcRequestBuilders.get("/nbs/api/profile/{patient}/investigation", patient)))
            .andReturn()
            .getResponse());
  }

  @Then("the classic profile is prepared to add an investigation")
  public void the_classic_profile_is_prepared_to_add_an_investigation() {
    server.verify();
  }

  @Then("I am redirected to Classic NBS to add an Investigation")
  public void i_am_redirected_to_classic_nbs_to_add_an_investigations() {
    long patient = patients.one();

    String expected = "/nbs/LoadSelectCondition1.do?ContextAction=AddInvestigation";

    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getRedirectedUrl()).contains(expected);

    assertThat(response.getCookies())
        .satisfiesOnlyOnce(cookie -> {
          assertThat(cookie.getName()).isEqualTo("Return-Patient");
          assertThat(cookie.getValue()).isEqualTo(String.valueOf(patient));
        });
  }

  @Then("I am not allowed to add a Classic NBS Investigation")
  public void i_am_not_allowed_to_add_a_classic_nbs_investigation() {
    MockHttpServletResponse response = activeResponse.active();

    assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
  }
}
//
