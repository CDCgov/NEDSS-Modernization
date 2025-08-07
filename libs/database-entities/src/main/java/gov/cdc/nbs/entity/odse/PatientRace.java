package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientRaceHistoryListener;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Person_race")
@IdClass(PatientRaceId.class)
@SuppressWarnings(
    //  The PatientRaceHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027", "javaarchitecture:S7091"}
)
@EntityListeners(PatientRaceHistoryListener.class)
public class PatientRace {

  @Id
  @Column(name = "race_cd", nullable = false, length = 20)
  private String race;

  @Id
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "person_uid", nullable = false)
  private Person patient;

  @Column(name = "race_category_cd", length = 20)
  private String category;

  @Column(name = "as_of_date")
  private LocalDate asOfDate;

  @Embedded
  private Audit audit;

  @Embedded
  private RecordStatus recordStatus;

  protected PatientRace() {

  }

  public PatientRace(
      final Person person,
      final PatientCommand.AddRaceCategory added
  ) {
    this.patient = person;
    this.asOfDate = added.asOf();
    this.category = added.category();
    this.race = added.category();

    this.recordStatus = new RecordStatus(added.requestedOn());
    this.audit = new Audit(added.requester(), added.requestedOn());

  }

  public PatientRace(
      final Person person,
      final PatientCommand.AddDetailedRace added
  ) {
    this.patient = person;
    this.asOfDate = added.asOf();
    this.category = added.category();
    this.race = added.race();

    this.recordStatus = new RecordStatus(added.requestedOn());
    this.audit = new Audit(added.requester(), added.requestedOn());

  }

  public PatientRace update(final PatientCommand.UpdateRaceInfo changed) {
    this.asOfDate = changed.asOf();

    changed(changed);
    return this;
  }

  public long patient() {
    return patient.id();
  }

  public LocalDate asOf() {
    return asOfDate;
  }

  public String category() {
    return this.category;
  }

  public String detail() {
    return this.race;
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }

  public Audit audit() {
    return audit;
  }

  private void changed(final PatientCommand command) {
    if (audit == null) {
      this.audit = new Audit();
    }
    audit.changed(command.requester(), command.requestedOn());
  }

  @Override
  public boolean equals(final Object o) {
    return o instanceof PatientRace other && this.race.equals(other.race);
  }

  @Override
  public int hashCode() {
    return race.hashCode();
  }

  @Override
  public String toString() {
    return "PatientRace{" +
        "asOfDate=" + asOfDate +
        ", category='" + category + '\'' +
        ", race='" + race + '\'' +
        '}';
  }
}
