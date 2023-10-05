package gov.cdc.nbs.patient.search.address;

import gov.cdc.nbs.patient.search.PatientSearchResult;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
class PatientSearchResultAddressResolver {

  private final PatientSearchResultAddressFinder finder;


  PatientSearchResultAddressResolver(final PatientSearchResultAddressFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "addresses")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<PatientSearchResultAddress> resolve(final PatientSearchResult patient) {
    return this.finder.find(patient.patient());
  }

}
