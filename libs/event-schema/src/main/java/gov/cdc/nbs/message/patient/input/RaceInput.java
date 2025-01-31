package gov.cdc.nbs.message.patient.input;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RaceInput {
  private long patient;
  private LocalDate asOf;
  private String category;
  private List<String> detailed;

  public RaceInput() {
    this.detailed = new ArrayList<>();
  }

  public long getPatient() {
    return patient;
  }

  public RaceInput setPatient(long patient) {
    this.patient = patient;
    return this;
  }

  public LocalDate getAsOf() {
    return asOf;
  }

  public RaceInput setAsOf(final LocalDate asOf) {
    this.asOf = asOf;
    return this;
  }

  public String getCategory() {
    return category;
  }

  public RaceInput setCategory(String category) {
    this.category = category;
    return this;
  }

  public List<String> getDetailed() {
    return detailed;
  }

  public RaceInput setDetailed(List<String> detailed) {
    this.detailed = detailed;
    return this;
  }

  public RaceInput withDetail(final String detail) {
    this.detailed.add(detail);
    return this;
  }

}
