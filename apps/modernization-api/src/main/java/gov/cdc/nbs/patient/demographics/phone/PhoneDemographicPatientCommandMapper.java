package gov.cdc.nbs.patient.demographics.phone;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class PhoneDemographicPatientCommandMapper {

  public static PatientCommand.AddPhone asAddPhone(
      final long patient,
      final RequestContext context,
      final PhoneDemographic input
  ) {
    return new PatientCommand.AddPhone(
        patient,
        input.type(),
        input.use(),
        input.asOf(),
        input.countryCode(),
        input.phoneNumber(),
        input.extension(),
        input.email(),
        input.url(),
        input.comment(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private PhoneDemographicPatientCommandMapper() {
    // static
  }

}
