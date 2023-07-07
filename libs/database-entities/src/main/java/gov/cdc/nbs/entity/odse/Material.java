package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Material {
    @Id
    @Column(name = "material_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "material_uid", nullable = false)
    private NBSEntity nbsEntity;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "cd", length = 50)
    private String cd;

    @Column(name = "cd_desc_txt", length = 100)
    private String cdDescTxt;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "effective_duration_amt", length = 20)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "form_cd", length = 20)
    private String formCd;

    @Column(name = "form_desc_txt", length = 100)
    private String formDescTxt;

    @Column(name = "handling_cd", length = 20)
    private String handlingCd;

    @Column(name = "handling_desc_txt", length = 100)
    private String handlingDescTxt;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "nm", length = 100)
    private String nm;

    @Column(name = "qty", length = 20)
    private String qty;

    @Column(name = "qty_unit_cd", length = 20)
    private String qtyUnitCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "risk_cd", length = 20)
    private String riskCd;

    @Column(name = "risk_desc_txt", length = 100)
    private String riskDescTxt;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

}
