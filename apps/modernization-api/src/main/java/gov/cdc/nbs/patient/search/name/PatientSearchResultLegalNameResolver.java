package gov.cdc.nbs.patient.search.name;

import gov.cdc.nbs.demographics.name.DisplayableName;
import gov.cdc.nbs.patient.name.PatientLegalNameResolver;
import gov.cdc.nbs.patient.search.PatientSearchResult;
import java.util.Optional;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchResultLegalNameResolver {

  private final PatientLegalNameResolver resolver;

  PatientSearchResultLegalNameResolver(final PatientLegalNameResolver resolver) {
    this.resolver = resolver;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "legalName")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Optional<DisplayableName> resolve(final PatientSearchResult patient) {
    return this.resolver.resolve(patient.patient());
  }
}
