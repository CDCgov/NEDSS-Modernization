package gov.cdc.nbs.patient.file.events.record.birth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import gov.cdc.nbs.patient.identifier.PatientIdentifier;
import gov.cdc.nbs.testing.interaction.http.AuthenticatedMvcRequester;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
class PatientFileBirthRecordRequester {

  private final AuthenticatedMvcRequester authenticated;

  PatientFileBirthRecordRequester(final AuthenticatedMvcRequester authenticated) {
    this.authenticated = authenticated;
  }

  ResultActions request(final PatientIdentifier patient) {
    return authenticated.request(get("/nbs/api/patients/{patient}/records/birth", patient.id()));
  }
}
