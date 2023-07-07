package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "LOOKUP_QUESTION")
public class LookupQuestion {
    @Id
    @Column(name = "LOOKUP_QUESTION_uid", nullable = false)
    private Long id;

    @Column(name = "FROM_QUESTION_IDENTIFIER", length = 250)
    private String fromQuestionIdentifier;

    @Column(name = "FROM_QUESTION_DISPLAY_NAME", length = 250)
    private String fromQuestionDisplayName;

    @Column(name = "FROM_CODE_SYSTEM_CD", length = 250)
    private String fromCodeSystemCd;

    @Column(name = "FROM_CODE_SYSTEM_DESC_TXT", length = 250)
    private String fromCodeSystemDescTxt;

    @Column(name = "FROM_DATA_TYPE", length = 250)
    private String fromDataType;

    @Column(name = "FROM_CODE_SET", length = 250)
    private String fromCodeSet;

    @Column(name = "FROM_FORM_CD", length = 250)
    private String fromFormCd;

    @Column(name = "TO_QUESTION_IDENTIFIER", length = 250)
    private String toQuestionIdentifier;

    @Column(name = "TO_QUESTION_DISPLAY_NAME", length = 250)
    private String toQuestionDisplayName;

    @Column(name = "TO_CODE_SYSTEM_CD", length = 250)
    private String toCodeSystemCd;

    @Column(name = "TO_CODE_SYSTEM_DESC_TXT", length = 250)
    private String toCodeSystemDescTxt;

    @Column(name = "TO_DATA_TYPE", length = 250)
    private String toDataType;

    @Column(name = "TO_CODE_SET", length = 250)
    private String toCodeSet;

    @Column(name = "TO_FORM_CD", length = 250)
    private String toFormCd;

    @Column(name = "RDB_COLUMN_NM", length = 30)
    private String rdbColumnNm;

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
