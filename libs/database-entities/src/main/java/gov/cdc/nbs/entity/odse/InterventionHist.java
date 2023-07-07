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
@Table(name = "Intervention_hist")
public class InterventionHist {
    @EmbeddedId
    private InterventionHistId id;

    @MapsId("interventionUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "intervention_uid", nullable = false)
    private Intervention interventionUid;

    @Column(name = "activity_duration_amt", length = 20)
    private String activityDurationAmt;

    @Column(name = "activity_duration_unit_cd", length = 20)
    private String activityDurationUnitCd;

    @Column(name = "activity_from_time")
    private Instant activityFromTime;

    @Column(name = "activity_to_time")
    private Instant activityToTime;

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

    @Column(name = "cd_system_cd", length = 20)
    private String cdSystemCd;

    @Column(name = "cd_system_desc_txt", length = 100)
    private String cdSystemDescTxt;

    @Column(name = "class_cd", length = 10)
    private String classCd;

    @Column(name = "confidentiality_cd", length = 20)
    private String confidentialityCd;

    @Column(name = "confidentiality_desc_txt", length = 100)
    private String confidentialityDescTxt;

    @Column(name = "effective_duration_amt", length = 20)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "method_cd", length = 20)
    private String methodCd;

    @Column(name = "method_desc_txt", length = 100)
    private String methodDescTxt;

    @Column(name = "priority_cd", length = 20)
    private String priorityCd;

    @Column(name = "priority_desc_txt", length = 100)
    private String priorityDescTxt;

    @Column(name = "prog_area_cd", length = 20)
    private String progAreaCd;

    @Column(name = "qty_amt", length = 20)
    private String qtyAmt;

    @Column(name = "qty_unit_cd", length = 20)
    private String qtyUnitCd;

    @Column(name = "reason_cd", length = 20)
    private String reasonCd;

    @Column(name = "reason_desc_txt", length = 100)
    private String reasonDescTxt;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "repeat_nbr")
    private Short repeatNbr;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "target_site_cd", length = 20)
    private String targetSiteCd;

    @Column(name = "target_site_desc_txt", length = 100)
    private String targetSiteDescTxt;

    @Column(name = "txt", length = 1000)
    private String txt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "shared_ind", nullable = false)
    private Character sharedInd;

    @Column(name = "electronic_ind")
    private Character electronicInd;

    @Column(name = "material_cd", length = 20)
    private String materialCd;

    @Column(name = "age_at_vacc")
    private Short ageAtVacc;

    @Column(name = "age_at_vacc_unit_cd", length = 20)
    private String ageAtVaccUnitCd;

    @Column(name = "vacc_mfgr_cd", length = 20)
    private String vaccMfgrCd;

    @Column(name = "material_lot_nm", length = 50)
    private String materialLotNm;

    @Column(name = "material_expiration_time")
    private Instant materialExpirationTime;

    @Column(name = "vacc_dose_nbr")
    private Short vaccDoseNbr;

    @Column(name = "vacc_info_source_cd", length = 20)
    private String vaccInfoSourceCd;

}
