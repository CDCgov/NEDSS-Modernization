package gov.cdc.nbs.patient.demographics.administrative;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class AdministrativePatientCommandMapper {

  public static PatientCommand.UpdateAdministrativeInfo asUpdateAdministrativeInfo(
      final long patient, final RequestContext context, final Administrative administrative) {

    return new PatientCommand.UpdateAdministrativeInfo(
        patient,
        administrative.asOf(),
        administrative.comment(),
        context.requestedBy(),
        context.requestedAt());
  }

  public static PatientCommand.ClearAdministrativeInformation asClearAdministrativeInformation(
      final long patient, final RequestContext context) {
    return new PatientCommand.ClearAdministrativeInformation(
        patient, context.requestedBy(), context.requestedAt());
  }

  private AdministrativePatientCommandMapper() {
    //  static
  }
}
