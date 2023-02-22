package gov.cdc.nbs.audit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public class RecordStatusAudit {


    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;


    protected RecordStatusAudit() {
    }

    public RecordStatusAudit(final Instant when) {
        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = when;
    }
    public String getRecordStatusCd() {
        return recordStatusCd;
    }

    public Instant getRecordStatusTime() {
        return recordStatusTime;
    }

}
