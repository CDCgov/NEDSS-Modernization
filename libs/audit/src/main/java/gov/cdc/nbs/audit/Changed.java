package gov.cdc.nbs.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class Changed {

  @Column(name = "last_chg_reason_cd", length = 20)
  private String lastChgReasonCd;

  @Column(name = "last_chg_time")
  private LocalDateTime lastChgTime;

  @Column(name = "last_chg_user_id")
  private Long lastChgUserId;

  protected Changed() {}

  public Changed(final long who, final LocalDateTime when, final String why) {
    this.lastChgUserId = who;
    this.lastChgTime = when;
    this.lastChgReasonCd = why;
  }

  public Changed(final long who, final LocalDateTime when) {
    this(who, when, null);
  }

  public String reason() {
    return lastChgReasonCd;
  }

  public LocalDateTime changedOn() {
    return lastChgTime;
  }

  public Long changedBy() {
    return lastChgUserId;
  }
}
