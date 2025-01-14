package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.UnitType;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.SetQuestionRequired;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateCodedQuestionValueset;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateSection;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateSubsection;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateTab;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_UI_metadata", catalog = "NBS_ODSE")
public class WaUiMetadata {
  public static final String ACTIVE = "Active";
  public static final String INACTIVE = "Inactive";

  private static final Long LINE_SEPARATOR_ID = 1012L;
  private static final Long HYPERLINK_ID = 1003L;
  private static final Long READ_ONLY_COMMENTS_ID = 1014L;
  private static final Long READ_ONLY_PARTICIPANTS_LIST_ID = 1030L;
  private static final Long ORIGINAL_ELECTRONIC_DOCUMENT_LIST_ID = 1036L;
  private static final Long READONLY_USER_ENTERED = 1026L;

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

  @OneToOne(
      fetch = FetchType.LAZY,
      mappedBy = "waUiMetadataUid",
      cascade = {
          CascadeType.PERSIST,
          CascadeType.REMOVE
      },
      orphanRemoval = true)
  private WaRdbMetadata waRdbMetadatum;

  @OneToOne(
      fetch = FetchType.LAZY,
      mappedBy = "waUiMetadataUid",
      cascade = {
          CascadeType.PERSIST,
          CascadeType.REMOVE
      },
      orphanRemoval = true
  )
  @SuppressWarnings(
      //  Bidirectional mappings require knowledge of each other
      "javaarchitecture:S7027"
  )
  private WaNndMetadatum waNndMetadatum;


