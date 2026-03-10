package gov.cdc.nbs.patient.file.demographics.general;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientFileGeneralInformationDemographicRequester {

  private final AuthenticatedMvcRequester authenticated;

  PatientFileGeneralInformationDemographicRequester(final AuthenticatedMvcRequester authenticated) {
    this.authenticated = authenticated;
  }

  ResultActions request(final PatientIdentifier patient) {
    return authenticated.request(
        get("/nbs/api/patients/{patient}/demographics/general", patient.id()));
  }
}
