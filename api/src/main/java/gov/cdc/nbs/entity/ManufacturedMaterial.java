package gov.cdc.nbs.entity;

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
@Table(name = "Manufactured_material")
public class ManufacturedMaterial {
    @EmbeddedId
    private ManufacturedMaterialId id;

    @MapsId("materialUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_uid", nullable = false)
    private Material materialUid;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "expiration_time")
    private Instant expirationTime;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "lot_nm", length = 50)
    private String lotNm;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "stability_from_time")
    private Instant stabilityFromTime;

    @Column(name = "stability_to_time")
    private Instant stabilityToTime;

    @Column(name = "stability_duration_cd", length = 20)
    private String stabilityDurationCd;

    @Column(name = "stability_duration_unit_cd", length = 20)
    private String stabilityDurationUnitCd;

}