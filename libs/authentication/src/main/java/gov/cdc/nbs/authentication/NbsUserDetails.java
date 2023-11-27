package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authorization.permission.Permission;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
@Builder
@RequiredArgsConstructor
public class NbsUserDetails implements UserDetails {
  private final Long id;
  private final String username;
  private final String firstName;
  private final String lastName;
  private final boolean isMasterSecurityAdmin;
  private final boolean isProgramAreaAdmin;
  private final Set<String> adminProgramAreas;
  private final String password;
  private final Set<NbsAuthority> authorities;
  private final boolean isEnabled;

  @Override
  public boolean isAccountNonExpired() {
    return isEnabled;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonExpired();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isAccountNonExpired();
  }

  /**
   * Checks if user has the specified permission
   *
   * @param object    The resource the action can be preformed on
   * @param operation The granted action
   * @return {@code true} if the operation can be performed on the object.
   */
  public boolean hasPermission(
      final String object,
      final String operation
  ) {
    if (getAuthorities() == null) {
      return false;
    }
    return getAuthorities()
        .stream()
        .anyMatch(a -> a.getBusinessObject().equalsIgnoreCase(object)
            && a.getBusinessOperation().equalsIgnoreCase(operation));
  }

  /**
   * Checks if user has the specified permission
   *
   * @param permission The Permission to test.
   * @return {@code true} if the Permission has been granted.
   */
  public boolean hasPermission(final Permission permission) {
    return permission != null && hasPermission(permission.object(), permission.operation());
  }
}
