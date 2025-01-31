package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class NameDemographicPatientCommandMapper {

  public static PatientCommand.AddName asAddName(
      final long patient,
      final RequestContext context,
      final NameDemographic demographic
  ) {

    return new PatientCommand.AddName(
        patient,
        demographic.asOf(),
        demographic.prefix(),
        demographic.first(),
        demographic.middle(),
        demographic.secondMiddle(),
        demographic.last(),
        demographic.secondLast(),
        demographic.suffix(),
        demographic.degree(),
        demographic.type(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private NameDemographicPatientCommandMapper() {
    // static
  }

}
