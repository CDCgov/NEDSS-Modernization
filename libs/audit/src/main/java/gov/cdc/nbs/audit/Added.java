package gov.cdc.nbs.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public class Added {

  @Column(name = "add_reason_cd", length = 20)
  private String addReasonCd;

  @Column(name = "add_time")
  private LocalDateTime addTime;

  @Column(name = "add_user_id")
  private Long addUserId;

  protected Added() {
  }

  public Added(final long who, final LocalDateTime when) {
    this(who, when, "Add");
  }

  public Added(final long who, final LocalDateTime when, final String why) {
    this.addReasonCd = why;
    this.addTime = when;
    this.addUserId = who;
  }

  public String reason() {
    return addReasonCd;
  }

  public LocalDateTime addedOn() {
    return addTime;
  }

  public Long addedBy() {
    return addUserId;
  }


}
