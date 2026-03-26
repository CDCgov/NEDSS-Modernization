package gov.cdc.nbs;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.testing.authorization.ActiveUser;
import gov.cdc.nbs.testing.support.Active;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PermissionSteps {

  private final Active<ActiveUser> activeUser;

  private final Active<UserDetails> activeUserDetails;

  public PermissionSteps(
      final Active<ActiveUser> activeUser, final Active<UserDetails> activeUserDetails) {
    this.activeUser = activeUser;
    this.activeUserDetails = activeUserDetails;
  }

  @Before
  public void clearAuth() {
    SecurityContextHolder.getContext().setAuthentication(null);
    activeUserDetails.reset();
  }

  @Given(
      "I have the authorities: {string} for the jurisdiction: {string} and program area: {string}")
  public void i_have_the_authority(
      String authoritiesString, String jurisdiction, String programArea) {
    Set<GrantedAuthority> authorities =
        Arrays.stream(authoritiesString.split(","))
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());

    var currentAuth = SecurityContextHolder.getContext().getAuthentication();
    if (currentAuth == null) {
      // no auth is set, create a new NbsUserDetails object and set the authentication
      // on the securityContextHolder

      //  The active user is set by the "I am logged into NBS and a security log entry exists" step
      // and is not
      //  always called in a feature.  When a user is not active then just give the ID of 1.  Will
      // fix the features
      //  to always activate a user.
      long id = activeUser.maybeActive().map(ActiveUser::id).orElse(1L);

      var nbsUserDetails = new NbsUserDetails(id, "MOCK-USER", "MOCK", "USER", authorities, true);
      applyUserDetails(nbsUserDetails);

    } else {
      // add authority to existing userDetails
      var existingUserDetails = (NbsUserDetails) currentAuth.getPrincipal();
      var existingAuthorities = existingUserDetails.getAuthorities();
      if (existingAuthorities == null) {
        existingAuthorities = new HashSet<>();
      }
      existingAuthorities.addAll(authorities);
      var nbsUserDetails =
          new NbsUserDetails(
              existingUserDetails.getId(),
              existingUserDetails.getUsername(),
              existingUserDetails.getFirstName(),
              existingUserDetails.getLastName(),
              authorities,
              true);

      applyUserDetails(nbsUserDetails);
    }
  }

  private void applyUserDetails(final UserDetails userDetails) {
    activeUserDetails.active(userDetails);

    var pat =
        new PreAuthenticatedAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(pat);
  }
}
