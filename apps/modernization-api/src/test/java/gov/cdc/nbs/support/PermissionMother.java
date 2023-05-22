package gov.cdc.nbs.support;

import java.time.Instant;
import gov.cdc.nbs.authentication.entity.AuthAudit;
import gov.cdc.nbs.authentication.entity.AuthPermSet;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;

public class PermissionMother {
    public static Long SYSTEM_USER_ID = 10191001L;

    public static AuthPermSet clericalPermissionSet() {
        var now = Instant.now();
        var audit = new AuthAudit();
        audit.setAddTime(now);
        audit.setAddUserId(SYSTEM_USER_ID);
        audit.setLastChgTime(now);
        audit.setLastChgUserId(SYSTEM_USER_ID);
        audit.setRecordStatusCd(AuthRecordStatus.ACTIVE);
        audit.setRecordStatusTime(now);
        return AuthPermSet.builder()
                .id(9L)
                .permSetNm("NEDSS Clerical Data Entry")
                .permSetDesc("Enters data into the system")
                .sysDefinedPermSetInd('T')
                .audit(audit)
                .build();
    }
}
