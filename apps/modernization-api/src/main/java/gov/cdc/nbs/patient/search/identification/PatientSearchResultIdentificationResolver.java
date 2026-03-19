package gov.cdc.nbs.patient.search.identification;

import gov.cdc.nbs.patient.search.PatientSearchResult;
import java.util.Collection;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchResultIdentificationResolver {

  private final PatientSearchResultIdentificationFinder finder;

  PatientSearchResultIdentificationResolver(final PatientSearchResultIdentificationFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "identification")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<PatientSearchResultIdentification> resolve(final PatientSearchResult patient) {
    return this.finder.find(patient.patient());
  }
}
