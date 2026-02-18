package gov.cdc.nbs.patient.profile.investigation;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.ResultActions;

public class DeleteInvestigationSteps {

  private final String classicUrl;

  private final Active<PatientIdentifier> activePatient;

  private final Active<NBS6InvestigationRequest> activeContext;

  private final NBS6InvestigationDeleteRequester requester;

  private final Active<ResultActions> response;

  private final MockRestServiceServer server;

  DeleteInvestigationSteps(
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final Active<ResultActions> response,
      final Active<PatientIdentifier> activePatient,
      @Value("${nbs.wildfly.url}") final String classicUrl,
      final Active<NBS6InvestigationRequest> activeContext,
      final NBS6InvestigationDeleteRequester requester) {
    this.server = server;
    this.response = response;
    this.activePatient = activePatient;
    this.classicUrl = classicUrl;
    this.activeContext = activeContext;
    this.requester = requester;
  }

  @Given("the viewed investigation has no associated events")
  public void the_viewed_investigation_has_not_associated_events() {
    activeContext
        .maybeActive()
        .ifPresent(
            request ->
                server
                    .expect(
                        requestToUriTemplate(
                            "{base}/nbs/{location}", classicUrl, request.location()))
                    .andExpect(method(HttpMethod.POST))
                    .andExpect(
                        content()
                            .formDataContains(
                                Map.of(
                                    "ContextAction",
                                    request.contextAction(),
                                    "method",
                                    "deleteSubmit")))
                    .andRespond(withSuccess()));
  }

  @Given("the viewed investigation has an associated Lab Report")
  public void the_viewed_investigation_has_an_associated_lab_report() {
    activeContext
        .maybeActive()
        .ifPresent(
            request ->
                server
                    .expect(requestTo(classicUrl + "/nbs/" + request.location()))
                    .andExpect(method(HttpMethod.POST))
                    .andExpect(
                        content()
                            .formDataContains(
                                Map.of(
                                    "ContextAction",
                                    request.contextAction(),
                                    "method",
                                    "deleteSubmit")))
                    .andRespond(
                        withStatus(HttpStatus.FOUND).header("Location", "/nbs6/investigation")));
  }

  @When("I delete the investigation from NBS6")
  public void i_delete_the_investigation_from_NBS6() {
    activePatient
        .maybeActive()
        .flatMap(
            patient ->
                activeContext
                    .maybeActive()
                    .map(contextAction -> requester.delete(patient, contextAction)))
        .ifPresent(response::active);
  }

  @Then("the investigation delete is submitted to NBS6")
  public void the_investigation_delete_is_submitted_to_classic_nbs() {
    server.verify();
  }

  @Then("I am returned to the investigation in NBS6")
  public void i_am_returned_to_the_investigation_in_nbs6() throws Exception {
    this.response
        .active()
        .andExpect(status().isFound())
        .andExpect(header().string("Location", startsWith("/nbs6/investigation")));
  }
}
