package gov.cdc.nbs.authentication;

import gov.cdc.nbs.authorization.permission.Permission;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class NbsUserDetailsTest {

  @Test
  void has_permission() {
    NbsAuthority authority = NbsAuthority.builder()
        .businessObject("TestObject")
        .businessOperation("TestOperation")
        .build();
    NbsUserDetails userDetails = NbsUserDetails.builder()
        .authorities(Collections.singleton(authority))
        .build();

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isTrue();
  }

  @Test
  void does_not_have_permission() {
    NbsAuthority authority = NbsAuthority.builder()
        .businessObject("Wrong Permission")
        .businessOperation("TestOperation")
        .build();
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
