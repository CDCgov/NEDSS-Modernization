package gov.cdc.nbs.patient.file;

import gov.cdc.nbs.demographics.name.DisplayableName;
import gov.cdc.nbs.patient.identifier.PatientLocalIdentifierResolver;
import gov.cdc.nbs.patient.name.PatientLegalNameResolver;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class PatientFileResolver {

  private final PatientLocalIdentifierResolver localIdentifierResolver;
  private final PatientFileFinder finder;
  private final PatientLegalNameResolver nameFinder;
  private final PatientFileDeletabilityResolver deletabilityResolver;

  PatientFileResolver(
      final PatientLocalIdentifierResolver localIdentifierResolver,
      final PatientFileFinder finder,
      final PatientLegalNameResolver nameFinder,
      final PatientFileDeletabilityResolver deletabilityResolver) {
    this.localIdentifierResolver = localIdentifierResolver;
    this.finder = finder;
    this.deletabilityResolver = deletabilityResolver;

    this.nameFinder = nameFinder;
  }

  Optional<PatientFile> resolve(final long patientId, final LocalDate asOf) {
    String local = this.localIdentifierResolver.resolve(patientId);

    return finder.find(local).map(found -> withExpandedProperties(found, asOf));
  }

  private PatientFile withExpandedProperties(final PatientFile file, final LocalDate asOf) {
    DisplayableName name = this.nameFinder.resolve(file.id(), asOf).orElse(null);
    PatientDeletability deletability = this.deletabilityResolver.resolve(file);

    return file.withName(name).withDeletability(deletability);
  }
}
