package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.graphql.filter.PatientFilter;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchResultResolver {

  private final PatientSearcher service;

  public PatientSearchResultResolver(final PatientSearcher service) {
    this.service = service;
  }

  @QueryMapping("findPatientsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Page<PatientSearchResult> findPatientsByFilter(@Argument PatientFilter filter, @Argument GraphQLPage page) {
    return service.search(filter, page);
  }

}
