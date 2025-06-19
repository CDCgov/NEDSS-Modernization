package gov.cdc.nbs.patient.file.demographics.sexBirth;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class PatientFileSexBirthDemographicRequester {

  private final AuthenticatedMvcRequester authenticated;

  PatientFileSexBirthDemographicRequester(final AuthenticatedMvcRequester authenticated) {
    this.authenticated = authenticated;
  }

  ResultActions request(final PatientIdentifier patient) {
    return authenticated.request(get("/nbs/api/patients/{patient}/demographics/sex-birth", patient.id()));
  }
}
