package gov.cdc.nbs.patient.profile.redirect.incoming;

import io.cucumber.java.ParameterType;

public class PatientProfileIncomingSteps {

  @ParameterType(name = "patientProfileTab", value = "(?i)summary|events|demographics")
  public String patientProfileTab(final String value) {
    return value.toLowerCase();
  }
}
