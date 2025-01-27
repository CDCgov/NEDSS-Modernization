package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class BirthDemographicPatientCommandMapper {

  public static PatientCommand.UpdateBirth asUpdateBirth(
      final long patient,
      final RequestContext context,
      final BirthDemographic demographic
  ) {

    return new PatientCommand.UpdateBirth(
        patient,
        demographic.asOf(),
        demographic.bornOn(),
        demographic.sex(),
        demographic.multiple(),
        demographic.order(),
        demographic.city(),
        demographic.state(),
        demographic.county(),
        demographic.country(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private BirthDemographicPatientCommandMapper() {
    // static
  }
}
