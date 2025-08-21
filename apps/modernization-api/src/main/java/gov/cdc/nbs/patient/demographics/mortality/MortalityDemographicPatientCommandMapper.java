package gov.cdc.nbs.patient.demographics.mortality;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class MortalityDemographicPatientCommandMapper {

  public static PatientCommand.UpdateMortality asUpdateMortality(
      final long patient,
      final RequestContext context,
      final MortalityDemographic demographic) {

    return new PatientCommand.UpdateMortality(
        patient,
        demographic.asOf(),
        demographic.deceased(),
        demographic.deceasedOn(),
        demographic.city(),
        demographic.state(),
        demographic.county(),
        demographic.country(),
        context.requestedBy(),
        context.requestedAt());
  }

  public static PatientCommand.ClearMoralityDemographics asClearMoralityDemographics(
      final long patient,
      final RequestContext context
  ) {
    return new PatientCommand.ClearMoralityDemographics(
        patient,
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private MortalityDemographicPatientCommandMapper() {
    // static
  }
}
