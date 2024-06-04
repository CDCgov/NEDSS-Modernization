package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authorization.permission.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public class NbsUserDetails implements UserDetails {
  private final Long id;
  private final String username;
  private final String firstName;
  private final String lastName;
  private final Set<GrantedAuthority> authorities;
  private final boolean isEnabled;

  public NbsUserDetails(
      final long id,
      final String username,
      final String firstName,
      final String lastName,
      final Set<GrantedAuthority> authorities,
      boolean isEnabled
  ) {
    this.id = id;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.authorities = authorities;
    this.isEnabled = isEnabled;
  }

  public Long getId() {
    return this.id;
  }

  public String getUsername() {
    return this.username;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public Set<GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public boolean isEnabled() {
    return this.isEnabled;
  }
  @Override
  public String getPassword() {
    return null;
  }

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
  private boolean hasPermission(
      final String operation,
      final String object
  ) {
    if (getAuthorities() == null) {
      return false;
    }

    String authority = operation + "-" + object;

    return getAuthorities()
        .stream()
        .anyMatch(found -> found.getAuthority().equalsIgnoreCase(authority));
  }

  /**
   * Checks if user has the specified permission
   *
   * @param permission The Permission to test.
   * @return {@code true} if the Permission has been granted.
   */
  public boolean hasPermission(final Permission permission) {
    return permission != null
        && hasPermission(permission.operation(), permission.object());
  }

}
