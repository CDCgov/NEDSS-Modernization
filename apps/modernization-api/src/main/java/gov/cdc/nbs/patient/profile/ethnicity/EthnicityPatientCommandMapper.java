package gov.cdc.nbs.patient.profile.ethnicity;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.time.LocalDateInstantMapper;

public class EthnicityPatientCommandMapper {

  public static PatientCommand.UpdateEthnicityInfo asUpdateEthnicityInfo(
      final long patient,
      final RequestContext context,
      final EthnicityDemographic ethnicity
  ) {
    return new PatientCommand.UpdateEthnicityInfo(
        patient,
        LocalDateInstantMapper.from(ethnicity.asOf()),
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

  private EthnicityPatientCommandMapper() {
    //  static
  }
}
