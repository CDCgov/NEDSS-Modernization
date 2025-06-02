package gov.cdc.nbs.patient.profile.address.change;

import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientNotFoundException;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.profile.PatientProfileService;
import org.springframework.stereotype.Component;

@Component
public class PatientAddressChangeService {

  private final AddressIdentifierGenerator generator;
  private final PatientProfileService service;

  PatientAddressChangeService(
      final AddressIdentifierGenerator generator,
      final PatientProfileService service
  ) {
    this.generator = generator;
    this.service = service;
  }

  public PatientAddressAdded add(final RequestContext context, final NewPatientAddressInput input) {
    return service.with(
            input.patient(),
            found -> found.add(
                new PatientCommand.AddAddress(
                    input.patient(),
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
                ),
                this.generator
            )
        )
        .map(added -> new PatientAddressAdded(input.patient(), added.getId().getLocatorUid()))
        .orElseThrow(() -> new PatientNotFoundException(input.patient()));
  }

  void update(final RequestContext context, final UpdatePatientAddressInput input) {

    this.service.using(input.patient(), found -> found.update(
        new PatientCommand.UpdateAddress(
            input.patient(),
            input.id(),
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
            context.requestedAt())));

  }

  void delete(final RequestContext context, final DeletePatientAddressInput input) {

    this.service.using(input.patient(), found -> found.delete(
        new PatientCommand.DeleteAddress(
            input.patient(),
            input.id(),
            context.requestedBy(),
            context.requestedAt())));
  }
}
