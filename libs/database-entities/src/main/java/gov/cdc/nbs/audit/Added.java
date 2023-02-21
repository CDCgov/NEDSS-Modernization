package gov.cdc.nbs.audit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public class Added {

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private long addUserId;

    protected Added() {
    }

    public Added(final long who, final Instant when) {
        this(who, when, "Add");
    }

    public Added(final long who, final Instant when, final String why) {
        this.addReasonCd = why;
        this.addTime = when;
        this.addUserId = who;
    }

    public String getAddReasonCd() {
        return addReasonCd;
    }

    public Instant getAddTime() {
        return addTime;
    }

    public Long getAddUserId() {
        return addUserId;
    }


}
