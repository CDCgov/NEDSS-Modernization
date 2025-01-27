package gov.cdc.nbs.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.function.Predicate;

@Embeddable
public class RecordStatus {

  private static final String ACTIVE_STATUS = "ACTIVE";

  public static Predicate<RecordStatus> active() {
    return RecordStatus::isActive;
  }

  @Column(name = "record_status_cd", length = 20)
  private String status;

  @Column(name = "record_status_time")
  private LocalDateTime appliedOn;


  public RecordStatus() {
    this.status = ACTIVE_STATUS;
  }

  public RecordStatus(final LocalDateTime when) {
    this();
    this.appliedOn = when;
  }

  public void inactivate(final LocalDateTime when) {
    change("INACTIVE", when);
  }

  public void change(final String status, final LocalDateTime when) {
    this.status = status;
    this.appliedOn = when;
  }

  public String status() {
    return status;
  }

  public boolean isActive() {
    return status.equals(ACTIVE_STATUS);
  }

  public LocalDateTime appliedOn() {
    return appliedOn;
  }

}
