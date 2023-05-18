package gov.cdc.nbs.support;

import java.time.Instant;
import gov.cdc.nbs.authentication.AuthUser;
import gov.cdc.nbs.entity.enums.RecordStatus;

public class UserMother {
    public static AuthUser clerical() {
        var now = Instant.now();
        var user = new AuthUser();
        user.setUserId("test-clerical");
        user.setUserType("internalUser");
        user.setUserFirstNm("test");
        user.setUserLastNm("clerical");
        user.setMasterSecAdminInd('F');
        user.setProgAreaAdminInd('F');
        user.setNedssEntryId(10090000L);
        user.setAddTime(now);
        user.setLastChgUserId(999999999L);
        user.setLastChgTime(now);
        user.setAddUserId(999999999L);
        user.setRecordStatusCd(RecordStatus.ACTIVE);
        user.setRecordStatusTime(now);
        return user;
    }
}
