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
@Table(name = "NBS_aggregate")
public class NbsAggregate {
    @Id
    @Column(name = "nbs_aggregate_uid", nullable = false)
    private Long id;

    @Column(name = "label", length = 250)
    private String label;

    @Column(name = "desc_txt", length = 2000)
    private String descTxt;

    @Column(name = "tool_tip", length = 2000)
    private String toolTip;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "code", nullable = false, length = 20)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nbs_question_uid", nullable = false)
    private NbsQuestion nbsQuestionUid;

}