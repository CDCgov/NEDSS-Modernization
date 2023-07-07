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
@Table(name = "Person_name_hist")
public class PersonNameHist {
    @EmbeddedId
    private PersonNameHistId id;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_uid", referencedColumnName = "person_uid", nullable = false)
    @JoinColumn(name = "person_name_seq", referencedColumnName = "person_name_seq", nullable = false)
    private PersonName personName;

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

    @Column(name = "nm_suffix", length = 20)
    private String nmSuffix;

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
