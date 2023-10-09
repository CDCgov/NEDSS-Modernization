package gov.cdc.nbs.patient.search.email;

import gov.cdc.nbs.patient.search.PatientSearchResult;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
class PatientSearchResultEmailResolver {

  private final PatientSearchResultEmailFinder finder;

  PatientSearchResultEmailResolver(final PatientSearchResultEmailFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "emails")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<String> resolve(final PatientSearchResult patient) {
    return this.finder.find(patient.patient());
  }
}
