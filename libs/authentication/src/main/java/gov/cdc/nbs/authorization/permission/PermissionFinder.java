package gov.cdc.nbs.authorization.permission;

import java.util.Collection;

public interface PermissionFinder {
    Collection<Permission> find(long user);
}
