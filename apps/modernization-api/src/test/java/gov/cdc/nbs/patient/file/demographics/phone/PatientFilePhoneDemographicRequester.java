package gov.cdc.nbs.patient.file.demographics.phone;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.Authenticated;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class PatientFilePhoneDemographicRequester {

  private final MockMvc mvc;
  private final Authenticated authenticated;

  PatientFilePhoneDemographicRequester(
      final MockMvc mvc,
      final Authenticated authenticated
  ) {
    this.mvc = mvc;
    this.authenticated = authenticated;
  }

  ResultActions request(final PatientIdentifier patient) {
    try {
      return mvc.perform(
          this.authenticated.withUser(
              get("/nbs/api/patients/{patient}/demographics/phones", patient.id())

          )
      );
    } catch (Exception exception) {
      throw new IllegalStateException(
          "An unexpected error occurred when viewing the patient phone demographics.",
          exception
      );
    }
  }
}
