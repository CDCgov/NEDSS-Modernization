package gov.cdc.nbs.patient.file.edit;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographicPatientCommandMapper.*;

@Component
class PatientPhoneEditService {

  private static long identifiedBy(final TeleEntityLocatorParticipation participation) {
    return participation.getId().getLocatorUid();
  }

  private static long identifiedBy(final PhoneDemographic demographic) {
    return demographic.identifier() == null ? demographic.hashCode() : demographic.identifier();
  }

  private final ChangeResolver<TeleEntityLocatorParticipation, PhoneDemographic, Long> resolver = ChangeResolver
      .ofDifferingTypes(
          PatientPhoneEditService::identifiedBy,
          PatientPhoneEditService::identifiedBy
      );

  private final PhoneIdentifierGenerator phoneIdentifierGenerator;

  public PatientPhoneEditService(final PhoneIdentifierGenerator phoneIdentifierGenerator) {
    this.phoneIdentifierGenerator = phoneIdentifierGenerator;
  }

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<PhoneDemographic> demographics) {
    Changes<TeleEntityLocatorParticipation, PhoneDemographic> changes = resolver.resolve(patient.phones(),
        demographics);

    changes.added()
        .map(demographic -> asAddPhone(patient.id(), context, demographic))
        .forEach(command -> patient.add(command, phoneIdentifierGenerator));

    changes.altered()
        .map(match -> asUpdatePhone(patient.id(), context, match.right()))
        .forEach(patient::update);

    changes.removed()
        .map(existing -> asDeletePhone(patient.id(), context, existing))
        .forEach(patient::delete);
  }
}
