package gov.cdc.nbs.patient.file.edit;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Component;

import gov.cdc.nbs.change.ChangeResolver;
import gov.cdc.nbs.change.Changes;
import gov.cdc.nbs.entity.odse.EntityId;
import gov.cdc.nbs.entity.odse.Person;
import gov.cdc.nbs.patient.RequestContext;
import gov.cdc.nbs.patient.demographics.identification.IdentificationDemographic;
import static gov.cdc.nbs.patient.demographics.identification.IdentificationDemographicPatientCommandMapper.asAddIdentification;
import static gov.cdc.nbs.patient.demographics.identification.IdentificationDemographicPatientCommandMapper.asUpdateIdentification;
import static gov.cdc.nbs.patient.demographics.identification.IdentificationDemographicPatientCommandMapper.asDeleteIdentification;

@Component
class PatientIdentificationEditService {

  private static long identifiedBy(final IdentificationDemographic demographic) {
    return demographic.sequence() == null ? demographic.hashCode() : demographic.sequence();
  }

  private static long identifyPersonName(EntityId identifier) {
    return Objects.hash(identifier.getId().getEntityIdSeq());
  }

  private final ChangeResolver<EntityId, IdentificationDemographic, Long> resolver = ChangeResolver
      .ofDifferingTypes(
          PatientIdentificationEditService::identifyPersonName,
          PatientIdentificationEditService::identifiedBy);

  void apply(
      final RequestContext context,
      final Person patient,
      final Collection<IdentificationDemographic> demographics) {
    Changes<EntityId, IdentificationDemographic> changes = resolver.resolve(patient.identifications(),
        demographics);

    changes.added()
        .map(demographic -> asAddIdentification(patient.getId(), context, demographic))
        .forEach(patient::add);

    changes.altered()
        .map(match -> asUpdateIdentification(patient.getId(), context, match.right()))
        .forEach(patient::update);

    changes.removed()
        .map(existing -> asDeleteIdentification(patient.getId(), context, existing))
        .forEach(patient::delete);
  }

}
