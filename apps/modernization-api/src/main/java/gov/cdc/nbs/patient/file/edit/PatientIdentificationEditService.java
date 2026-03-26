package gov.cdc.nbs.patient.file.edit;

import static gov.cdc.nbs.patient.demographics.identification.IdentificationDemographicPatientCommandMapper.*;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.change.Match;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographics.identification.IdentificationDemographic;
import java.util.Collection;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
class PatientIdentificationEditService {

  private static long identifiedBy(final IdentificationDemographic demographic) {
    return demographic.sequence() == null
        ? demographic.hashCode()
        : Objects.hash(demographic.sequence());
  }

  private static long identifyPersonName(EntityId identifier) {
    return Objects.hash(identifier.identifier().getEntityIdSeq());
  }

  private static boolean changed(final Match.Both<EntityId, IdentificationDemographic> both) {
    EntityId existing = both.left();
    IdentificationDemographic demographic = both.right();

    return !(Objects.equals(existing.asOf(), demographic.asOf())
        && Objects.equals(existing.type(), demographic.type())
        && Objects.equals(existing.issuer(), demographic.issuer())
        && Objects.equals(existing.value(), demographic.value()));
  }

  private final ChangeResolver<EntityId, IdentificationDemographic, Long> resolver =
      ChangeResolver.ofDifferingTypes(
          PatientIdentificationEditService::identifyPersonName,
          PatientIdentificationEditService::identifiedBy);

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<IdentificationDemographic> demographics) {
    Changes<EntityId, IdentificationDemographic> changes =
        resolver.resolve(patient.identifications(), demographics);

    changes
        .added()
        .map(demographic -> asAddIdentification(patient.id(), context, demographic))
        .forEach(patient::add);

    changes
        .altered()
        .filter(PatientIdentificationEditService::changed)
        .map(match -> asUpdateIdentification(patient.id(), context, match.right()))
        .forEach(patient::update);

    changes
        .removed()
        .map(existing -> asDeleteIdentification(patient.id(), context, existing))
        .forEach(patient::delete);
  }
}
