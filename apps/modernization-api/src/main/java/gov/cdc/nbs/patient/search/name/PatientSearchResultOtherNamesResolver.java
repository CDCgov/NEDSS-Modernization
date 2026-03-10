package gov.cdc.nbs.patient.search.name;

import gov.cdc.nbs.patient.search.PatientSearchResult;
import java.util.Collection;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchResultOtherNamesResolver {

  private final PatientSearchResultOtherNamesFinder finder;

  PatientSearchResultOtherNamesResolver(final PatientSearchResultOtherNamesFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "names")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<PatientSearchResultName> resolve(final PatientSearchResult patient) {
    return this.finder.find(patient.patient());
  }
}
