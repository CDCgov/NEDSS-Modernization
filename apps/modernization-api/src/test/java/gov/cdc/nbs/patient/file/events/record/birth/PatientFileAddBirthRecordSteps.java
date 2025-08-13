package gov.cdc.nbs.patient.file.events.record.birth;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class PatientFileAddBirthRecordSteps {

  private final String classicUrl;
  private final Active<PatientIdentifier> activePatient;
  private final AuthenticatedMvcRequester authenticated;
  private final Active<ResultActions> activeResponse;
  private final MockRestServiceServer server;

  PatientFileAddBirthRecordSteps(
      @Value("${nbs.wildfly.url:http://wildfly:7001}") final String classicUrl,
      final Active<PatientIdentifier> activePatient,
      final AuthenticatedMvcRequester authenticated,
      final Active<ResultActions> activeResponse,
      @Qualifier("classicRestService") final MockRestServiceServer server
  ) {
    this.classicUrl = classicUrl;
    this.activePatient = activePatient;
    this.authenticated = authenticated;
    this.activeResponse = activeResponse;
    this.server = server;
  }

  @Before("@patient-file-birth-record-redirect")
  public void reset() {
    server.reset();
  }

  @When("a birth record is added from the Patient file")
  public void added() {
    long patient = activePatient.active().id();

    server.expect(
            requestTo(classicUrl + "/nbs/HomePage.do?method=patientSearchSubmit"))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());

    server.expect(requestTo(classicUrl + "/nbs/PatientSearchResults1.do?ContextAction=ViewFile&uid=" + patient))
        .andExpect(method(HttpMethod.GET))
        .andRespond(withSuccess());


    activeResponse.active(
        authenticated.request(
            MockMvcRequestBuilders.get("/nbs/api/patients/{patient}/records/birth/redirect", patient)));
  }

  @Then("NBS is prepared to view a birth record")
  @Then("NBS is prepared to add a birth record")
  public void sessionIsPrepared() {
    server.verify();
  }

  @Then("I am redirected to Classic NBS to add a birth record")
  public void addRedirected() throws Exception {
    long patient = activePatient.active().id();

    String expected = "/nbs/PageAction.do?method=createGenericLoad&businessObjectType=BIR&Action=DSFilePath";

    activeResponse.active()
        .andExpect(MockMvcResultMatchers.header().string("Location", expected))
        .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.cookie().value("Return-Patient", String.valueOf(patient)));
  }
}
