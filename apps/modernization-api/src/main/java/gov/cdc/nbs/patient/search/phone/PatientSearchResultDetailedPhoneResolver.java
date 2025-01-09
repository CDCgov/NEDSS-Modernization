package gov.cdc.nbs.patient.search.phone;

import gov.cdc.nbs.patient.search.PatientSearchResult;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
class PatientSearchResultDetailedPhoneResolver {

  private final PatientSearchResultDetailedPhoneFinder finder;

  PatientSearchResultDetailedPhoneResolver(final PatientSearchResultDetailedPhoneFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "detailedPhones")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<PatientSearchResultPhone> resolve(final PatientSearchResult patient) {
    return this.finder.find(patient.patient());
  }
}
