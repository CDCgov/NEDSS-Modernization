package gov.cdc.nbs.questionbank.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_UI_metadata", catalog = "NBS_ODSE")
public class WaUiMetadatum {

    public static final String ACTIVE = "Active";
    public static final String INACTIVE = "Inactive";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_ui_metadata_uid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wa_template_uid", nullable = false)
    private WaTemplate waTemplateUid;

    @Column(name = "nbs_ui_component_uid", nullable = false)
    private Long nbsUiComponentUid;

    @Column(name = "parent_uid")
    private Long parentUid;

    @Column(name = "question_label", length = 300)
    private String questionLabel;

    @Column(name = "question_tool_tip", length = 2000)
    private String questionToolTip;

    @Column(name = "enable_ind", length = 1)
    private String enableInd;

    @Column(name = "default_value", length = 300)
    private String defaultValue;

    @Column(name = "display_ind", length = 1)
    private String displayInd;

    @Column(name = "order_nbr")
    private Integer orderNbr;

    @Column(name = "required_ind", length = 2)
    private String requiredInd;

    @Column(name = "add_time")
    private Instant addTime;

    @Column(name = "add_user_id")
    private Long addUserId;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    @Column(name = "record_status_cd", length = 20)
    private String recordStatusCd;

    @Column(name = "record_status_time")
    private Instant recordStatusTime;

    @Column(name = "max_length")
    private Long maxLength;

    @Column(name = "admin_comment", length = 2000)
    private String adminComment;

    @Column(name = "version_ctrl_nbr", nullable = false)
    private Integer versionCtrlNbr;

    @Column(name = "field_size", length = 10)
    private String fieldSize;

    @Column(name = "future_date_ind_cd")
    private Character futureDateIndCd;

    @Column(name = "local_id", length = 50)
    private String localId;

    @Column(name = "code_set_group_id")
    private Long codeSetGroupId;

    @Column(name = "data_cd", length = 50)
    private String dataCd;

    @Column(name = "data_location", length = 150)
    private String dataLocation;

    @Column(name = "data_type", length = 20)
    private String dataType;

    @Column(name = "data_use_cd", length = 20)
    private String dataUseCd;

    @Column(name = "legacy_data_location", length = 150)
    private String legacyDataLocation;

    @Column(name = "part_type_cd", length = 50)
    private String partTypeCd;

    @Column(name = "question_group_seq_nbr")
    private Integer questionGroupSeqNbr;

    @Column(name = "question_identifier", length = 50)
    private String questionIdentifier;

    @Column(name = "question_oid", length = 150)
    private String questionOid;

    @Column(name = "question_oid_system_txt", length = 100)
    private String questionOidSystemTxt;

    @Column(name = "question_unit_identifier", length = 20)
    private String questionUnitIdentifier;

    @Column(name = "repeats_ind_cd")
    private Character repeatsIndCd;

    @Column(name = "unit_parent_identifier", length = 20)
    private String unitParentIdentifier;

    @Column(name = "group_nm", length = 50)
    private String groupNm;

    @Column(name = "sub_group_nm", length = 50)
    private String subGroupNm;

    @Column(name = "desc_txt", length = 2000)
    private String descTxt;

    @Column(name = "mask", length = 50)
    private String mask;

    @Column(name = "entry_method", length = 20)
    private String entryMethod;

    @Column(name = "question_type", length = 20)
    private String questionType;

    @Column(name = "publish_ind_cd")
    private Character publishIndCd;

    @Column(name = "min_value")
    private Long minValue;

    @Column(name = "max_value")
    private Long maxValue;

    @Column(name = "standard_question_ind_cd")
    private Character standardQuestionIndCd;

    @Column(name = "standard_nnd_ind_cd")
    private Character standardNndIndCd;

    @Column(name = "question_nm", length = 50)
    private String questionNm;

    @Column(name = "unit_type_cd", length = 20)
    private String unitTypeCd;

    @Column(name = "unit_value", length = 50)
    private String unitValue;

    @Column(name = "other_value_ind_cd")
    private Character otherValueIndCd;

    @Column(name = "batch_table_appear_ind_cd")
    private Character batchTableAppearIndCd;

    @Column(name = "batch_table_header", length = 50)
    private String batchTableHeader;

    @Column(name = "batch_table_column_width")
    private Integer batchTableColumnWidth;

    @Column(name = "coinfection_ind_cd")
    private Character coinfectionIndCd;

    @Column(name = "block_nm", length = 30)
    private String blockNm;


    public WaUiMetadatum(PageContentCommand.AddQuestion command) {
        // Defaults
        this.standardNndIndCd = 'F';
        this.standardQuestionIndCd = 'F';
        this.enableInd = "T";
        this.entryMethod = "USER";
        this.recordStatusCd = ACTIVE;

        // User specified
        this.waTemplateUid = command.page();
        this.nbsUiComponentUid = command.uiComponent();
        this.questionLabel = command.label();
        this.questionToolTip = command.tooltip();
        this.orderNbr = command.orderNumber();
        this.adminComment = command.adminComment();
        this.dataLocation = command.dataLocation();
        this.descTxt = command.description();
        this.questionType = command.questionType();
        this.questionNm = command.questionName();
        this.questionIdentifier = command.questionIdentifier();
        this.questionOid = command.questionOid();
        this.questionOidSystemTxt = command.questionOidSystem();
        this.groupNm = command.groupName();


        if (command instanceof PageContentCommand.AddQuestion.AddTextQuestion t) {
            setTextFields(t);
        } else if (command instanceof PageContentCommand.AddQuestion.AddDateQuestion d) {
            setDateFields(d);
        } else if (command instanceof PageContentCommand.AddQuestion.AddNumericQuestion n) {
            setNumericFields(n);
        } else if (command instanceof PageContentCommand.AddQuestion.AddCodedQuestion c) {
            setCodedFields(c);
        }

        this.added(command);
    }

    public void setTextFields(PageContentCommand.AddQuestion.AddTextQuestion command) {
        this.dataType = "TEXT";
        this.defaultValue = command.defaultValue();
        this.mask = command.mask();
        this.fieldSize = command.fieldLength();
    }

    public void setDateFields(PageContentCommand.AddQuestion.AddDateQuestion command) {
        this.dataType = "DATE";
        this.mask = command.mask();
        this.futureDateIndCd = command.futureDateIndicator();
    }

    public void setNumericFields(PageContentCommand.AddQuestion.AddNumericQuestion command) {
        this.dataType = "NUMERIC";
        this.mask = command.mask();
        this.fieldSize = command.fieldLength();
        this.defaultValue = command.defaultValue();
        this.minValue = command.minValue();
        this.maxValue = command.maxValue();
        this.unitTypeCd = command.unitTypeCd();
        this.unitValue = command.unitValue();
    }

    public void setCodedFields(PageContentCommand.AddQuestion.AddCodedQuestion command) {
        this.dataType = "CODED";
        this.codeSetGroupId = command.codeSetGroupId();
        this.defaultValue = command.defaultValue();
    }

    private void added(PageContentCommand.AddQuestion command) {
        this.addTime = command.requestedOn();
        this.addUserId = command.userId();
        this.lastChgTime = command.requestedOn();;
        this.lastChgUserId = command.userId();
        this.recordStatusTime = command.requestedOn();;
    }



}
