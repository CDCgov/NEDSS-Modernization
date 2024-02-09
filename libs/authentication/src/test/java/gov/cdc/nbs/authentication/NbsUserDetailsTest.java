package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authorization.permission.Permission;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class NbsUserDetailsTest {

  @Test
  void has_permission() {
    NbsAuthority authority = new NbsAuthority("TestOperation", "TestObject");

    NbsUserDetails userDetails = NbsUserDetails.builder()
        .authorities(Collections.singleton(authority))
        .build();

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isTrue();
  }

  @Test
  void does_not_have_permission_for_object() {
    NbsAuthority authority = new NbsAuthority("TestOperation", "WrongObject");


    NbsUserDetails userDetails = NbsUserDetails.builder()
        .authorities(Collections.singleton(authority))
        .build();

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }

  @Test
  void does_not_have_permission_for_operation() {
    NbsAuthority authority = new NbsAuthority("WrongOperation", "TestObject");

    NbsUserDetails userDetails = NbsUserDetails.builder()
        .authorities(Collections.singleton(authority))
        .build();

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }
  @Test
  void does_not_have_permission_null() {
    NbsUserDetails userDetails = NbsUserDetails.builder().build();

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }
}
