# authorization

A utility library for the NEDSS Modernization project that provides
standardized [object/operation](../../documentation/authorization/NBS6-Authorization.md) permission resolution for
backend services.

The [PermissionScopeResolver](src/main/java/gov/cdc/nbs/authorization/permission/scope/PermissionScopeResolver.java) is
the entrypoint for the library which will resolve
the [PermissionScope](src/main/java/gov/cdc/nbs/authorization/permission/scope/PermissionScope.java) of
a [Permission](src/main/java/gov/cdc/nbs/authorization/permission/Permission.java) based on the currently logged-in
user.
