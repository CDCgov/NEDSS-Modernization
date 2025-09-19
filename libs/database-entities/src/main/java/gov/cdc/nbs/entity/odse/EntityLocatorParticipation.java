package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientEntityLocatorHistoryListener;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Predicate;


@Entity
@Table(name = "Entity_locator_participation")
@SuppressWarnings(
    //  Bidirectional mappings require knowledge of each other
    "javaarchitecture:S7027"
)
@EntityListeners(PatientEntityLocatorHistoryListener.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class_cd", discriminatorType = DiscriminatorType.STRING)
public abstract class EntityLocatorParticipation implements Identifiable<EntityLocatorParticipationId> {

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
  protected String type;

  @Column(name = "cd_desc_txt", length = 100)
  private String cdDescTxt;

  @Column(name = "duration_amt", length = 20)
  private String durationAmt;

  @Column(name = "duration_unit_cd", length = 20)
  private String durationUnitCd;

  @Column(name = "from_time")
  private LocalDate fromTime;

  @Column(name = "locator_desc_txt", length = 2000)
  protected String comment;

  @Column(name = "to_time")
  private LocalDate toTime;

  @Column(name = "use_cd", length = 20)
  protected String use;

  @Column(name = "user_affiliation_txt", length = 20)
  private String userAffiliationTxt;

  @Column(name = "valid_time_txt", length = 100)
  private String validTimeTxt;

  @Version
  @Column(name = "version_ctrl_nbr")
  private short versionCtrlNbr;

  @Column(name = "as_of_date")
  protected LocalDate asOf;

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

  @Override
  public EntityLocatorParticipationId identifier() {
    return this.id;
  }

  public abstract Locator locator();

  public LocalDate asOf() {
    return this.asOf;
  }

  public String type() {
    return this.type;
  }

  public String use() {
    return use;
  }

  public String comments() {
    return this.comment;
  }

  public Audit audit() {
    return audit;
  }

  public RecordStatus recordStatus() {
    return recordStatus;
  }

  public Status status() {
    return status;
  }

  protected void changed(final PatientCommand command) {
    if (this.audit == null) {
      this.audit = new Audit(command.requester(), command.requestedOn());
    }
    this.audit.changed(command.requester(), command.requestedOn());
  }

  protected void inactivate(final PatientCommand command) {
    changed(command);
    this.recordStatus.inactivate(command.requestedOn());
    this.status.inactivate(command.requestedOn());
  }

}
