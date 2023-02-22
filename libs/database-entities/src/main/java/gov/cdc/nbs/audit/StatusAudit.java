package gov.cdc.nbs.audit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public class StatusAudit {

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time", nullable = false)
    private Instant statusTime;

    protected StatusAudit() {
    }

    public StatusAudit(Character statusCd, Instant statusTime) {
        this.statusCd = statusCd;
        this.statusTime = statusTime;
    }

    public Character getStatusCd() {
        return statusCd;
    }

    public Instant getStatusTime() {
        return statusTime;
    }

}
