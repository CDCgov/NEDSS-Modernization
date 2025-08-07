package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographics.address.AddressDemographic;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static gov.cdc.nbs.patient.demographics.address.AddressDemographicPatientCommandMapper.asAddAddress;
import static gov.cdc.nbs.patient.demographics.address.AddressDemographicPatientCommandMapper.asUpdateAddress;
import static gov.cdc.nbs.patient.demographics.address.AddressDemographicPatientCommandMapper.asDeleteAddress;

@Component
class PatientAddressEditService {

  private static long identifiedBy(final AddressDemographic demographic) {
    return demographic.identifier() == null ? demographic.hashCode() : demographic.identifier();
  }

  private final ChangeResolver<PostalEntityLocatorParticipation, AddressDemographic, Long> resolver = ChangeResolver
      .ofDifferingTypes(
          PostalEntityLocatorParticipation::identifier,
          PatientAddressEditService::identifiedBy);

  private final AddressIdentifierGenerator addressIdentifierGenerator;

  PatientAddressEditService(final AddressIdentifierGenerator addressIdentifierGenerator) {
    this.addressIdentifierGenerator = addressIdentifierGenerator;
  }

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<AddressDemographic> demographics) {
    Changes<PostalEntityLocatorParticipation, AddressDemographic> changes = resolver.resolve(patient.addresses(),
        demographics);

    changes.added()
        .map(demographic -> asAddAddress(patient.getId(), context, demographic))
        .forEach(command -> patient.add(command, addressIdentifierGenerator));

    changes.altered()
        .map(match -> asUpdateAddress(patient.getId(), context, match.right()))
        .forEach(patient::update);

    changes.removed()
        .map(existing -> asDeleteAddress(patient.getId(), context, existing))
        .forEach(patient::delete);

  }

}
