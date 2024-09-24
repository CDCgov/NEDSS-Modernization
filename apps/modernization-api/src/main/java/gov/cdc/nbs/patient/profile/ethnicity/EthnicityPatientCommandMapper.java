package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class EthnicityPatientCommandMapper {

  public static PatientCommand.UpdateEthnicityInfo asUpdateEthnicityInfo(
      final long patient,
      final RequestContext context,
      final EthnicityDemographic ethnicity) {
    return new PatientCommand.UpdateEthnicityInfo(
        patient,
        ethnicity.asOf(),
        ethnicity.ethnicGroup(),
        ethnicity.unknownReason(),
        context.requestedBy(),
        context.requestedAt());
  }

  private EthnicityPatientCommandMapper() {
    //  static
  }
}
