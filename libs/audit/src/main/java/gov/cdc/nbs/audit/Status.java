package gov.cdc.nbs.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Status {

  @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time", nullable = false)
  private LocalDateTime statusTime;

  public Status() {
  }

  public Status(final LocalDateTime applied) {
    this('A', applied);
  }

  public Status(final Character statusCd, final LocalDateTime statusTime) {
    this.statusCd = statusCd;
    this.statusTime = statusTime;
  }

  public Character status() {
    return statusCd;
  }

  public LocalDateTime appliedOn() {
    return statusTime;
  }

}
