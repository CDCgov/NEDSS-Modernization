package gov.cdc.nbs.questionbank.entity;

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
@Table(name = "WA_question_hist", catalog = "NBS_ODSE")
public class WaQuestionHist {
    @Id
    @Column(name = "wa_question_hist_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wa_question_uid", nullable = false)
    private WaQuestion waQuestionUid;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "data_cd", length = 50)
    private String dataCd;

    @Column(name = "data_location", length = 150)
    private String dataLocation;

    @Column(name = "question_identifier", nullable = false, length = 50)
    private String questionIdentifier;

    @Column(name = "question_oid", length = 150)
    private String questionOid;

    @Column(name = "question_oid_system_txt", length = 100)
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier", length = 20)
    private String questionUnitIdentifier;

    @Column(name = "data_type", length = 20)
    private String dataType;

    @Column(name = "data_use_cd", length = 20)
    private String dataUseCd;

    @Column(name = "question_label", length = 300)
    private String questionLabel;

    @Column(name = "question_tool_tip", length = 2000)
    private String questionToolTip;

    @Column(name = "rdb_column_nm", length = 30)
    private String rdbColumnNm;

    @Column(name = "part_type_cd", length = 50)
    private String partTypeCd;

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Integer versionCtrlNbr;

    @Column(name = "unit_parent_identifier", length = 20)
    private String unitParentIdentifier;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "future_date_ind_cd")
    private Character futureDateIndCd;

    @Column(name = "legacy_data_location", length = 150)
    private String legacyDataLocation;

    @Column(name = "repeats_ind_cd")
    private Character repeatsIndCd;

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "question_nm", length = 50)
    private String questionNm;

    @Column(name = "group_nm", length = 50)
    private String groupNm;

    @Column(name = "sub_group_nm", length = 50)
    private String subGroupNm;

    @Column(name = "desc_txt", length = 2000)
    private String descTxt;

    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "rpt_admin_column_nm", length = 50)
    private String rptAdminColumnNm;

    @Column(name = "nnd_msg_ind")
    private Character nndMsgInd;

    @Column(name = "question_identifier_nnd", length = 50)
    private String questionIdentifierNnd;

    @Column(name = "question_label_nnd", length = 150)
    private String questionLabelNnd;

    @Column(name = "question_required_nnd")
    private Character questionRequiredNnd;

    @Column(name = "question_data_type_nnd", length = 10)
    private String questionDataTypeNnd;

    @Column(name = "hl7_segment_field", length = 30)
    private String hl7SegmentField;

    @Column(name = "order_group_id", length = 5)
    private String orderGroupId;

    @Column(name = "record_status_cd", nullable = false, length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "nbs_ui_component_uid")
    private Long nbsUiComponentUid;

    @Column(name = "standard_question_ind_cd")
    private Character standardQuestionIndCd;

    @Column(name = "entry_method", length = 20)
    private String entryMethod;

    @Column(name = "question_type", length = 20)
    private String questionType;

    @Column(name = "admin_comment", length = 2000)
    private String adminComment;

    @Column(name = "rdb_table_nm", length = 30)
    private String rdbTableNm;

    @Column(name = "user_defined_column_nm", length = 30)
    private String userDefinedColumnNm;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_nnd_ind_cd")
    private Character standardNndIndCd;

    @Column(name = "legacy_question_identifier", length = 50)
    private String legacyQuestionIdentifier;

    @Column(name = "unit_type_cd", length = 20)
    private String unitTypeCd;

    @Column(name = "unit_value", length = 50)
    private String unitValue;

    @Column(name = "other_value_ind_cd")
    private Character otherValueIndCd;

    @Column(name = "source_nm", length = 250)
    private String sourceNm;

    @Column(name = "coinfection_ind_cd")
    private Character coinfectionIndCd;

}
