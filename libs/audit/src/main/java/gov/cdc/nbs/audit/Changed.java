package gov.cdc.nbs.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public class Changed {

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    protected Changed() {
    }

    public Changed(final long who, final Instant when, final String why) {
        this.lastChgUserId = who;
        this.lastChgTime = when;
        this.lastChgReasonCd = why;
    }

    public Changed(final long who, final Instant when) {
        this(who, when, null);
    }

    public String reason() {
        return lastChgReasonCd;
    }

    public Instant changedOn() {
        return lastChgTime;
    }

    public Long changedBy() {
        return lastChgUserId;
    }

}