  public WaUiMetadata() {
    this.standardNndIndCd = 'F';
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

  public WaUiMetadata(PageContentCommand.AddReadOnlyComments command) {
    // Defaults
    this();

    this.publishIndCd = null;
    this.entryMethod = null;
    // User specified
    this.waTemplateUid = command.page();
    this.orderNbr = command.orderNumber();
    this.adminComment = command.adminComments();
    this.questionLabel = command.comments();
    this.nbsUiComponentUid = READ_ONLY_COMMENTS_ID;


    this.added(command);
  }

  public void update(PageContentCommand.UpdateTextQuestion command) {
    if (!"TEXT".equals(dataType)) {
      throw new PageContentModificationException("Targeted question is not a TEXT question");
    }
    updateSharedQuestionFields(command);

    // always updatable
    this.defaultValue = command.defaultValue();

    // updatable if not published
    if (!Character.valueOf('T').equals(publishIndCd)) {
      this.fieldSize = command.fieldLength().toString();
    }

    updated(command);
  }

  public void update(PageContentCommand.UpdateNumericQuestion command) {
    if (!"NUMERIC".equals(dataType)) {
      throw new PageContentModificationException("Targeted question is not a NUMERIC question");
    }

    updateSharedQuestionFields(command);

    // always updatable
    this.defaultValue = String.valueOf(command.defaultValue());

    // updatable if not published
    if (!Character.valueOf('T').equals(publishIndCd)) {
      this.mask = command.mask();
      this.fieldSize = String.valueOf(command.fieldLength());
      this.minValue = command.minValue();
      this.maxValue = command.maxValue();

      if (command.relatedUnitsValueSet() != null) {
        this.unitTypeCd = UnitType.CODED.toString();
        this.unitValue = command.relatedUnitsValueSet().toString();
      } else if (command.relatedUnitsLiteral() != null && !command.relatedUnitsLiteral().isBlank()) {
        this.unitTypeCd = UnitType.LITERAL.toString();
        this.unitValue = command.relatedUnitsLiteral();
      }
    }

    updated(command);
  }

  public void update(PageContentCommand.UpdateDateQuestion command) {
    if (!"DATE".equals(dataType)) {
      throw new PageContentModificationException("Targeted question is not a DATE question");
    }

    updateSharedQuestionFields(command);

    // updatable if not published
    if (!Character.valueOf('T').equals(publishIndCd)) {
      this.mask = command.mask();
      this.futureDateIndCd = command.allowFutureDates() ? 'T' : 'F';
    }

    updated(command);
  }

  public void update(PageContentCommand.UpdateCodedQuestion command) {
    if (!"CODED".equals(dataType)) {
      throw new PageContentModificationException("Targeted question is not a CODED question");
    }

    updateSharedQuestionFields(command);

    // always updatable
    this.defaultValue = command.defaultValue();

    // updatable if not published
    if (!Character.valueOf('T').equals(publishIndCd)) {
      this.codeSetGroupId = command.valueset();
    }

    updated(command);
  }


  public void update(UpdateCodedQuestionValueset command) {
    if (!"CODED".equals(dataType)) {
      throw new PageContentModificationException("Targeted question is not a CODED question");
    }

    // updatable if not published
    if (Character.valueOf('T').equals(publishIndCd)) {
      throw new PageContentModificationException("Unable to change valueset for published question");
    }

    this.codeSetGroupId = command.valueset();
    updated(command);
  }

  // Updates the fields that are common between different question types
  private void updateSharedQuestionFields(PageContentCommand.QuestionUpdate command) {
    // always updatable
    this.questionLabel = command.label();
    this.questionToolTip = command.tooltip();
    this.displayInd = command.visible() ? "T" : "F";
    this.enableInd = command.enabled() ? "T" : "F";
    this.requiredInd = command.required() ? "T" : "F";
    this.adminComment = command.adminComments();

    // updatable if not published
    if (!Character.valueOf('T').equals(publishIndCd)) {
      this.nbsUiComponentUid = command.displayControl();
    }

    if (command.displayControl() == READONLY_USER_ENTERED) {
      this.waRdbMetadatum = null;
      this.waNndMetadatum = null;
    } else {
      // Reporting
      updateReporting(command);

      // Messaging
      updateMessaging(command);
    }
  }

  private void updateReporting(PageContentCommand.QuestionUpdate command) {
    // Reporting
    if (this.waRdbMetadatum == null) {
      this.waRdbMetadatum = new WaRdbMetadata(this, command);
    } else {
      this.waRdbMetadatum.update(
          command.datamartInfo().reportLabel(),
          command.datamartInfo().dataMartColumnName(),
          command.userId(),
          command.requestedOn());
    }
  }

  private void updateMessaging(PageContentCommand.QuestionUpdate command) {
    if (!command.includedInMessage()) {
      waNndMetadatum = null;
    } else {
      this.questionOidSystemTxt = command.codeSystemName();
      this.questionOid = command.codeSystemOid();
      if (waNndMetadatum == null) {
        waNndMetadatum = new WaNndMetadatum(
            this,
            command.messageVariableId(),
            command.labelInMessage(),
            command.requiredInMessage(),
            command.hl7DataType(),
            command.userId(),
            command.requestedOn());
      } else {
        waNndMetadatum.update(
            command.messageVariableId(),
            command.labelInMessage(),
            command.requiredInMessage(),
            command.hl7DataType(),
            command.userId(),
            command.requestedOn());
      }
    }
  }

  public void update(PageContentCommand.UpdateReadOnlyComments command) {
    this.adminComment = command.adminComments();
    this.questionLabel = command.comments();
    updated(command);
  }

  public WaUiMetadata(PageContentCommand.AddHyperLink command) {
    // Defaults
    this();

    this.publishIndCd = null;
    this.entryMethod = null;

    // User specified
    this.waTemplateUid = command.page();
    this.orderNbr = command.orderNumber();
    this.adminComment = command.adminComments();
    this.questionLabel = command.label();
    this.defaultValue = command.linkUrl();
    this.nbsUiComponentUid = HYPERLINK_ID;


    this.added(command);
  }

  public void update(PageContentCommand.UpdateHyperlink command) {
    this.adminComment = command.adminComments();
    this.questionLabel = command.label();
    this.defaultValue = command.linkUrl();

    updated(command);
  }

  public void update(PageContentCommand.UpdateDefaultStaticElement command) {
    this.adminComment = command.adminComments();

    updated(command);
  }

  public WaUiMetadata(PageContentCommand.AddLineSeparator command) {
    // Defaults
    this();

    this.publishIndCd = null;
    this.entryMethod = null;


    // User specified
    this.waTemplateUid = command.page();
    this.orderNbr = command.orderNumber();
    this.adminComment = command.adminComments();
    this.nbsUiComponentUid = LINE_SEPARATOR_ID;


    this.added(command);
  }

  public WaUiMetadata(PageContentCommand.AddOrignalElectronicDocList command) {
    // Defaults
    this();

    this.publishIndCd = null;
    this.entryMethod = null;


    // User specified
    this.waTemplateUid = command.page();
    this.orderNbr = command.orderNumber();
    this.adminComment = command.adminComments();
    this.nbsUiComponentUid = ORIGINAL_ELECTRONIC_DOCUMENT_LIST_ID;


    this.added(command);
  }

  public WaUiMetadata(PageContentCommand.AddReadOnlyParticipantsList command) {
    // Defaults
    this();

    this.publishIndCd = null;
    this.entryMethod = null;


    // User specified
    this.waTemplateUid = command.page();
    this.orderNbr = command.orderNumber();
    this.adminComment = command.adminComments();
    this.nbsUiComponentUid = READ_ONLY_PARTICIPANTS_LIST_ID;


    this.added(command);
  }

  public WaUiMetadata(WaTemplate page, PageContentCommand.AddQuestion command, Integer orderNumber) {
    this();
    // Defaults
    this.standardNndIndCd = 'F';
    this.standardQuestionIndCd = 'F';
    this.publishIndCd = null;

    // User specified
    this.waTemplateUid = page;
    this.nbsUiComponentUid = command.question().getNbsUiComponentUid();
    this.questionLabel = command.question().getQuestionLabel();
    this.questionToolTip = command.question().getQuestionToolTip();
    this.orderNbr = orderNumber;
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
    this.coinfectionIndCd = command.question().getCoinfectionIndCd();

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
    if (command.question().getRdbTableNm() != null) {
      this.waRdbMetadatum = new WaRdbMetadata(page, this, command);
    }

    // If question is 'included in message', create WaNNDMetadata entry
    if (Character.valueOf('T').equals(command.question().getNndMsgInd())) {
      this.waNndMetadatum = new WaNndMetadatum(
          this,
          command.question().getQuestionIdentifierNnd(),
          command.question().getQuestionLabelNnd(),
          Character.valueOf('R').equals(command.question().getQuestionRequiredNnd()),
          command.question().getQuestionDataTypeNnd(),
          command.userId(),
          command.requestedOn());
    }

    // Audit info
    this.added(command);

  }

  public WaUiMetadata(WaTemplate page, PageContentCommand.AddTab command, Integer orderNumber) {
    this();
    this.nbsUiComponentUid = 1010L;
    this.waTemplateUid = page;
    this.questionLabel = command.label();
    setVisible(command.visible());
    this.questionIdentifier = command.identifier();
    this.orderNbr = orderNumber;

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

  public WaUiMetadata(WaTemplate page, PageContentCommand.AddSection command, Integer orderNumber) {
    this();
    this.nbsUiComponentUid = 1015L;
    this.waTemplateUid = page;
    this.questionLabel = command.label();
    setVisible(command.visible());
    this.questionIdentifier = command.identifier();
    this.orderNbr = orderNumber;

    // Audit
    added(command);
  }

  public WaUiMetadata(WaTemplate page, PageContentCommand.AddSubsection command, Integer orderNumber) {
    this();
    this.nbsUiComponentUid = 1016L;
    this.waTemplateUid = page;
    this.questionLabel = command.label();
    setVisible(command.visible());
    this.questionIdentifier = command.identifier();
    this.orderNbr = orderNumber;

    // Audit
    added(command);
  }

  public WaUiMetadata(long id, Integer orderNbr, Long nbsUiComponentUid) {
    this.id = id;
    this.orderNbr = orderNbr;
    this.nbsUiComponentUid = nbsUiComponentUid;
  }

  public void update(UpdateSection command) {
    setVisible(command.visible());
    this.questionLabel = command.label();
    updated(command);
  }

  public void update(SetQuestionRequired command) {
    setRequired(command.required());
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

  public static WaUiMetadata clone(WaUiMetadata original, WaTemplate page) {

    WaUiMetadata cloned = new WaUiMetadata(
        null,
        page,
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
        original.getBlockNm(),
        null,
        null);


    if (original.getWaRdbMetadatum() != null) {
      WaRdbMetadata rdbMetadata = WaRdbMetadata.clone(original.getWaRdbMetadatum(), page, cloned);
      cloned.setWaRdbMetadatum(rdbMetadata);
    }

    if (original.getWaNndMetadatum() != null) {
      WaNndMetadatum nndMetadata = WaNndMetadatum.clone(original.getWaNndMetadatum(), page, cloned);
      cloned.setWaNndMetadatum(nndMetadata);
    }
    return cloned;
  }

  private void setVisible(boolean visible) {
    this.displayInd = visible ? "T" : "F";
  }

  private void setRequired(boolean required) {
    this.requiredInd = required ? "T" : "F";
  }

  public void update(PageContentCommand.GroupSubsection command, int questionGroupSeqNbr) {
    this.blockNm = command.blockName().toUpperCase();
    this.questionGroupSeqNbr = questionGroupSeqNbr;
    updated(command);
  }

  public void updateQuestionBatch(PageContentCommand.GroupSubsection command, int groupSeqNbr) {
    GroupSubSectionRequest.Batch batch = command.batches().stream().filter(b -> b.id() == this.id).findFirst()
        .orElseThrow(() -> new PageContentModificationException("Failed to find batch to update"));

    this.blockNm = command.blockName().toUpperCase();
    this.batchTableAppearIndCd = batch.appearsInTable() ? 'Y' : 'N';
    this.questionGroupSeqNbr = groupSeqNbr;
    if (batch.appearsInTable()) {
      this.batchTableHeader = batch.label();
      this.batchTableColumnWidth = batch.width();
    } else {
      this.batchTableHeader = null;
      this.batchTableColumnWidth = null;
    }

    if (waRdbMetadatum != null) {
      waRdbMetadatum.groupSubsectionQuestions(command);
    }
    updated(command);
  }

  public void addToExistingGroup(String blockName, Integer groupSeqNbr, Integer pivotNumber) {
    this.blockNm = blockName;
    this.batchTableAppearIndCd = 'N';
    this.questionGroupSeqNbr = groupSeqNbr;
    if (waRdbMetadatum != null) {
      waRdbMetadatum.setBlockPivotNbr(pivotNumber);
    }
  }

  public void update(PageContentCommand.UnGroupSubsection command) {
    this.blockNm = null;
    this.questionGroupSeqNbr = null;
    updated(command);
  }

  public void ungroup(PageContentCommand.UnGroupSubsection command) {
    this.blockNm = null;
    this.batchTableAppearIndCd = null;
    this.batchTableHeader = null;
    this.batchTableColumnWidth = null;
    this.questionGroupSeqNbr = null;

    if (waRdbMetadatum != null) {
      waRdbMetadatum.unGroupSubsectionQuestions(command);
    }
    updated(command);
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
