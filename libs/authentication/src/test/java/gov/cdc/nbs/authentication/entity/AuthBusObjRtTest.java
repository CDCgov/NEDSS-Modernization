package gov.cdc.nbs.authentication.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class AuthBusObjRtTest {

  @Test
  void should_initialize_with_audit_from_set() {

    AuthPermSet set =
        new AuthPermSet()
            .audit(new AuthAudit(new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z"))));

    AuthBusObjType objectType = new AuthBusObjType();
    AuthBusOpType operationType = new AuthBusOpType();

    AuthBusObjRt actual = new AuthBusObjRt(set, objectType, operationType);

    assertThat(actual.audit())
        .returns(1139L, AuthAudit::addedBy)
        .returns(Instant.parse("2023-07-14T01:02:03Z"), AuthAudit::addedOn);
  }

  @Test
  void should_initialize_with_created_operation_right() {

    AuthPermSet set =
        new AuthPermSet()
            .audit(new AuthAudit(new AuthAudit(1139L, Instant.parse("2023-07-14T01:02:03Z"))));

    AuthBusObjType objectType = new AuthBusObjType();
    AuthBusOpType operationType = new AuthBusOpType();

    AuthBusObjRt objectRight = new AuthBusObjRt(set, objectType, operationType);

    assertThat(objectRight.operationRight())
        .satisfies(actual -> assertThat(actual.objectRight()).isSameAs(objectRight))
        .satisfies(actual -> assertThat(actual.operationType()).isSameAs(operationType));
  }
}
