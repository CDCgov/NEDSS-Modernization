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
@Table(name = "CT_contact_answer_hist")
public class CtContactAnswerHist {
    @Id
    @Column(name = "ct_contact_answer_hist_uid", nullable = false)
    private Long id;

    @Column(name = "ct_contact_answer_uid", nullable = false)
    private Long ctContactAnswerUid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ct_contact_uid", nullable = false)
    private CtContact ctContactUid;

    @Column(name = "answer_txt", length = 2000)
    private String answerTxt;

    @Column(name = "nbs_question_uid", nullable = false)
    private Long nbsQuestionUid;

    @Column(name = "nbs_question_version_ctrl_nbr", nullable = false)
    private Short nbsQuestionVersionCtrlNbr;

    @Column(name = "ct_contact_version_ctrl_nbr", nullable = false)
    private Long ctContactVersionCtrlNbr;

    @Column(name = "last_chg_time", nullable = false)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id", nullable = false)
    private Long lastChgUserId;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time", nullable = false)
    private Instant recordStatusTime;

    @Column(name = "seq_nbr")
    private Short seqNbr;

    @Lob
    @Column(name = "answer_large_txt", columnDefinition = "TEXT")
    private String answerLargeTxt;

    @Column(name = "answer_group_seq_nbr")
    private Integer answerGroupSeqNbr;

}