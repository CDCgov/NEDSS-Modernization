package gov.cdc.nbs.support;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import java.time.Instant;

public class PermissionMother {
  public static final Long SYSTEM_USER_ID = 10191001L;

  public static AuthPermSet clericalPermissionSet() {
    var now = Instant.now();
    var audit = new AuthAudit(SYSTEM_USER_ID, now);
    return new AuthPermSet()
        .name("NEDSS Clerical Data Entry")
        .description("Enters data into the system")
        .systemDefined('T')
        .audit(audit);
  }
}
