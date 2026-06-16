package gov.cdc.nbs.authentication;

import static org.assertj.core.api.Assertions.assertThat;

import gov.cdc.nbs.authorization.permission.Permission;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class NbsUserDetailsTest {

  @Test
  void should_return_correct_user_properties() {
    NbsUserDetails userDetails =
        new NbsUserDetails(103L, "username", "first", "last", Set.of(), true, 1L);

    assertThat(userDetails.getId()).isEqualTo(103L);
    assertThat(userDetails.getUsername()).isEqualTo("username");
    assertThat(userDetails.getFirstName()).isEqualTo("first");
    assertThat(userDetails.getLastName()).isEqualTo("last");
    assertThat(userDetails.getExternalOrgUid()).isEqualTo(1L);
  }

  @Test
  void should_return_correct_security_status_when_enabled() {
    NbsUserDetails userDetails =
        new NbsUserDetails(103L, "username", "first", "last", Set.of(), true, 1L);

    assertThat(userDetails.getPassword()).isNull();
    assertThat(userDetails.isEnabled()).isTrue();
    assertThat(userDetails.isAccountNonExpired()).isTrue();
    assertThat(userDetails.isAccountNonLocked()).isTrue();
    assertThat(userDetails.isCredentialsNonExpired()).isTrue();
  }

  @Test
  void should_return_correct_security_status_when_disabled() {
    NbsUserDetails userDetails =
        new NbsUserDetails(103L, "username", "first", "last", Set.of(), false, 1L);

    assertThat(userDetails.isEnabled()).isFalse();
    assertThat(userDetails.isAccountNonExpired()).isFalse();
    assertThat(userDetails.isAccountNonLocked()).isFalse();
    assertThat(userDetails.isCredentialsNonExpired()).isFalse();
  }

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
  void does_not_have_permission_when_authorities_empty() {
    NbsUserDetails userDetails =
        new NbsUserDetails(103L, "username", "first", "last", Set.of(), true, 1L);

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }

  @Test
  void does_not_have_permission_when_authorities_null() {
    NbsUserDetails userDetails =
        new NbsUserDetails(103L, "username", "first", "last", null, true, 1L);

    assertThat(userDetails.hasPermission(new Permission("TestOperation", "TestObject"))).isFalse();
  }

  @Test
  void does_not_have_permission_when_permission_is_null() {
    NbsUserDetails userDetails =
        new NbsUserDetails(103L, "username", "first", "last", Set.of(), true, 1L);

    assertThat(userDetails.hasPermission(null)).isFalse();
  }
}
