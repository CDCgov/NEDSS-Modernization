package gov.cdc.nbs.patient.profile.gender;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

import java.time.Instant;
import java.time.ZoneId;

public class GenderDemographicPatientCommandMapper {

  public static PatientCommand.UpdateGender asUpdateGender(
      final long patient,
      final RequestContext context,
      final GenderDemographic demographic
  ) {

    Instant asOf = demographic.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant();

    return new PatientCommand.UpdateGender(
        patient,
        asOf,
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
