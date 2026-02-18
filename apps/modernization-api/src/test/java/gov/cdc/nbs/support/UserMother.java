package gov.cdc.nbs.support;

import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthUser;
import java.time.Instant;

public class UserMother {
  public static AuthUser clerical() {
    var now = Instant.now();
    var audit = new AuthAudit(999999999L, now);

    var user = new AuthUser();
    user.setUserId("test-clerical");
    user.setUserType("internalUser");
    user.setUserFirstNm("test");
    user.setUserLastNm("clerical");
    user.setMasterSecAdminInd('F');
    user.setProgAreaAdminInd('F');
    user.setNedssEntryId(10090000L);
    user.setAudit(audit);
    return user;
  }
}
