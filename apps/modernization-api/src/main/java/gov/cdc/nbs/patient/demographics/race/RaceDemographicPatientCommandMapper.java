package gov.cdc.nbs.patient.demographics.race;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class RaceDemographicPatientCommandMapper {

  public static PatientCommand.AddRaceInfo asAddRace(
      final long patient,
      final RequestContext context,
      final RaceDemographic demographic
  ) {

    return new PatientCommand.AddRaceInfo(
        patient,
        demographic.asOf(),
        demographic.race(),
        demographic.detailed(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private RaceDemographicPatientCommandMapper() {
    // static
  }

}
