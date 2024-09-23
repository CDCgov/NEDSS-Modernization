package gov.cdc.nbs.patient.profile.administrative;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.time.LocalDateInstantMapper;

import java.time.Instant;

public class AdministrativePatientCommandMapper {

  public static PatientCommand.UpdateAdministrativeInfo asUpdateAdministrativeInfo(
      final long patient,
      final RequestContext context,
      final Administrative administrative
  ) {

    Instant asOf = LocalDateInstantMapper.from(administrative.asOf());

    return new PatientCommand.UpdateAdministrativeInfo(
        patient,
        asOf,
        administrative.comment(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private AdministrativePatientCommandMapper() {
    //  static
  }
}
