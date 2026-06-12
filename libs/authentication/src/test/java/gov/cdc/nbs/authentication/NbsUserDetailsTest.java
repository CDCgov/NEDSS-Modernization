package gov.cdc.nbs.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.authorization.permission.Permission;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class NbsUserDetailsTest {

  @Test
  void has_permission() {

    NbsUserDetails userDetails =
        new NbsUserDetails(
            103L,
            "username",
            "first",
            "last",
            Set.of(new SimpleGrantedAuthority("TestOperation-TestObject")),
            true,
            1L);

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isTrue();
  }

  @Test
  void does_not_have_permission_for_object() {
    NbsUserDetails userDetails =
        new NbsUserDetails(
            103L,
            "username",
            "first",
            "last",
            Set.of(new SimpleGrantedAuthority("TestOperation-WrongObject")),
            true,
            1L);

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }

  @Test
  void does_not_have_permission_for_operation() {
    NbsUserDetails userDetails =
        new NbsUserDetails(
            103L,
            "username",
            "first",
            "last",
            Set.of(new SimpleGrantedAuthority("WrongOperation-TestObject")),
            true,
            1L);

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }

  @Test
  void does_not_have_permission_null() {
    NbsUserDetails userDetails =
        new NbsUserDetails(103L, "username", "first", "last", Set.of(), true, 1L);

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }
}
