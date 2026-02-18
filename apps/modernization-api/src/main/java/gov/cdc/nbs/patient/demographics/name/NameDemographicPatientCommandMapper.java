package gov.cdc.nbs.patient.demographics.name;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class NameDemographicPatientCommandMapper {

  public static PatientCommand.AddName asAddName(
      final long patient, final RequestContext context, final NameDemographic demographic) {

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
        context.requestedAt());
  }

  public static PatientCommand.UpdateNameInfo asUpdateName(
      final long patient, final RequestContext context, final NameDemographic demographic) {

    return new PatientCommand.UpdateNameInfo(
        patient,
        demographic.sequence(),
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
        context.requestedAt());
  }

  public static PatientCommand.DeleteNameInfo asDeleteName(
      final long patient, final short sequence, final RequestContext context) {
    return new PatientCommand.DeleteNameInfo(
        patient, sequence, context.requestedBy(), context.requestedAt());
  }

  private NameDemographicPatientCommandMapper() {
    // static
  }
}
