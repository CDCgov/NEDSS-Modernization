package gov.cdc.nbs.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Status {

  private static final char ACTIVE_CODE = 'A';
  private static final char INACTIVE_CODE = 'I';

  @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time", nullable = false)
  private LocalDateTime statusTime;

  public Status() {
  }

  public Status(final LocalDateTime applied) {
    this(ACTIVE_CODE, applied);
  }

  public Status(final Character statusCd, final LocalDateTime statusTime) {
    this.statusCd = statusCd;
    this.statusTime = statusTime;
  }

  public void inactivate(final LocalDateTime when) {
    this.statusCd = INACTIVE_CODE;
    this.statusTime = when;
  }

  public Character status() {
    return statusCd;
  }

  public LocalDateTime appliedOn() {
    return statusTime;
  }



}
