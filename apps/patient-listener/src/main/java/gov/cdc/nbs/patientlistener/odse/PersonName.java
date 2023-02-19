package gov.cdc.nbs.patientlistener.odse;

import java.time.Instant;

import gov.cdc.nbs.patientlistener.enums.Suffix;
import gov.cdc.nbs.patientlistener.enums.converter.SuffixConverter;


import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Person_name")
public class PersonName {
    @EmbeddedId
    private PersonNameId id;

    @MapsId("personUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_uid", nullable = false)
    private Person personUid;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "default_nm_ind")
    private Character defaultNmInd;

    @Column(name = "duration_amt", length = 20)
    private String durationAmt;

    @Column(name = "duration_unit_cd", length = 20)
    private String durationUnitCd;

    @Column(name = "first_nm", length = 50)
    private String firstNm;

    @Column(name = "first_nm_sndx", length = 50)
    private String firstNmSndx;

    @Column(name = "from_time")
    private Instant fromTime;

    @Column(name = "last_chg_reason_cd", length = 20)
    private String lastChgReasonCd;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "last_nm", length = 50)
    private String lastNm;

    @Column(name = "last_nm_sndx", length = 50)
    private String lastNmSndx;

    @Column(name = "last_nm2", length = 50)
    private String lastNm2;

    @Column(name = "last_nm2_sndx", length = 50)
    private String lastNm2Sndx;

    @Column(name = "middle_nm", length = 50)
    private String middleNm;

    @Column(name = "middle_nm2", length = 50)
    private String middleNm2;

    @Column(name = "nm_degree", length = 20)
    private String nmDegree;

    @Column(name = "nm_prefix", length = 20)
    private String nmPrefix;

    @Convert(converter = SuffixConverter.class)
    @Column(name = "nm_suffix", length = 20)
    private Suffix nmSuffix;

    @Column(name = "nm_use_cd", length = 20)
    private String nmUseCd;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "status_cd", nullable = false)
    private Character statusCd;

    @Column(name = "status_time", nullable = false)
    private Instant statusTime;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "as_of_date")
    private Instant asOfDate;

}