package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientEntityLocatorHistoryListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Entity_locator_participation")
@SuppressWarnings(
    //  The PatientEntityLocatorHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027", "javaarchitecture:S7091"}
)
@EntityListeners(PatientEntityLocatorHistoryListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_cd", discriminatorType = DiscriminatorType.STRING)
public abstract class EntityLocatorParticipation {

  public static <V extends EntityLocatorParticipation> Predicate<V> active() {
    return input -> input.recordStatus().isActive();
  }

  public static <V extends EntityLocatorParticipation> Predicate<V> withUse(final String value) {
    return participation -> Objects.equals(participation.use(), value);
  }

  @EmbeddedId
  private EntityLocatorParticipationId id;

  @MapsId("entityUid")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "entity_uid", nullable = false)
  private NBSEntity nbsEntity;

  @Column(name = "cd", length = 50)
  protected String cd;

  @Column(name = "cd_desc_txt", length = 100)
  private String cdDescTxt;

  @Column(name = "duration_amt", length = 20)
  private String durationAmt;

  @Column(name = "duration_unit_cd", length = 20)
  private String durationUnitCd;

  @Column(name = "from_time")
  private LocalDate fromTime;

  @Column(name = "locator_desc_txt", length = 2000)
  protected String locatorDescTxt;

  @Column(name = "to_time")
  private LocalDate toTime;

  @Column(name = "use_cd", length = 20)
  protected String useCd;

  @Column(name = "user_affiliation_txt", length = 20)
  private String userAffiliationTxt;

  @Column(name = "valid_time_txt", length = 100)
  private String validTimeTxt;

  @Version
  @Column(name = "version_ctrl_nbr")
  private short versionCtrlNbr;

  @Column(name = "as_of_date")
  protected LocalDate asOfDate;

  @Embedded
  private Audit audit;

  @Embedded
  protected RecordStatus recordStatus;

  @Embedded
  protected Status status;

  protected EntityLocatorParticipation() {
  }

  protected EntityLocatorParticipation(
      final PatientCommand command,
      final NBSEntity nbs,
      final EntityLocatorParticipationId identifier
  ) {
    this.id = identifier;
    this.nbsEntity = nbs;

    this.status = new Status(command.requestedOn());
    this.recordStatus = new RecordStatus(command.requestedOn());
    this.audit = new Audit(command.requester(), command.requestedOn());
  }

  public long identifier() {
    return this.id.getLocatorUid();
  }

  public abstract Locator getLocator();

  public abstract String getClassCd();

  public String use() {
    return useCd;
  }

  public Audit audit() {
    return audit;
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }

  protected void changed(final PatientCommand command) {
    this.audit.changed(command.requester(), command.requestedOn());
  }

}
