package gov.cdc.nbs.patient.file.edit;

import static gov.cdc.nbs.patient.demographics.address.AddressDemographicPatientCommandMapper.*;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.change.Match;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.AddressIdentifierGenerator;
import gov.cdc.nbs.patient.demographics.address.AddressDemographic;
import java.util.Collection;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
class PatientAddressEditService {

  private static long identifiedBy(final PostalEntityLocatorParticipation participation) {
    return participation.identifier().getLocatorUid();
  }

  private static long identifiedBy(final AddressDemographic demographic) {
    return demographic.identifier() == null ? demographic.hashCode() : demographic.identifier();
  }

  private static boolean changed(
      final Match.Both<PostalEntityLocatorParticipation, AddressDemographic> both) {
    PostalEntityLocatorParticipation existing = both.left();
    AddressDemographic demographic = both.right();

    return !(Objects.equals(existing.asOf(), demographic.asOf())
        && Objects.equals(existing.type(), demographic.type())
        && Objects.equals(existing.use(), demographic.use())
        && Objects.equals(existing.locator().address1(), demographic.address1())
        && Objects.equals(existing.locator().address2(), demographic.address2())
        && Objects.equals(existing.locator().city(), demographic.city())
        && Objects.equals(existing.locator().county(), demographic.county())
        && Objects.equals(existing.locator().zip(), demographic.zipcode())
        && Objects.equals(existing.locator().state(), demographic.state())
        && Objects.equals(existing.locator().country(), demographic.country())
        && Objects.equals(existing.locator().censusTract(), demographic.censusTract())
        && Objects.equals(existing.comments(), demographic.comment()));
  }

  private final ChangeResolver<PostalEntityLocatorParticipation, AddressDemographic, Long>
      resolver =
          ChangeResolver.ofDifferingTypes(
              PatientAddressEditService::identifiedBy, PatientAddressEditService::identifiedBy);

  private final AddressIdentifierGenerator addressIdentifierGenerator;

  PatientAddressEditService(final AddressIdentifierGenerator addressIdentifierGenerator) {
    this.addressIdentifierGenerator = addressIdentifierGenerator;
  }

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<AddressDemographic> demographics) {
    Changes<PostalEntityLocatorParticipation, AddressDemographic> changes =
        resolver.resolve(patient.addresses(), demographics);

    changes
        .added()
        .map(demographic -> asAddAddress(patient.id(), context, demographic))
        .forEach(command -> patient.add(command, addressIdentifierGenerator));

    changes
        .altered()
        .filter(PatientAddressEditService::changed)
        .map(match -> asUpdateAddress(patient.id(), context, match.right()))
        .forEach(patient::update);

    changes
        .removed()
        .map(existing -> asDeleteAddress(patient.id(), context, existing))
        .forEach(patient::delete);
  }
}
