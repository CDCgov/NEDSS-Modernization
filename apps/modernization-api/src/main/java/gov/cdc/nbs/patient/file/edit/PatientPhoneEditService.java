package gov.cdc.nbs.patient.file.edit;

import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographicPatientCommandMapper.*;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.change.Match;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;
import java.util.Collection;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
class PatientPhoneEditService {

  private static long identifiedBy(final TeleEntityLocatorParticipation participation) {
    return participation.identifier().getLocatorUid();
  }

  private static long identifiedBy(final PhoneDemographic demographic) {
    return demographic.identifier() == null ? demographic.hashCode() : demographic.identifier();
  }

  private static boolean changed(
      final Match.Both<TeleEntityLocatorParticipation, PhoneDemographic> both) {
    TeleEntityLocatorParticipation existing = both.left();
    PhoneDemographic demographic = both.right();

    return !(Objects.equals(existing.asOf(), demographic.asOf())
        && Objects.equals(existing.type(), demographic.type())
        && Objects.equals(existing.use(), demographic.use())
        && Objects.equals(existing.locator().countryCode(), demographic.countryCode())
        && Objects.equals(existing.locator().phoneNumber(), demographic.phoneNumber())
        && Objects.equals(existing.locator().extension(), demographic.extension())
        && Objects.equals(existing.locator().email(), demographic.email())
        && Objects.equals(existing.locator().url(), demographic.url())
        && Objects.equals(existing.comments(), demographic.comment()));
  }

  private final ChangeResolver<TeleEntityLocatorParticipation, PhoneDemographic, Long> resolver =
      ChangeResolver.ofDifferingTypes(
          PatientPhoneEditService::identifiedBy, PatientPhoneEditService::identifiedBy);

  private final PhoneIdentifierGenerator phoneIdentifierGenerator;

  public PatientPhoneEditService(final PhoneIdentifierGenerator phoneIdentifierGenerator) {
    this.phoneIdentifierGenerator = phoneIdentifierGenerator;
  }

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<PhoneDemographic> demographics) {

    Changes<TeleEntityLocatorParticipation, PhoneDemographic> changes =
        resolver.resolve(patient.phones(), demographics);

    changes
        .added()
        .map(demographic -> asAddPhone(patient.id(), context, demographic))
        .forEach(command -> patient.add(command, phoneIdentifierGenerator));

    changes
        .altered()
        .filter(PatientPhoneEditService::changed)
        .map(match -> asUpdatePhone(patient.id(), context, match.right()))
        .forEach(patient::update);

    changes
        .removed()
        .map(existing -> asDeletePhone(patient.id(), context, existing))
        .forEach(patient::delete);
  }
}
