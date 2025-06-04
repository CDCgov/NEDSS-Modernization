package gov.cdc.nbs.patient.demographics.identification;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class IdentificationDemographicPatientCommandMapper {

  public static PatientCommand.AddIdentification asAddIdentification(
      final long patient,
      final RequestContext context,
      final IdentificationDemographic demographic
  ) {

    return new PatientCommand.AddIdentification(
        patient,
        demographic.asOf(),
        demographic.value(),
        demographic.issuer(),
        demographic.type(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private IdentificationDemographicPatientCommandMapper() {
    // static
  }

}
