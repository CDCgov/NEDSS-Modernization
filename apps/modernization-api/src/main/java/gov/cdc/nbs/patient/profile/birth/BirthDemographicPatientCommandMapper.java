package gov.cdc.nbs.patient.profile.birth;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

import java.time.Instant;
import java.time.ZoneId;

public class BirthDemographicPatientCommandMapper {

  public static PatientCommand.UpdateBirth asUpdateBirth(
      final long patient,
      final RequestContext context,
      final BirthDemographic demographic
  ) {

    Instant asOf = demographic.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant();

    return new PatientCommand.UpdateBirth(
        patient,
        asOf,
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
