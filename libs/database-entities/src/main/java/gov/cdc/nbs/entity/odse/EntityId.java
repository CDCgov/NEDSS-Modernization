package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.entity.enums.RecordStatus;
import gov.cdc.nbs.patient.PatientCommand;
import gov.cdc.nbs.patient.PatientIdentificationHistoryListener;
import lombok.AllArgsConstructor;
import lombok.Getter;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "Entity_id")
@SuppressWarnings(
    //  The PatientIdentificationHistoryListener is an entity listener specifically for instances of this class
    {"javaarchitecture:S7027", "javaarchitecture:S7091"}
)
@EntityListeners(PatientIdentificationHistoryListener.class)
public class EntityId {

    public static Predicate<EntityId> active() {
        return input -> Objects.equals(input.getRecordStatusCd(), RecordStatus.ACTIVE.name());
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

    @Column(name = "assigning_authority_desc_txt", length = 100)
    private String assigningAuthorityDescTxt;

    @Column(name = "duration_amt", length = 20)
    private String durationAmt;

    @Column(name = "duration_unit_cd", length = 20)
    private String durationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "root_extension_txt", length = 100)
    private String rootExtensionTxt;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "type_cd", length = 50)
    private String typeCd;

    @Column(name = "type_desc_txt", length = 100)
    private String typeDescTxt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "valid_from_time")
    private Instant validFromTime;

    @Column(name = "valid_to_time")
    private Instant validToTime;

    @Column(name = "as_of_date")
    private Instant asOfDate;

    @Column(name = "assigning_authority_id_type", length = 50)
    private String assigningAuthorityIdType;

    @Embedded
    private Audit audit;

    protected EntityId() {

    }

    public EntityId(
            final NBSEntity nbs,
            final EntityIdId identifier,
            final PatientCommand.AddIdentification added) {
        this.nbsEntityUid = nbs;
        this.id = identifier;

        this.asOfDate = added.asOf();
        this.typeCd = added.identificationType();
        this.assigningAuthorityCd = added.assigningAuthority();
        this.rootExtensionTxt = added.identificationNumber();

        this.statusCd = 'A';
        this.statusTime = added.requestedOn();

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = added.requestedOn();

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
        this.recordStatusCd = RecordStatus.INACTIVE.name();
        this.recordStatusTime = deleted.requestedOn();

        this.audit.changed(deleted.requester(), deleted.requestedOn());
    }
}
