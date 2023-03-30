package gov.cdc.nbs.identity;

import java.time.Instant;

public class MotherSettings {

  private final long starting;
  private final String suffix;
  private final long createedBy;
  private final Instant createdOn;

  public MotherSettings(long starting, String suffix, long createdBy) {
    this.starting = starting;
    this.suffix = suffix;
    this.createedBy = createdBy;
    this.createdOn = Instant.now();
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

  public Instant createdOn() {
    return createdOn;
  }
}
