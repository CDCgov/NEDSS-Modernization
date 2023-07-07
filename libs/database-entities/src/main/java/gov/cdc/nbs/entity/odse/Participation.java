package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import gov.cdc.nbs.entity.enums.RecordStatus;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Participation {
    @EmbeddedId
    private ParticipationId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("cd")
    @JoinColumn(name = "subject_entity_uid", nullable = false)
    @JoinColumn(name = "role_seq")
    @JoinColumn(name = "cd")
    private Role role;

    @MapsId("actUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "act_uid", nullable = false)
    private Act actUid;

    @Column(name = "act_class_cd", length = 10)
    private String actClassCd;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "awareness_cd", length = 20)
    private String awarenessCd;

    @Column(name = "awareness_desc_txt", length = 100)
    private String awarenessDescTxt;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "record_status_cd", length = 20)
    private RecordStatus recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "subject_class_cd", length = 10)
    private String subjectClassCd;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "type_desc_txt", length = 100)
    private String typeDescTxt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

    @Column(name = "cd", length = 40)
    private String cd;

}
