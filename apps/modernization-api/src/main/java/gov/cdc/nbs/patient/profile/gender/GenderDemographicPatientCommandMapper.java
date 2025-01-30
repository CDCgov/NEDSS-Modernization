package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class GenderDemographicPatientCommandMapper {

  public static PatientCommand.UpdateGender asUpdateGender(
      final long patient,
      final RequestContext context,
      final GenderDemographic demographic
  ) {

    return new PatientCommand.UpdateGender(
        patient,
        demographic.asOf(),
        demographic.current(),
        demographic.unknownReason(),
        demographic.transgenderInformation(),
        demographic.additionalGender(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private GenderDemographicPatientCommandMapper() {
    // static
  }
}
