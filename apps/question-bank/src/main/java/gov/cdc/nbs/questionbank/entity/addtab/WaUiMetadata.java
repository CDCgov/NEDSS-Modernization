package gov.cdc.nbs.questionbank.entity.addtab;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_ui_metadata", catalog = "NBS_ODSE")
public class WaUiMetadata {
    @Id
    @Column(name = "wa_ui_metadata_uid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wa_template_uid", length = 19, nullable = false)
    private Long waTemplateUid;

    @Column(name = "nbs_ui_component_uid")
    private Long nbsUiComponentUid;

    @Column(name = "parent_uid")
    private Long parentUid;

    @Column(name = "question_label", length = 4000)
    private String questionLabel;

    @Column(name = "question_tool_tip", length = 4000)
    private String questionToolTip;

    @Column(name = "enable_ind", length = 20)
    private String enableInd;

    @Column(name = "default_value", length = 20)
    private String defaultValue;

    @Column(name = "display_ind")
    private String displayInd;

    @Column(name = "order_nbr")
    private Integer orderNbr;

    @Column(name = "required_ind", length = 19)
    private String requiredInd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time", length =4000)
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name= "record_status_cd", length = 100)
    private String recordStatusCd;

    @Column(name = "record_status_time", length = 50)
    private Instant recordStatusTime;

    @Column(name = "max_length")
    private Long maxLength;

    @Column(name = "admin_comment", length = 4000)
    private String adminComment;

    @Column(name = "version_ctrl_nbr")
    private Integer versionCtrlNbr;

    @Column(name = "field_size", length = 50)
    private String fieldSize;

    @Column(name = "future_date_ind_cd", length = 50)
    private String futureDateIndCd;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "data_cd", length = 50)
    private String dataCd;

    @Column(name = "data_location", length = 50)
    private String dataLlocation;

    @Column(name = "data_type", length = 50)
    private String dataType;

    @Column(name = "data_use_cd", length = 50)
    private String dataUseCd;


    @Column(name = "legacy_data_location", length = 50)
    private String legacyDataLocation;

    @Column(name = "part_type_cd", length = 50)
    private String partTypeCd;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "question_identifier", length = 50)
    private String questionIdentifier;

    @Column(name = "question_oid", length = 50)
    private String questionOid;


    @Column(name = "question_oid_system_txt", length = 50)
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier", length = 50)
    private String questionUnitIdentifier;

    @Column(name = "repeats_ind_cd", length = 50)
    private String repeatsIndCd;

    @Column(name = "unit_parent_identifier", length = 50)
    private String unitParentIdentifier;

    @Column(name = "group_nm", length = 50)
    private String groupNm;

    @Column(name = "sub_group_nm", length = 50)
    private String subGroupNm;

    @Column(name = "desc_txt", length = 50)
    private String descTxt;


    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "entry_method", length = 50)
    private String entryMethod;

    @Column(name = "question_type", length = 50)
    private String questionType;

    @Column(name = "publish_ind_cd", length = 50)
    private String publishIndCd;


    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_question_ind_cd", length = 50)
    private String standardQuestionIndCd;

    @Column(name = "standard_nnd_ind_cd", length = 50)
    private String standardNndIndCd;


    @Column(name = "question_nm", length = 50)
    private String questionNm;

    @Column(name = "unit_type_cd", length = 50)
    private String unitTypeCd;

    @Column(name = "unit_value", length = 50)
    private String unitValue;

    @Column(name = "other_value_ind_cd", length = 50)
    private String otherValueIndCd;

    @Column(name = "batch_table_appear_ind_cd", length = 50)
    private String batchTableAppearIndCd;

    @Column(name = "batch_table_header", length = 50)
    private String batchTableHeader;

    @Column(name = "batch_table_column_width")
    private Integer batchTableColumnWidth;


    @Column(name = "coinfection_ind_cd", length = 50)
    private String coinfectionIndCd;

    @Column(name = "block_nm", length = 50)
    private String blockNm;

}

