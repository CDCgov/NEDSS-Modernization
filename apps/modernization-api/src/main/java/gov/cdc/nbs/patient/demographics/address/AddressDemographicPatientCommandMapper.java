package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class AddressDemographicPatientCommandMapper {

  public static PatientCommand.AddAddress asAddAddress(
      final long patient,
      final RequestContext context,
      final AddressDemographic input
  ) {
    return new PatientCommand.AddAddress(
        patient,
        input.asOf(),
        input.type(),
        input.use(),
        input.address1(),
        input.address2(),
        input.city(),
        input.state(),
        input.zipcode(),
        input.county(),
        input.country(),
        input.censusTract(),
        input.comment(),
        context.requestedBy(),
        context.requestedAt()
    );
  }

  private AddressDemographicPatientCommandMapper() {
    // static
  }

}
