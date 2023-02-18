package gov.cdc.nbs.patientlistener.odse;

import java.time.Instant;
import java.util.List;

/*
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;*/

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Observation {
    @Id
    @Column(name = "observation_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "observation_uid", nullable = false)
    private Act act;

    @OneToMany(mappedBy = "id.observationUid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ObsValueCoded> obsValueCodedList;

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

    @Column(name = "cd_desc_txt", length = 1000)
    private String cdDescTxt;

    @Column(name = "cd_system_cd", length = 300)
    private String cdSystemCd;

    @Column(name = "cd_system_desc_txt", length = 100)
    private String cdSystemDescTxt;

    @Column(name = "confidentiality_cd", length = 20)
    private String confidentialityCd;

    @Column(name = "confidentiality_desc_txt", length = 100)
    private String confidentialityDescTxt;

    @Column(name = "ctrl_cd_display_form", length = 20)
    private String ctrlCdDisplayForm;

    @Column(name = "ctrl_cd_user_defined_1", length = 20)
    private String ctrlCdUserDefined1;

    @Column(name = "ctrl_cd_user_defined_2", length = 20)
    private String ctrlCdUserDefined2;

    @Column(name = "ctrl_cd_user_defined_3", length = 20)
    private String ctrlCdUserDefined3;

    @Column(name = "ctrl_cd_user_defined_4", length = 20)
    private String ctrlCdUserDefined4;

    @Column(name = "derivation_exp")
    private Short derivationExp;

    @Column(name = "effective_duration_amt", length = 20)
    private String effectiveDurationAmt;

    @Column(name = "effective_duration_unit_cd", length = 20)
    private String effectiveDurationUnitCd;

    @Column(name = "effective_from_time")
    private Instant effectiveFromTime;

    @Column(name = "effective_to_time")
    private Instant effectiveToTime;

    @Column(name = "electronic_ind")
    private Character electronicInd;

    @Column(name = "group_level_cd", length = 10)
    private String groupLevelCd;

    @Column(name = "jurisdiction_cd", length = 20)
    private String jurisdictionCd;

    @Column(name = "lab_condition_cd", length = 20)
    private String labConditionCd;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "method_cd", length = 2000)
    private String methodCd;

    @Column(name = "method_desc_txt", length = 2000)
    private String methodDescTxt;

    @Column(name = "obs_domain_cd", length = 20)
    private String obsDomainCd;

    @Column(name = "obs_domain_cd_st_1", length = 20)
    private String obsDomainCdSt1;

    @Column(name = "pnu_cd")
    private Character pnuCd;

    @Column(name = "priority_cd", length = 20)
    private String priorityCd;

    @Column(name = "priority_desc_txt", length = 100)
    private String priorityDescTxt;

    @Column(name = "prog_area_cd", length = 20)
    private String progAreaCd;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_person_uid")
    private Person subjectPersonUid;

    @Column(name = "target_site_cd", length = 20)
    private String targetSiteCd;

    @Column(name = "target_site_desc_txt", length = 100)
    private String targetSiteDescTxt;

    @Column(name = "txt", length = 1000)
    private String txt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "value_cd", length = 20)
    private String valueCd;

    @Column(name = "ynu_cd")
    private Character ynuCd;

    @Column(name = "program_jurisdiction_oid")
    private Long programJurisdictionOid;

    @Column(name = "shared_ind", nullable = false)
    private Character sharedInd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "alt_cd", length = 50)
    private String altCd;

    @Column(name = "alt_cd_desc_txt", length = 1000)
    private String altCdDescTxt;

    @Column(name = "alt_cd_system_cd", length = 300)
    private String altCdSystemCd;

    @Column(name = "alt_cd_system_desc_txt", length = 100)
    private String altCdSystemDescTxt;

    @Column(name = "cd_derived_ind")
    private Character cdDerivedInd;

    @Column(name = "rpt_to_state_time")
    private Instant rptToStateTime;

    @Column(name = "cd_version", length = 50)
    private String cdVersion;

    @Column(name = "processing_decision_cd", length = 20)
    private String processingDecisionCd;

    @Column(name = "pregnant_ind_cd", length = 20)
    private String pregnantIndCd;

    @Column(name = "pregnant_week")
    private Short pregnantWeek;

    @Column(name = "processing_decision_txt", length = 1000)
    private String processingDecisionTxt;

}