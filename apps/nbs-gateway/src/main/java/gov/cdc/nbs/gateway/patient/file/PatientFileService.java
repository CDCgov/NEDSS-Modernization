package gov.cdc.nbs.gateway.patient.file;

import gov.cdc.nbs.gateway.Service;
import java.net.URI;

public class PatientFileService extends Service {
  public PatientFileService(final URI uri) {
    super(uri);
  }

  public String direct() {
    return "/nbs/redirect/patient/file";
  }

  public String events() {
    return "/nbs/redirect/patient/file/events/return";
  }

  public String summary() {
    return "/nbs/redirect/patient/file/summary/return";
  }
}
