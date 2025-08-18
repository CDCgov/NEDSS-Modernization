package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class PatientAdministrativeInformation {

  @Column(name = "as_of_date_admin")
  private LocalDate asOf;

  @Column(name = "description", length = 2000)
  private String comments;

  public PatientAdministrativeInformation() {
  }

  public void update(final PatientCommand.UpdateAdministrativeInfo info) {
    this.asOf = info.asOf();
    this.comments = info.comment();

  }

  public void clear(final PatientCommand.ClearAdministrativeInformation command) {
    this.asOf = null;
    this.comments = null;
  }

  public LocalDate asOf() {
    return asOf;
  }

  public String comments() {
    return comments;
  }

  public long signature() {
    return Objects.hash(asOf, comments);
  }


}
