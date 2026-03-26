package gov.cdc.nbs.authentication.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class AuthPermSetTest {

  @Test
  void should_not_initialize_with_rights() {
    AuthPermSet permissionSet =
        new AuthPermSet().audit(new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z")));

    assertThat(permissionSet.objectRights()).isEmpty();
  }

  @Test
  void should_add_objectRight() {

    AuthBusOpType operationType = new AuthBusOpType();
    AuthBusObjType objectType = new AuthBusObjType();

    AuthPermSet permissionSet =
        new AuthPermSet().audit(new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z")));

    AuthBusObjRt objectRight = permissionSet.addObjectRight(operationType, objectType);

    assertThat(objectRight)
        .satisfies(actual -> assertThat(actual.permissionSet()).isSameAs(permissionSet))
        .satisfies(actual -> assertThat(actual.objectType()).isSameAs(objectType));

    assertThat(permissionSet.objectRights()).contains(objectRight);
  }

  @Test
  void should_add_a_second_objectRight() {

    AuthPermSet permissionSet =
        new AuthPermSet().audit(new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z")));

    AuthBusObjRt initial = permissionSet.addObjectRight(new AuthBusOpType(), new AuthBusObjType());

    AuthBusOpType operationType = new AuthBusOpType();
    AuthBusObjType objectType = new AuthBusObjType();

    AuthBusObjRt objectRight = permissionSet.addObjectRight(operationType, objectType);

    assertThat(objectRight)
        .satisfies(actual -> assertThat(actual.permissionSet()).isSameAs(permissionSet))
        .satisfies(actual -> assertThat(actual.objectType()).isSameAs(objectType));

    assertThat(permissionSet.objectRights()).contains(initial, objectRight);
  }
}
