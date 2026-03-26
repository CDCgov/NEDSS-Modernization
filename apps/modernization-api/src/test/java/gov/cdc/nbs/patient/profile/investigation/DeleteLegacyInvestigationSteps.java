package gov.cdc.nbs.patient.profile.investigation;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.ResultActions;

public class DeleteLegacyInvestigationSteps {

  private final String classicUrl;

  private final Active<PatientIdentifier> activePatient;

  private final Active<NBS6InvestigationRequest> activeContext;
  private final NBS6LegacyInvestigationDeleteRequester requester;
  private final Active<ResultActions> response;

  private final MockRestServiceServer server;

  DeleteLegacyInvestigationSteps(
      final Active<NBS6InvestigationRequest> activeContext,
      @Qualifier("classicRestService") final MockRestServiceServer server,
      final Active<PatientIdentifier> activePatient,
      @Value("${nbs.wildfly.url}") final String classicUrl,
      final NBS6LegacyInvestigationDeleteRequester requester,
      final Active<ResultActions> response) {
    this.activeContext = activeContext;
    this.server = server;
    this.activePatient = activePatient;
    this.classicUrl = classicUrl;
    this.requester = requester;
    this.response = response;
  }

  @Given("the viewed legacy investigation has no associated events")
  public void the_viewed_legacy_investigation_has_no_associated_events() {
    activeContext
        .maybeActive()
        .map(this::resolveRequestMatcher)
        .ifPresent(
            request ->
                server
                    .expect(request)
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(withSuccess()));
  }

  private RequestMatcher resolveRequestMatcher(final NBS6InvestigationRequest request) {
    return requestToUriTemplate(
        "{base}/nbs/{location}?ContextAction={contextAction}&delete=true",
        classicUrl,
        request.location(),
        request.contextAction());
  }

  @Given("the viewed legacy investigation has an associated Lab Report")
  public void the_viewed_legacy_investigation_an_associated_lab_report() {
    activeContext
        .maybeActive()
        .map(this::resolveRequestMatcher)
        .ifPresent(
            request ->
                server
                    .expect(request)
                    .andExpect(method(HttpMethod.POST))
                    .andRespond(
                        withStatus(HttpStatus.FOUND).header("Location", "/nbs6/investigation")));
  }

  @When("I delete the legacy investigation from NBS6")
  public void i_delete_the_legacy_investigation_from_NBS6() {
    activePatient
        .maybeActive()
        .flatMap(
            patient ->
                activeContext
                    .maybeActive()
                    .map(contextAction -> requester.delete(patient, contextAction)))
        .ifPresent(response::active);
  }
}
