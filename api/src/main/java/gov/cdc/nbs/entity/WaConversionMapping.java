package gov.cdc.nbs.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_Conversion_Mapping")
public class WaConversionMapping {
    @Id
    @Column(name = "WA_Conversion_Mapping_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "NBS_Conversion_Page_Mgmt_uid", nullable = false)
    private NbsConversionPageMgmt nbsConversionPageMgmtUid;

    @Column(name = "from_question_id", length = 30)
    private String fromQuestionId;

    @Column(name = "from_answer", length = 2000)
    private String fromAnswer;

    @Column(name = "to_question_id", length = 30)
    private String toQuestionId;

    @Column(name = "to_answer", length = 2000)
    private String toAnswer;

    @Column(name = "to_code_set_group_id")
    private Long toCodeSetGroupId;

    @Column(name = "to_data_type", length = 30)
    private String toDataType;

    @Column(name = "to_nbs_ui_component_uid")
    private Long toNbsUiComponentUid;

    @Column(name = "block_id_nbr")
    private Integer blockIdNbr;

    @Column(name = "mapping_status", length = 20)
    private String mappingStatus;

    @Column(name = "question_mapped", length = 20)
    private String questionMapped;

    @Column(name = "answer_mapped", length = 20)
    private String answerMapped;

    @Column(name = "answer_group_seq_nbr")
    private Integer answerGroupSeqNbr;

    @Column(name = "conversion_type", length = 50)
    private String conversionType;

}