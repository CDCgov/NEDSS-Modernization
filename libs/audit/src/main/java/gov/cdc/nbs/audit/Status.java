package gov.cdc.nbs.audit;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
public class Status {

  @Column(name = "status_cd", nullable = false)
  private Character statusCd;

  @Column(name = "status_time", nullable = false)
  private Instant statusTime;

  protected Status() {
  }

  public Status(final Instant applied) {
    this('A', applied);
  }

  public Status(final Character statusCd, final Instant statusTime) {
    this.statusCd = statusCd;
    this.statusTime = statusTime;
  }

  public Character status() {
    return statusCd;
  }

  public Instant appliedOn() {
    return statusTime;
  }

}
