package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

import gov.cdc.nbs.entity.enums.RecordStatus;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Organization {
    @Id
    @Column(name = "organization_uid", nullable = false)
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_uid", nullable = false)
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

    @Column(name = "duration_amt", length = 20)
    private String durationAmt;

    @Column(name = "duration_unit_cd", length = 20)
    private String durationUnitCd;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status_cd", length = 20)
    private RecordStatus recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "standard_industry_class_cd", length = 20)
    private String standardIndustryClassCd;

    @Column(name = "standard_industry_desc_txt", length = 100)
    private String standardIndustryDescTxt;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "display_nm", length = 100)
    private String displayNm;

    @Column(name = "street_addr1", length = 100)
    private String streetAddr1;

    @Column(name = "street_addr2", length = 100)
    private String streetAddr2;

    @Column(name = "city_cd", length = 20)
    private String cityCd;

    @Column(name = "city_desc_txt", length = 100)
    private String cityDescTxt;

    @Column(name = "state_cd", length = 20)
    private String stateCd;

    @Column(name = "cnty_cd", length = 20)
    private String cntyCd;

    @Column(name = "cntry_cd", length = 20)
    private String cntryCd;

    @Column(name = "zip_cd", length = 20)
    private String zipCd;

    @Column(name = "phone_nbr", length = 20)
    private String phoneNbr;

    @Column(name = "phone_cntry_cd", length = 20)
    private String phoneCntryCd;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Short versionCtrlNbr;

    @Column(name = "electronic_ind")
    private Character electronicInd;

    @Column(name = "edx_ind", length = 1)
    private String edxInd;

}