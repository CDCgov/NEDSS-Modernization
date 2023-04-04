package gov.cdc.nbs.entity.syphilis_qb;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;




@Entity
@Table(name = "questions")
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_uid", nullable = false)
    private Long questionId;

    @Column(name = "code_set_group_id")
    private Long codeSetId;

    @Column(name = "data_cd")
    private String dataCd;

    @Column(name = "data_location")
    private String dataLocation;

    @Column(name = "question_identifier")
    private String questionIdentifer;

    @Column(name = "question_old")
    private String questionOld;

    @Column(name = "question_old_system_text")
    private String questionOldSystemText;

    @Column(name = "question_unit_identifier", length = 20)
    private String questionUnitIdentifier;

    @Column(name = "data_type", length = 20)
    private String dataType;

    @Column(name = "data_use_cd", length = 20)
    private String dataUseCd;

    @Column(name = "question_label")
    private String questionLabel;

    @Column(name = "question_tool_tip")
    private String questionToolTip;

    @Column(name = "rdb_column_nm", length = 30)
    private String rdbColumnName;

    @Column(name = "part_type_cd")
    private String partTypeCd;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "version_ctrl_nbr")
    private Integer versionCtrlNbr;

    @Column(name = "unit_parent_identifier", length = 20)
    private String unitParentIdentifier;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "future_date_ind_cd", length = 1)
    private String futureDateIndCd;

    @Column(name = "legacy_data_location")
    private String legacyDataLocation;

    @Column(name = "repeats_ind_cd", length = 1)
    private String repeatsIndCd;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "add_time")
    private String addTime;

    @Column(name = "last_chg_time")
    private SetterTime lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "question_nm")
    private String questionNm;

    @Column(name = "group_nm")
    private String groupNm;

    @Column(name = "sub_group_nm")
    private String subGroupNm;

    @Column(name = "desc_txt")
    private String descTxt;

    @Column(name = "mask")
    private String mask;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "rpt_admin_column_nm")
    private String rptAdminColumnNm;

    @Column(name = "nnd_msg_ind", length = 1)
    private String nndMsgInd;

    @Column(name = "question_identifier_nnd")
    private String questionIdentifierNnd;

    @Column(name = "question_label_nnd")
    private String questionLabelNnd;

    @Column(name = "question_required_nnd", length = 1)
    private String questionRequiredNnd;

    @Column(name = "question_data_type_nnd", length = 10)
    private String questionDataTypeNnd;

    @Column(name = "hl7_segment_field")
    private String hl7SegmentField;

    @Column(name = "order_group_id", length = 5)
    private String orderGroupId;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private String recordStatusTime;

    @Column(name = "nbs_ui_component_uid")
    private Long nbsUiComponentUid;

    @Column(name = "standard_question_ind_cd", length = 1)
    private String standardQuestionIndCd;

    @Column(name = "entry_method", length = 20)
    private String entryMethod;

    @Column(name = "question_type", length = 20)
    private String questionType;

    @Column(name = "admin_comment")
    private String adminComment;

    @Column(name = "rdb_table_nm")
    private String rdbTableName;

    @Column(name = "user_defined_column_nm")
    private String userDefinedColumnNm;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_nnd_ind_cd", length = 1)
    private String standardNndIndCd;

    @Column(name = "legacy_question_identifier")
    private String legacyQuestionIdentifier;

    @Column(name = "unit_type_cd", length = 20)
    private String unitTypeCd;

    @Column(name = "unit_value")
    private String unitValue;

    @Column(name = "other_value_ind_cd", length = 1)
    private String otherValueIndCd;

    @Column(name = "source_nm")
    private String sourceNm;

    @Column(name = "coinfection_ind_cd", length = 1)
    private String coinfectionIndCd;

    @Column(name = "question_set_question_set_uid")
    private Long questionSetQuestionSetUid;
}