package gov.cdc.nbs.patient.file.edit;

import static gov.cdc.nbs.patient.demographics.name.NameDemographicPatientCommandMapper.asAddName;
import static gov.cdc.nbs.patient.demographics.name.NameDemographicPatientCommandMapper.asDeleteName;
import static gov.cdc.nbs.patient.demographics.name.NameDemographicPatientCommandMapper.asUpdateName;

import java.util.Collection;
import java.util.Objects;

import gov.cdc.nbs.change.Match;
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

  private static long identifyPersonName(PersonName name) {
    return Objects.hash(name.identifier().getPersonNameSeq());
  }

  private static long identifyNameDemographic(NameDemographic name) {
    return name.sequence() != null ? Objects.hash(name.sequence()) : name.hashCode();
  }

  private static boolean changed(final Match.Both<PersonName, NameDemographic> both) {
    PersonName existing = both.left();
    NameDemographic demographic = both.right();

    return !(Objects.equals(existing.asOf(), demographic.asOf())
        && Objects.equals(existing.type(), demographic.type())
        && Objects.equals(existing.prefix(), demographic.prefix())
        && Objects.equals(existing.first(), demographic.first())
        && Objects.equals(existing.middle(), demographic.middle())
        && Objects.equals(existing.secondMiddle(), demographic.secondMiddle())
        && Objects.equals(existing.last(), demographic.last())
        && Objects.equals(existing.secondLast(), demographic.secondLast())
        && Objects.equals(existing.suffix(), demographic.suffix())
        && Objects.equals(existing.degree(), demographic.degree())
    );
  }

  // Gets a hash of the name sequence for comparison, except in the case of a new
  // NameDemographic (no sequence), then hash the entire object
  private final ChangeResolver<PersonName, NameDemographic, Long> resolver = ChangeResolver.ofDifferingTypes(
      PatientNameEditService::identifyPersonName,
      PatientNameEditService::identifyNameDemographic
  );

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<NameDemographic> demographics) {
    Changes<PersonName, NameDemographic> changes = resolver.resolve(patient.names(), demographics);

    changes.added()
        .map(demographic -> asAddName(patient.id(), context, demographic))
        .forEach(command -> patient.add(soundexResolver, command));

    changes.altered()
        .filter(PatientNameEditService::changed)
        .map(match -> asUpdateName(patient.id(), context, match.right()))
        .forEach(command -> patient.update(soundexResolver, command));

    changes.removed()
        .map(existing -> asDeleteName(patient.id(), existing.identifier().getPersonNameSeq(), context))
        .forEach(patient::delete);
  }
}
