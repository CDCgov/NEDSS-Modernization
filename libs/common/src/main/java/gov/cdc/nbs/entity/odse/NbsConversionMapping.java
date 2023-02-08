package gov.cdc.nbs.entity.odse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "NBS_conversion_mapping")
public class NbsConversionMapping {
    @Id
    @Column(name = "nbs_conversion_mapping_uid", nullable = false)
    private Long id;

    @Column(name = "from_code", length = 2000)
    private String fromCode;

    @Column(name = "from_code_set_nm", length = 256)
    private String fromCodeSetNm;

    @Column(name = "from_data_type", length = 50)
    private String fromDataType;

    @Column(name = "from_question_id", length = 30)
    private String fromQuestionId;

    @Column(name = "condition_cd_group_id")
    private Long conditionCdGroupId;

    @Column(name = "to_code", length = 2000)
    private String toCode;

    @Column(name = "to_code_set_nm", length = 256)
    private String toCodeSetNm;

    @Column(name = "to_data_type", length = 30)
    private String toDataType;

    @Column(name = "to_question_id", length = 30)
    private String toQuestionId;

    @Column(name = "translation_required_ind", nullable = false, length = 20)
    private String translationRequiredInd;

    @Column(name = "from_db_location", nullable = false, length = 50)
    private String fromDbLocation;

    @Column(name = "to_db_location", nullable = false, length = 50)
    private String toDbLocation;

    @Column(name = "from_label", length = 200)
    private String fromLabel;

    @Column(name = "to_label", length = 200)
    private String toLabel;

    @Column(name = "legacy_block_ind", length = 50)
    private String legacyBlockInd;

    @Column(name = "block_id_nbr")
    private Integer blockIdNbr;

    @Column(name = "other_ind")
    private Character otherInd;

    @Column(name = "unit_ind")
    private Character unitInd;

    @Column(name = "unit_type_cd", length = 50)
    private String unitTypeCd;

    @Column(name = "unit_value", length = 50)
    private String unitValue;

    @Column(name = "trigger_question_id", length = 30)
    private String triggerQuestionId;

    @Column(name = "trigger_question_value", length = 50)
    private String triggerQuestionValue;

    @Column(name = "from_other_question_id", length = 30)
    private String fromOtherQuestionId;

    @Column(name = "conversion_type", length = 50)
    private String conversionType;

    @Column(name = "answer_group_seq_nbr")
    private Integer answerGroupSeqNbr;

}