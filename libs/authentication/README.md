# authentication

A utility library for NEDSS Modernization applications that provides and adapter from the NBS Security Model to Spring
Security.


## Authorities

Authorities allow the use NBS6 role based permission with method level security provided with Spring Security. A user's
`Authorities` are derived from
the [Feature Level](../../documentation/authorization/NBS6-Authorization.md#feature-level) permission of a user.
Authorities are resolved from the roles that are granted to a user through associated permission sets and named by
concatenating the operation and object of the role.

For example, if a permission set allows a user to "View a Patient File" that user will have a role with an operations of
`VIEWWORKUP` and object of `PATIENT`. A service can then restrict a specific method be invoked only when a user is able
to "View a Patient file" by annotating the method with `@PreAuthorize("hasAuthority('VIEWWORKUP-PATIENT')")`
