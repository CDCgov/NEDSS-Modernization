package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.audit.RecordStatus;
import gov.cdc.nbs.audit.Status;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientIdentificationHistoryListener;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.LocalDate;
import java.util.function.Predicate;


@Getter
@Entity
@Table(name = "Entity_id")
@EntityListeners(PatientIdentificationHistoryListener.class)
public class EntityId implements Identifiable<EntityIdId> {

  public static Predicate<EntityId> active() {
    return input -> input.recordStatus.isActive();
  }

  @EmbeddedId
  private EntityIdId id;

  @MapsId("entityUid")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "entity_uid", nullable = false)
  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  private NBSEntity nbsEntityUid;

  @Column(name = "assigning_authority_cd", length = 199)
  private String assigningAuthorityCd;

  @Column(name = "root_extension_txt", length = 100)
  private String rootExtensionTxt;

  @Column(name = "type_cd", length = 50)
  private String typeCd;

  @Column(name = "as_of_date")
  private LocalDate asOfDate;

  @Embedded
  private Audit audit;

  @Embedded
  private RecordStatus recordStatus;

  @Embedded
  private Status status;

  protected EntityId() {

  }

  public EntityId(
      final NBSEntity nbs,
      final EntityIdId identifier,
      final PatientCommand.AddIdentification added
  ) {
    this.nbsEntityUid = nbs;
    this.id = identifier;

    this.asOfDate = added.asOf();
    this.typeCd = added.identificationType();
    this.assigningAuthorityCd = added.assigningAuthority();
    this.rootExtensionTxt = added.identificationNumber();

    this.status = new Status(added.requestedOn());
    this.recordStatus = new RecordStatus(added.requestedOn());

    this.audit = new Audit(added.requester(), added.requestedOn());
  }

  public void update(final PatientCommand.UpdateIdentification info) {
    this.asOfDate = info.asOf();
    this.assigningAuthorityCd = info.assigningAuthority();
    this.rootExtensionTxt = info.identificationNumber();
    this.typeCd = info.identificationType();

    this.audit.changed(info.requester(), info.requestedOn());
  }

  public void delete(final PatientCommand.DeleteIdentification deleted) {
    this.recordStatus.inactivate(deleted.requestedOn());

    this.audit.changed(deleted.requester(), deleted.requestedOn());
  }

  @Override
  public EntityIdId identifier() {
    return this.id;
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
}
