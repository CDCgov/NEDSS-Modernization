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

    public Character getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(Character statusCd) {
        this.statusCd = statusCd;
    }

    public Instant getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Instant statusTime) {
        this.statusTime = statusTime;
    }
}
