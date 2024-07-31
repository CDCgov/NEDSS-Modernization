package gov.cdc.nbs.authorization.permission.scope;

import gov.cdc.nbs.authorization.permission.Permission;

import java.util.Optional;

public interface PermissionScopeFinder {
    Optional<PermissionScope> find(String user, Permission permission);
}
