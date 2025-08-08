package gov.cdc.nbs.patient.file.edit;

import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographicPatientCommandMapper.asAddPhone;
import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographicPatientCommandMapper.asUpdatePhone;
import static gov.cdc.nbs.patient.demographics.phone.PhoneDemographicPatientCommandMapper.asDeletePhone;

import java.util.Collection;

import org.springframework.stereotype.Component;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.TeleEntityLocatorParticipation;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.phone.PhoneIdentifierGenerator;
import gov.cdc.nbs.patient.demographics.phone.PhoneDemographic;

@Component
class PatientPhoneEditService {

  private static long identifiedBy(final PhoneDemographic demographic) {
    return demographic.identifier() == null ? demographic.hashCode() : demographic.identifier();
  }

  private final ChangeResolver<TeleEntityLocatorParticipation, PhoneDemographic, Long> resolver = ChangeResolver
      .ofDifferingTypes(
          TeleEntityLocatorParticipation::identifier,
          PatientPhoneEditService::identifiedBy);

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
        .map(demographic -> asAddPhone(patient.getId(), context, demographic))
        .forEach(command -> patient.add(command, phoneIdentifierGenerator));

    changes.altered()
        .map(match -> asUpdatePhone(patient.getId(), context, match.right()))
        .forEach(patient::update);

    changes.removed()
        .map(existing -> asDeletePhone(patient.getId(), context, existing))
        .forEach(patient::delete);
  }
}
