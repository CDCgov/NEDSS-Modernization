package gov.cdc.nbs.support;

import java.time.Instant;

import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.entity.odse.AuthPermSet;

public class PermissionMother {
    public static Long SYSTEM_USER_ID = 10191001L;

    public static AuthPermSet clericalPermissionSet() {
        var now = Instant.now();
        return AuthPermSet.builder()
                .id(9L)
                .permSetNm("NEDSS Clerical Data Entry")
                .permSetDesc("Enters data into the system")
                .sysDefinedPermSetInd('T')
                .addTime(now)
                .addUserId(SYSTEM_USER_ID)
                .lastChgTime(now)
                .lastChgUserId(SYSTEM_USER_ID)
                .recordStatusCd(RecordStatus.ACTIVE)
                .recordStatusTime(now)
                .build();
    }
}
