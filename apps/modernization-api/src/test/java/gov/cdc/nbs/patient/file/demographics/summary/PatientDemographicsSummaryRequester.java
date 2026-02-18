package gov.cdc.nbs.patient.file.demographics.summary;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientDemographicsSummaryRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientDemographicsSummaryRequester(final MockMvc mvc, final Authenticated authenticated) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final long patient) {
    try {
      return mvc.perform(
          this.authenticated.withUser(get("/nbs/api/patients/{patient}/demographics", patient)));

    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when viewing the summary of patient demographics.",
          exception);
    }
  }
}
