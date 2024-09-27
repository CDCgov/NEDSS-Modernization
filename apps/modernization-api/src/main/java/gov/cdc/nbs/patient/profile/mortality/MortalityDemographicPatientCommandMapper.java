package gov.cdc.nbs.patient.profile.mortality;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import java.time.Instant;
import java.time.ZoneId;

public class MortalityDemographicPatientCommandMapper {

  public static PatientCommand.UpdateMortality asUpdateMortality(
      final long patient,
      final RequestContext context,
      final MortalityDemographic demographic) {

    Instant asOf = demographic.asOf().atStartOfDay(ZoneId.systemDefault()).toInstant();

    return new PatientCommand.UpdateMortality(
        patient,
        asOf,
        demographic.deceased(),
        demographic.deceasedOn(),
        demographic.city(),
        demographic.state(),
        demographic.county(),
        demographic.country(),
        context.requestedBy(),
        context.requestedAt());
  }

  private MortalityDemographicPatientCommandMapper() {
    // static
  }
}
