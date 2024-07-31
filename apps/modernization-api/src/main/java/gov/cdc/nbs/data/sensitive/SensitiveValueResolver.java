package gov.cdc.nbs.data.sensitive;

import gov.cdc.nbs.authorization.permission.Permission;
import gov.cdc.nbs.authorization.permission.scope.PermissionScope;
import gov.cdc.nbs.authorization.permission.scope.PermissionScopeResolver;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class SensitiveValueResolver {

  private static final String DEFAULT_REASON = "Insufficient permissions";
  private final PermissionScopeResolver resolver;

  public SensitiveValueResolver(final PermissionScopeResolver resolver) {
    this.resolver = resolver;
  }

  public <V> Sensitive resolve(final Permission permission, final V value) {
    return resolve(permission, value, () -> DEFAULT_REASON);
  }

  public <V> Sensitive resolve(
      final Permission permission,
      final V value,
      final Supplier<String> ifRestricted
  ) {
    PermissionScope scope = resolver.resolve(permission);

    return scope.allowed()
        ? new Sensitive.Allowed<>(value)
        : new Sensitive.Restricted(ifRestricted.get());
  }
}
