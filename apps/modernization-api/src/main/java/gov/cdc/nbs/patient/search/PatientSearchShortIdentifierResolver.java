package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.patient.identifier.PatientShortIdentifierResolver;
import java.util.OptionalLong;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchShortIdentifierResolver {

  private final PatientShortIdentifierResolver resolver;

  PatientSearchShortIdentifierResolver(final PatientShortIdentifierResolver resolver) {
    this.resolver = resolver;
  }

  @SchemaMapping(typeName = "PatientSearchResult", value = "shortId")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  OptionalLong resolve(final PatientSearchResult patient) {
    return this.resolver.resolve(patient.local());
  }
}
