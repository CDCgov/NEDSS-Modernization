package gov.cdc.nbs.patient.profile.names;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

import java.time.Instant;
import java.time.ZoneId;

public class NameDemographicPatientCommandMapper {

  public static PatientCommand.AddName asAddName(
      final long patient,
      final RequestContext context,
      final NameDemographic demographic
  ) {

    Instant asOf = demographic.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant();

    return new PatientCommand.AddName(
        patient,
        asOf,
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
