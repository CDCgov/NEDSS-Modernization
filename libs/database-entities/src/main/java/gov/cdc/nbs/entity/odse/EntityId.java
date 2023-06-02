package gov.cdc.nbs.entity.odse;

import gov.cdc.nbs.audit.Audit;
import gov.cdc.nbs.patient.PatientCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "Entity_id")
public class EntityId {
    @EmbeddedId
    private EntityIdId id;

    @MapsId("entityUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_uid", nullable = false)
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
        final PatientCommand.AddIdentification added
    ) {
        this.nbsEntityUid = nbs;
        this.id = identifier;

        this.typeCd = added.identificationType();
        this.assigningAuthorityCd = added.assigningAuthority();
        this.rootExtensionTxt = added.identificationNumber();

        this.recordStatusCd = "ACTIVE";
        this.recordStatusTime = added.requestedOn();

        this.audit = new Audit(added.requester(), added.requestedOn());
    }

    public void update(final PatientCommand.UpdateIdentification info) {
        this.assigningAuthorityCd = info.assigningAuthority();
        this.rootExtensionTxt = info.identificationNumber();
        this.typeCd = info.identificationType();

        this.audit.changed(info.requester(), info.requestedOn(), "Identification Updated");
    }
}
