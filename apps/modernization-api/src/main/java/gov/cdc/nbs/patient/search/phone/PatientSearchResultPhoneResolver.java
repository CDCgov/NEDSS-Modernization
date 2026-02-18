package gov.cdc.nbs.patient.search.phone;

import gov.cdc.nbs.patient.search.PatientSearchResult;
import java.util.Collection;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchResultPhoneResolver {

  private final PatientSearchResultPhoneFinder finder;

  PatientSearchResultPhoneResolver(final PatientSearchResultPhoneFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "phones")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<String> resolve(final PatientSearchResult patient) {
    return this.finder.find(patient.patient());
  }
}
