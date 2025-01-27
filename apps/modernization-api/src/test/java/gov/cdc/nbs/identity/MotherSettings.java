package gov.cdc.nbs.identity;

import java.time.LocalDateTime;

public class MotherSettings {

  private final long starting;
  private final String suffix;
  private final long createedBy;
  private final LocalDateTime createdOn;

  public MotherSettings(long starting, String suffix, long createdBy) {
    this.starting = starting;
    this.suffix = suffix;
    this.createedBy = createdBy;
    this.createdOn = LocalDateTime.now();
  }

  public long starting() {
    return starting;
  }

  public String suffix() {
    return suffix;
  }

  public long createdBy() {
    return createedBy;
  }

  public LocalDateTime createdOn() {
    return createdOn;
  }
}
