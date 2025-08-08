package gov.cdc.nbs.patient.demographic;

import gov.cdc.nbs.message.enums.Deceased;
import gov.cdc.nbs.patient.PatientCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class PatientMortality {

  @Column(name = "as_of_date_morbidity")
  private LocalDate asOf;

  @Enumerated(EnumType.STRING)
  @Column(name = "deceased_ind_cd", length = 20)
  private Deceased deceased;

  @Column(name = "deceased_time")
  private LocalDate deceasedOn;

  public PatientMortality() {
  }

  public PatientMortality(final PatientCommand.AddPatient patient) {
    this.asOf = patient.asOf();
    this.deceased = patient.deceased();
    this.deceasedOn = patient.deceasedTime();

  }

  public void update(
      final PatientCommand.UpdateMortality info
  ) {
    this.asOf = info.asOf();
    this.deceased = Deceased.resolve(info.deceased());

    if (Objects.equals(Deceased.Y, this.deceased)) {
      this.deceasedOn = info.deceasedOn();
    } else {
      this.deceasedOn = null;
    }
  }

  public LocalDate asOf() {
    return asOf;
  }

  public Deceased deceased() {
    return deceased;
  }

  public LocalDate deceasedOn() {
    return deceasedOn;
  }

  public long signature() {
    return Objects.hash(asOf,deceased, deceasedOn);
  }
}
