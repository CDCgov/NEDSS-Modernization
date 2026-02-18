package gov.cdc.nbs.authentication.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AuthUserRoleTest {

  @Test
  void should_initialize_with_permission_set_for_user() {

    AuthUser user = new AuthUser();

    AuthPermSet permissionSet = new AuthPermSet();

    AuthUserRole role = new AuthUserRole(user, permissionSet);

    assertThat(role)
        .returns('T', AuthUserRole::forReadOnly)
        .returns(permissionSet, AuthUserRole::permissionSet)
        .returns(user, AuthUserRole::user);
  }
}
