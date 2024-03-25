package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.patient.PatientCommand;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;

@MappedSuperclass
public abstract class Locator {

    @Column(name = "add_reason_cd", length = 80)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    protected Locator() {

    }

    protected Locator(final PatientCommand command) {
        this.addUserId = command.requester();
        this.addTime = command.requestedOn();

        this.lastChgTime = command.requestedOn();
        this.lastChgUserId = command.requester();

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = command.requestedOn();
    }

    public String getRecordStatusCd() {
        return recordStatusCd;
    }

    public void setRecordStatusCd(String recordStatusCd) {
        this.recordStatusCd = recordStatusCd;
    }
}
