package gov.cdc.nbs.patient.search;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.search.SearchResolver;
import gov.cdc.nbs.search.SearchResult;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
class PatientSearchResultResolver {

  private final SearchGraphQLPageableMapper mapper;
  private final SearchResolver<PatientFilter, PatientSearchResult> resolver;
  private final AuthorizedPatientFilterAdjuster adjuster;

  PatientSearchResultResolver(
      final SearchGraphQLPageableMapper mapper,
      final SearchResolver<PatientFilter, PatientSearchResult> resolver
  ) {
    this.mapper = mapper;
    this.resolver = resolver;
    this.adjuster = new AuthorizedPatientFilterAdjuster(new Permission("FindInactive", "Patient"));
  }

  @QueryMapping("findPatientsByFilter")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  SearchResult<PatientSearchResult> resolve(
      @AuthenticationPrincipal final NbsUserDetails user,
      @Argument final PatientFilter filter,
      @Argument final GraphQLPage page
  ) {

    PatientFilter adjusted = PatientFilterValidator.validate(this.adjuster.adjusted(user, filter));
    Pageable pageable = mapper.from(page);

    return resolver.search(
        adjusted,
        pageable
    );
  }


}
