package gov.cdc.nbs.patient.file.demographics.identification;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Component
class PatientFileIdentificationDemographicRequester {

  private final AuthenticatedMvcRequester authenticated;

  PatientFileIdentificationDemographicRequester(final AuthenticatedMvcRequester authenticated) {
    this.authenticated = authenticated;
  }

  ResultActions request(final PatientIdentifier patient) {
    return authenticated.request(get("/nbs/api/patients/{patient}/demographics/identifications", patient.id()));
  }
}

