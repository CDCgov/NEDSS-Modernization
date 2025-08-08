package gov.cdc.nbs.entity.odse;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


public class PatientRaceId implements Serializable {

  @Serial
  private static final long serialVersionUID = -8655697160777324427L;

  private long patient;

  private String race;

  protected PatientRaceId() {
  }

  public PatientRaceId(long patient, String race) {
    this.patient = patient;
    this.race = race;
  }

  public long getPatient() {
    return patient;
  }

  public String getRace() {
    return race;
  }

  @Override
  public boolean equals(final Object o) {
    return o instanceof PatientRaceId other && patient == other.patient && race.equals(other.race);
  }

  @Override
  public int hashCode() {
    return Objects.hash(patient, race);
  }

  @Override
  public String toString() {
    return "PersonRaceId{" +
        "patient=" + patient +
        ", race='" + race + '\'' +
        '}';
  }
}
