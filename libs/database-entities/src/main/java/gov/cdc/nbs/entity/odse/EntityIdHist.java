package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Entity_id_hist")
public class EntityIdHist {
    @EmbeddedId
    private EntityIdHistId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entity_uid", referencedColumnName = "entity_uid", nullable = false)
    @JoinColumn(name = "entity_id_seq", referencedColumnName = "entity_id_seq", nullable = false)
    private EntityId entity;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

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

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

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

}