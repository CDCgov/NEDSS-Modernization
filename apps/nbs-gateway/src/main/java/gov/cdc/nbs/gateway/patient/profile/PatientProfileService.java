package gov.cdc.nbs.gateway.patient.profile;

import gov.cdc.nbs.gateway.Service;

import java.net.URI;

public class PatientProfileService extends Service {
  public PatientProfileService(final URI uri) {
    super(uri);
  }

  public String events() {
    return "/nbs/redirect/patientProfile/events/return";
  }

  public String summary() {
    return "/nbs/redirect/patientProfile/summary/return";
  }
}
