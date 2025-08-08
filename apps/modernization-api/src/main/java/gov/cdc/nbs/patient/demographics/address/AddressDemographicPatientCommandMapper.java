package gov.cdc.nbs.patient.demographics.address;

import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.RequestContext;

public class AddressDemographicPatientCommandMapper {

  public static PatientCommand.AddAddress asAddAddress(
      final long patient,
      final RequestContext context,
      final AddressDemographic input) {
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
        context.requestedAt());
  }

  public static PatientCommand.UpdateAddress asUpdateAddress(
      final long patient,
      final RequestContext context,
      final AddressDemographic demographic) {
    return new PatientCommand.UpdateAddress(
        patient,
        demographic.identifier(),
        demographic.asOf(),
        demographic.type(),
        demographic.use(),
        demographic.address1(),
        demographic.address2(),
        demographic.city(),
        demographic.state(),
        demographic.zipcode(),
        demographic.county(),
        demographic.country(),
        demographic.censusTract(),
        demographic.comment(),
        context.requestedBy(),
        context.requestedAt());
  }

  public static PatientCommand.DeleteAddress asDeleteAddress(
      final long patient,
      final RequestContext context,
      final PostalEntityLocatorParticipation existing) {
    return new PatientCommand.DeleteAddress(
        patient,
        existing.identifier(),
        context.requestedBy(),
        context.requestedAt());
  }

  private AddressDemographicPatientCommandMapper() {
    // static
  }

}
