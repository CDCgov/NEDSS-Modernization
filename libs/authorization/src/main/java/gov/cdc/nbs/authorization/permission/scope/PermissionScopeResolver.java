package gov.cdc.nbs.authorization.permission.scope;

import gov.cdc.nbs.authorization.permission.Permission;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PermissionScopeResolver {

  private final PermissionScopeFinder finder;

  public PermissionScopeResolver(final PermissionScopeFinder finder) {
    this.finder = finder;
  }

  public PermissionScope resolve(final Permission permission) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    return this.finder.find(username, permission).orElse(PermissionScope.none());
  }
}
