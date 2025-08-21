package gov.cdc.nbs.patient.demographics.ethnicity;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class EthnicityPatientCommandMapper {

  public static PatientCommand.UpdateEthnicityInfo asUpdateEthnicityInfo(
      final long patient,
      final RequestContext context,
      final EthnicityDemographic ethnicity
  ) {
    return new PatientCommand.UpdateEthnicityInfo(
        patient,
        ethnicity.asOf(),
        ethnicity.ethnicGroup(),
        ethnicity.unknownReason(),
        context.requestedBy(),
        context.requestedAt());
  }

  public static PatientCommand.AddDetailedEthnicity asAddDetailedEthnicity(
      final long patient,
      final RequestContext context,
      final String detail
  ) {
    return new PatientCommand.AddDetailedEthnicity(
        patient,
        detail,
        context.requestedBy(),
        context.requestedAt()
    );
  }

  public static PatientCommand.ClearEthnicityDemographics asClearEthnicityDemographics(
      final long patient,
      final RequestContext context
  ) {
    return new PatientCommand.ClearEthnicityDemographics(
        patient,
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private EthnicityPatientCommandMapper() {
    //  static
  }
}
