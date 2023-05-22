package gov.cdc.nbs.support;

import java.time.Instant;
import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthUser;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;

public class UserMother {
    public static AuthUser clerical() {
        var now = Instant.now();
        var audit = new AuthAudit();
        audit.setAddTime(now);
        audit.setAddUserId(999999999L);
        audit.setLastChgTime(now);
        audit.setLastChgUserId(999999999L);
        audit.setRecordStatusCd(AuthRecordStatus.ACTIVE);
        audit.setRecordStatusTime(now);
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
