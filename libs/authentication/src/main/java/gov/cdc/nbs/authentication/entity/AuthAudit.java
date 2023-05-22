package gov.cdc.nbs.authentication.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class AuthAudit {
    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status_cd", nullable = false, length = 20)
    private AuthRecordStatus recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

}
