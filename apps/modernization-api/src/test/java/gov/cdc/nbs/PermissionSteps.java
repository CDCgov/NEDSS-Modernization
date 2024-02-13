package gov.cdc.nbs;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.event.search.InvestigationFilter;
import gov.cdc.nbs.event.search.LabReportFilter;
import gov.cdc.nbs.event.search.investigation.InvestigationResolver;
import gov.cdc.nbs.event.search.labreport.LabReportResolver;
import gov.cdc.nbs.graphql.GraphQLPage;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Transactional
public class PermissionSteps {

  private final Active<ActiveUser> activeUser;
  private final LabReportResolver labReportResolver;
  private final InvestigationResolver investigationResolver;
  private final Active<UserDetails> activeUserDetails;

  private Object response;
  private AccessDeniedException exception;

  public PermissionSteps(
      final Active<ActiveUser> activeUser,
      final LabReportResolver labReportResolver,
      final InvestigationResolver investigationResolver,
      final Active<UserDetails> activeUserDetails
  ) {
    this.activeUser = activeUser;
    this.labReportResolver = labReportResolver;
    this.investigationResolver = investigationResolver;
    this.activeUserDetails = activeUserDetails;
  }

  @Before
  public void clearAuth() {
    SecurityContextHolder.getContext().setAuthentication(null);
    activeUserDetails.reset();
  }

  @Given("I have the authorities: {string} for the jurisdiction: {string} and program area: {string}")
  public void i_have_the_authority(String authoritiesString, String jurisdiction, String programArea) {
    Set<GrantedAuthority> authorities = Arrays.stream(authoritiesString.split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toSet());

    var currentAuth = SecurityContextHolder.getContext().getAuthentication();
    if (currentAuth == null) {
      // no auth is set, create a new NbsUserDetails object and set the authentication
      // on the securityContextHolder

      //  The active user is set by the "I am logged into NBS and a security log entry exists" step and is not
      //  always called in a feature.  When a user is not active then just give the ID of 1.  Will fix the features
      //  to always activate a user.
      long id = activeUser.maybeActive()
          .map(ActiveUser::id)
          .orElse(1L);

      var nbsUserDetails = new NbsUserDetails(
          id,
          "MOCK-USER",
          "MOCK",
          "USER",
          authorities,
          true
      );
      applyUserDetails(nbsUserDetails);

    } else {
      // add authority to existing userDetails
      var existingUserDetails = (NbsUserDetails) currentAuth.getPrincipal();
      var existingAuthorities = existingUserDetails.getAuthorities();
      if (existingAuthorities == null) {
        existingAuthorities = new HashSet<>();
      }
      existingAuthorities.addAll(authorities);
      var nbsUserDetails = new NbsUserDetails(
          existingUserDetails.getId(),
          existingUserDetails.getUsername(),
          existingUserDetails.getFirstName(),
          existingUserDetails.getLastName(),
          authorities,
          true
      );

      applyUserDetails(nbsUserDetails);
    }
  }

  private void applyUserDetails(final UserDetails userDetails) {
    activeUserDetails.active(userDetails);

    var pat = new PreAuthenticatedAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(pat);
  }

  @When("I search for a patient by {string}")
  public void i_search_for_a_patient(String searchType) {
    var page = new GraphQLPage(10, 0);
    try {
      switch (searchType) {
        case "findInvestigation":
          response = investigationResolver.findInvestigationsByFilter(new InvestigationFilter(), page);
          break;
        case "findLabReport":
          response = labReportResolver.findLabReportsByFilter(new LabReportFilter(), page);
          break;
        default:
          throw new RuntimeException("Invalid searchType: " + searchType);
      }
    } catch (AccessDeniedException e) {
      exception = e;
    }
  }

  @Then("a {string} result is returned")
  public void a_response_type_is_returned(String resultType) {
    if (resultType.equals("success")) {
      assertNotNull(response);
      assertNull(exception);
    } else if (resultType.equals("AccessDeniedException")) {
      assertNotNull(exception);
      assertNull(response);
    } else {
      throw new RuntimeException("Invalid responseType specified: " + resultType);
    }
  }

}
