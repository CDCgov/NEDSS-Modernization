package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateSection;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateSubsection;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateTab;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_UI_metadata", catalog = "NBS_ODSE")
public class WaUiMetadata {
  public static final String ACTIVE = "Active";
  public static final String INACTIVE = "Inactive";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "wa_ui_metadata_uid", nullable = false)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {
      CascadeType.REMOVE,
      CascadeType.DETACH
    })
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

  public WaUiMetadata() {
    this.standardNndIndCd = 'F';
    this.publishIndCd = 'T';
    this.enableInd = "T";
    this.displayInd = "T";
    this.requiredInd = "F";
    this.entryMethod = "USER";
    this.versionCtrlNbr = 1;
    this.recordStatusCd = ACTIVE;
  }

  WaUiMetadata(
      final WaTemplate template,
      final long type,
      final String name,
      final int order,
      final long createdBy,
      final Instant createdOn) {
    this();
    this.waTemplateUid = template;
    this.questionLabel = name;
    this.orderNbr = order;
    this.nbsUiComponentUid = type;

    this.addUserId = createdBy;
    this.addTime = createdOn;
    this.lastChgUserId = createdBy;
    this.lastChgTime = createdOn;

    this.recordStatusTime = createdOn;

  }

  private void added(PageContentCommand.AddQuestion command) {
    this.addTime = command.requestedOn();
    this.addUserId = command.userId();
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.userId();
    this.recordStatusTime = command.requestedOn();
  }


  public WaUiMetadata(WaTemplate page, PageContentCommand.AddQuestion command) {
    this();
    // Defaults
    this.standardNndIndCd = 'F';
    this.standardQuestionIndCd = 'F';

    // User specified
    this.waTemplateUid = page;
    this.nbsUiComponentUid = command.question().getNbsUiComponentUid();
    this.questionLabel = command.question().getQuestionLabel();
    this.questionToolTip = command.question().getQuestionToolTip();
    this.orderNbr = command.orderNumber();
    this.adminComment = command.question().getAdminComment();
    this.dataLocation = command.question().getDataLocation();
    this.descTxt = command.question().getDescTxt();
    this.questionType = command.question().getQuestionType();
    this.questionNm = command.question().getQuestionNm();
    this.questionIdentifier = command.question().getQuestionIdentifier();
    this.questionOid = command.question().getQuestionOid();
    this.questionOidSystemTxt = command.question().getQuestionOidSystemTxt();
    this.groupNm = command.question().getGroupNm();
    this.subGroupNm = command.question().getSubGroupNm();
    this.dataType = command.question().getDataType();
    this.otherValueIndCd = command.question().getOtherValueIndCd();

    // Question type specific fields
    if (command.question() instanceof TextQuestionEntity t) {
      this.defaultValue = t.getDefaultValue();
      this.mask = t.getMask();
      this.fieldSize = t.getFieldSize();
    } else if (command.question() instanceof DateQuestionEntity d) {
      this.mask = d.getMask();
      this.futureDateIndCd = d.getFutureDateIndCd();
    } else if (command.question() instanceof NumericQuestionEntity n) {
      this.mask = n.getMask();
      this.fieldSize = n.getFieldSize();
      this.defaultValue = n.getDefaultValue();
      this.minValue = n.getMinValue();
      this.maxValue = n.getMaxValue();
      this.unitTypeCd = n.getUnitTypeCd();
      this.unitValue = n.getUnitValue();
    } else if (command.question() instanceof CodedQuestionEntity c) {
      this.codeSetGroupId = c.getCodeSetGroupId();
      this.defaultValue = c.getDefaultValue();
    } else {
      throw new AddQuestionException("Failed to determine question type");
    }

    // Audit info
    this.added(command);
  }

  public WaUiMetadata(WaTemplate page, PageContentCommand.AddTab command) {
    this();
    this.nbsUiComponentUid = 1010L;
    this.waTemplateUid = page;
    this.questionLabel = command.label();
    setVisible(command.visible());
    this.questionIdentifier = command.identifier();
    this.orderNbr = command.orderNumber();

    // Audit
    added(command);
  }

  public void update(UpdateTab command) {
    setVisible(command.visible());
    this.questionLabel = command.label();
    updated(command);
  }

  public void update(UpdateSubsection command) {
    setVisible(command.visible());
    this.questionLabel = command.label();
    updated(command);
  }

  public WaUiMetadata(WaTemplate page, PageContentCommand.AddSection command) {
    this();
    this.nbsUiComponentUid = 1015L;
    this.waTemplateUid = page;
    this.questionLabel = command.label();
    setVisible(command.visible());
    this.questionIdentifier = command.identifier();
    this.orderNbr = command.orderNumber();

    // Audit
    added(command);
  }

   public WaUiMetadata(WaTemplate page, PageContentCommand.AddSubsection command) {
    this();
    this.nbsUiComponentUid = 1016L;
    this.waTemplateUid = page;
    this.questionLabel = command.label();
    setVisible(command.visible());
    this.questionIdentifier = command.identifier();
    this.orderNbr = command.orderNumber();

    // Audit
    added(command);
  }

  public void update(UpdateSection command) {
    setVisible(command.visible());
    this.questionLabel = command.label();
    updated(command);
  }

  private void added(PageContentCommand command) {
    this.addTime = command.requestedOn();
    this.addUserId = command.userId();
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.userId();
    this.recordStatusCd = ACTIVE;
    this.recordStatusTime = command.requestedOn();
  }

  private void updated(PageContentCommand command) {
    this.lastChgTime = command.requestedOn();
    this.lastChgUserId = command.userId();
  }

  public static WaUiMetadata clone(WaUiMetadata original) {
    return new WaUiMetadata(
        null,
        null,
        original.getNbsUiComponentUid(),
        original.getParentUid(),
        original.getQuestionLabel(),
        original.getQuestionToolTip(),
        original.getEnableInd(),
        original.getDefaultValue(),
        original.getDisplayInd(),
        original.getOrderNbr(),
        original.getRequiredInd(),
        original.getAddTime(),
        original.getAddUserId(),
        original.getLastChgTime(),
        original.getLastChgUserId(),
        original.getRecordStatusCd(),
        original.getRecordStatusTime(),
        original.getMaxLength(),
        original.getAdminComment(),
        original.getVersionCtrlNbr(),
        original.getFieldSize(),
        original.getFutureDateIndCd(),
        original.getLocalId(),
        original.getCodeSetGroupId(),
        original.getDataCd(),
        original.getDataLocation(),
        original.getDataType(),
        original.getDataUseCd(),
        original.getLegacyDataLocation(),
        original.getPartTypeCd(),
        original.getQuestionGroupSeqNbr(),
        original.getQuestionIdentifier(),
        original.getQuestionOid(),
        original.getQuestionOidSystemTxt(),
        original.getQuestionUnitIdentifier(),
        original.getRepeatsIndCd(),
        original.getUnitParentIdentifier(),
        original.getGroupNm(),
        original.getSubGroupNm(),
        original.getDescTxt(),
        original.getMask(),
        original.getEntryMethod(),
        original.getQuestionType(),
        original.getPublishIndCd(),
        original.getMinValue(),
        original.getMaxValue(),
        original.getStandardQuestionIndCd(),
        original.getStandardNndIndCd(),
        original.getQuestionNm(),
        original.getUnitTypeCd(),
        original.getUnitValue(),
        original.getOtherValueIndCd(),
        original.getBatchTableAppearIndCd(),
        original.getBatchTableHeader(),
        original.getBatchTableColumnWidth(),
        original.getCoinfectionIndCd(),
        original.getBlockNm());
  }

  private void setVisible(boolean visible) {
    this.displayInd = visible ? "T" : "F";
  }

  @Override
  public String toString() {
    return "WaUiMetadata{" +
        "id=" + id +
        ", type=" + nbsUiComponentUid +
        ", name='" + questionLabel + '\'' +
        ", order=" + orderNbr +
        '}';
  }
}
