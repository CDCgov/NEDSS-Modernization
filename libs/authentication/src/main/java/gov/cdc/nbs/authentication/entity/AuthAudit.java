package gov.cdc.nbs.authentication.entity;

import gov.cdc.nbs.authentication.enums.AuthRecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.Instant;

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

  AuthAudit() {}

  public AuthAudit(final long who, final Instant when) {
    this();
    this.addUserId = who;
    this.addTime = when;
    this.lastChgUserId = who;
    this.lastChgTime = when;
    this.recordStatusCd = AuthRecordStatus.ACTIVE;
    this.recordStatusTime = when;
  }

  public AuthAudit(final AuthAudit audit) {
    this();
    this.addUserId = audit.addUserId;
    this.addTime = audit.addTime;
    this.lastChgUserId = audit.lastChgUserId;
    this.lastChgTime = audit.lastChgTime;
    this.recordStatusCd = audit.recordStatusCd;
    this.recordStatusTime = audit.recordStatusTime;
  }

  public Instant addedOn() {
    return addTime;
  }

  public Long addedBy() {
    return addUserId;
  }

  public Instant changedOn() {
    return lastChgTime;
  }

  public Long changedBy() {
    return lastChgUserId;
  }

  public AuthRecordStatus recordStatus() {
    return recordStatusCd;
  }

  public AuthAudit inactivate(final Instant when) {
    this.recordStatusCd = AuthRecordStatus.INACTIVE;
    this.recordStatusTime = when;
    return this;
  }

  public Instant recordStatusChangedOn() {
    return recordStatusTime;
  }
}
