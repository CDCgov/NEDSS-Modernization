package gov.cdc.nbs.patient.file.edit;

import static gov.cdc.nbs.patient.demographics.name.NameDemographicPatientCommandMapper.asAddName;
import static gov.cdc.nbs.patient.demographics.name.NameDemographicPatientCommandMapper.asDeleteName;
import static gov.cdc.nbs.patient.demographics.name.NameDemographicPatientCommandMapper.asUpdateName;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.entity.odse.PersonName;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographic.name.SoundexResolver;
import gov.cdc.nbs.patient.demographics.name.NameDemographic;

@Component
class PatientNameEditService {
  private final SoundexResolver soundexResolver;

  public PatientNameEditService(final SoundexResolver soundexResolver) {
    this.soundexResolver = soundexResolver;
  }

  private long identifyPersonName(PersonName name) {
    return Objects.hash(name.getId().getPersonNameSeq());
  }

  private long identifyNameDemographic(NameDemographic name) {
    return name.sequence() != null ? Objects.hash(name.sequence()) : name.hashCode();
  }

  // Gets a hash of the name sequence for comparison, except in the case of a new
  // NameDemographic (no sequence), then hash the entire object
  private final ChangeResolver<PersonName, NameDemographic, Long> resolver = ChangeResolver.ofDifferingTypes(
      this::identifyPersonName,
      this::identifyNameDemographic);

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<NameDemographic> demographics) {
    Changes<PersonName, NameDemographic> changes = resolver.resolve(patient.names(), demographics);

    changes.added()
        .map(demographic -> asAddName(patient.getId(), context, demographic))
        .forEach(command -> patient.add(soundexResolver, command));

    changes.altered()
        .map(match -> asUpdateName(patient.getId(), context, match.right()))
        .forEach(command -> patient.update(soundexResolver, command));

    changes.removed()
        .map(existing -> asDeleteName(patient.getId(), existing.getId().getPersonNameSeq(), context))
        .forEach(patient::delete);
  }
}
