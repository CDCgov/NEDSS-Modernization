package gov.cdc.nbs.questionbank.entity.condition;

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
@Table(name = "Code_to_condition", catalog="NBS_SRTE")
public class CodeToCondition {
    @EmbeddedId
    private CodeToConditionId id;

    @Column(name="code_system_cd", length=256)
    private String codeSystemCd;

    @Column(name="code_system_desc_txt", length=100)
    private String codeSystemDescTxt;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("code")
    @JoinColumn(name = "code", insertable = false, updatable = false)
    private String code;

    @Column(name="code_desc_txt", length=256)
    private String codeDescTxt;

    @Column(name="code_system_version_id", length=256)
    private String codeSystemVersionId;

    @Column(name="condition_cd", length=20)
    private String conditionCd;

    @Column(name="disease_nm", length=200)
    private String diseaseNm;

    @Column(name="status_cd", length=1)
    private String statusCd;

    @Column(name="status_time")
    private Instant statusTime;

    @Column(name="nbs_uid")
    private Integer nbsUid;

    @Column(name="effective_from_time")
    private Instant effectiveFromTime;

    @Column(name="effective_to_time")
    private Instant effectiveToTime;
}
