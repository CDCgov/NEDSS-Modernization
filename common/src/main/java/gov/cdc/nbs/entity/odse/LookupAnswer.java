package gov.cdc.nbs.entity.odse;

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
@Table(name = "LOOKUP_ANSWER")
public class LookupAnswer {
    @Id
    @Column(name = "LOOKUP_ANSWER_UID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LOOKUP_QUESTION_UID", nullable = false)
    private LookupQuestion lookupQuestionUid;

    @Column(name = "FROM_ANSWER_CODE", length = 250)
    private String fromAnswerCode;

    @Column(name = "FROM_ANS_DISPLAY_NM", length = 250)
    private String fromAnsDisplayNm;

    @Column(name = "FROM_CODE_SYSTEM_CD", length = 250)
    private String fromCodeSystemCd;

    @Column(name = "FROM_CODE_SYSTEM_DESC_TXT", length = 250)
    private String fromCodeSystemDescTxt;

    @Column(name = "TO_ANSWER_CODE", length = 250)
    private String toAnswerCode;

    @Column(name = "TO_ANS_DISPLAY_NM", length = 250)
    private String toAnsDisplayNm;

    @Column(name = "TO_CODE_SYSTEM_CD", length = 250)
    private String toCodeSystemCd;

    @Column(name = "TO_CODE_SYSTEM_DESC_TXT", length = 250)
    private String toCodeSystemDescTxt;

    @Column(name = "ADD_TIME", nullable = false)
    private Instant addTime;

    @Column(name = "ADD_USER_ID", nullable = false)
    private Long addUserId;

    @Column(name = "LAST_CHG_TIME", nullable = false)
    private Instant lastChgTime;

    @Column(name = "LAST_CHG_USER_ID", nullable = false)
    private Long lastChgUserId;

    @Column(name = "STATUS_CD", nullable = false, length = 1)
    private String statusCd;

    @Column(name = "STATUS_TIME", nullable = false)
    private Instant statusTime;

}