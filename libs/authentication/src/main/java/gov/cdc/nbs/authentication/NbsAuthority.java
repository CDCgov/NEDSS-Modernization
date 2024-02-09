package gov.cdc.nbs.authentication;

import org.springframework.security.core.GrantedAuthority;

/**
 * Used in place of default
 * {@link org.springframework.security.core.authority.SimpleGrantedAuthority#SimpleGrantedAuthority
 * SimpleGrantedAuthority}. The expanded functionality allows us to capture relevant security information such as
 * program area and jurisdiction
 */
public class NbsAuthority implements GrantedAuthority {
  private final String businessOperation;
  private final String businessObject;
  private final String programArea;
  private final String authority;

  public NbsAuthority(final String operation, final String object) {
    this(operation, object, null, operation + '-' + object);
  }

  public NbsAuthority(
      final String businessOperation,
      final String businessObject,
      final String programArea,
      final String authority
  ) {
    this.businessOperation = businessOperation;
    this.businessObject = businessObject;
    this.programArea = programArea;
    this.authority = authority;
  }

  public String operation() {
    return this.businessOperation;
  }

  public String object() {
    return this.businessObject;
  }

  public String programArea() {
    return this.programArea;
  }

  @Override
  public String getAuthority() {
    return this.authority;
  }
}
