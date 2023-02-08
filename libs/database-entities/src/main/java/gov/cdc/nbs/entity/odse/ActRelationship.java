package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Act_relationship")
public class ActRelationship {
    @EmbeddedId
    private ActRelationshipId id;

    @MapsId("sourceActUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_act_uid", nullable = false)
    private Act sourceActUid;

    @MapsId("targetActUid")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_act_uid", nullable = false)
    private Act targetActUid;

    @Column(name = "add_reason_cd", length = 20)
    private String addReasonCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

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

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "sequence_nbr")
    private Short sequenceNbr;

    @Column(name = "source_class_cd", length = 10)
    private String sourceClassCd;

    @Column(name = "status_cd")
    private Character statusCd;

    @Column(name = "status_time")
    private Instant statusTime;

    @Column(name = "target_class_cd", length = 10)
    private String targetClassCd;

    @Column(name = "to_time")
    private Instant toTime;

    @Column(name = "type_desc_txt", length = 100)
    private String typeDescTxt;

    @Column(name = "user_affiliation_txt", length = 20)
    private String userAffiliationTxt;

}