package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.graphql.GraphQLPage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchResultResolver {

  private final PatientSearchPageableResolver resolver;
  private final PatientSearcher service;
  private final AuthorizedPatientFilterAdjuster adjuster;

  PatientSearchResultResolver(
      final PatientSearchPageableResolver resolver,
      final PatientSearcher service
  ) {
    this.resolver = resolver;
    this.service = service;
    this.adjuster = new AuthorizedPatientFilterAdjuster(new Permission("FindInactive", "Patient"));
  }

  @QueryMapping("findPatientsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Page<PatientSearchResult> findPatientsByFilter(
      @AuthenticationPrincipal final NbsUserDetails user,
      @Argument final PatientFilter filter,
      @Argument final GraphQLPage page
  ) {

    PatientFilter adjusted = PatientFilterValidator.validate(this.adjuster.adjusted(user, filter));
    Pageable pageable = resolver.from(page);

    return service.search(
        adjusted,
        pageable
    );
  }


}
