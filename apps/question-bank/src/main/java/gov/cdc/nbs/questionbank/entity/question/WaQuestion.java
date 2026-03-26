package gov.cdc.nbs.questionbank.entity.question;

import static gov.cdc.nbs.questionbank.util.PageBuilderUtil.requireNonNull;

import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.CreateQuestionCommand;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor
@Getter
@Entity
@SQLRestriction("data_type in ('DATE', 'TEXT', 'NUMERIC', 'CODED')")
@Table(name = "WA_question", catalog = "NBS_ODSE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "data_type", discriminatorType = DiscriminatorType.STRING)
@SuppressWarnings(
    "javaarchitecture:S7027") //  Bidirectional mappings require knowledge of each other
public abstract class WaQuestion {

  public static final String ACTIVE = "Active";
  public static final String INACTIVE = "Inactive";

  @Id
  @Column(name = "wa_question_uid", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(
      mappedBy = "waQuestionUid",
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.PERSIST})
  private List<WaQuestionHist> questionHist;

  @Column(name = "data_cd", length = 50)
  private String dataCd;

  @Column(name = "data_type", insertable = false, updatable = false)
  private String dataType;

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

  @Column(name = "version_ctrl_nbr", nullable = false)
  private Integer versionCtrlNbr;

  @Column(name = "unit_parent_identifier", length = 20)
  private String unitParentIdentifier;

  @Column(name = "question_group_seq_nbr")
  private Integer questionGroupSeqNbr;

  @Column(name = "future_date_ind_cd")
  protected Character futureDateIndCd;

  @Column(name = "legacy_data_location", length = 150)
  private String legacyDataLocation;

  @Column(name = "repeats_ind_cd")
  private Character repeatsIndCd;

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

  @Column(name = "standard_nnd_ind_cd")
  private Character standardNndIndCd;

  @Column(name = "legacy_question_identifier", length = 50)
  private String legacyQuestionIdentifier;

  @Column(name = "other_value_ind_cd")
  protected Character otherValueIndCd;

  @Column(name = "source_nm", length = 250)
  private String sourceNm;

  @Column(name = "coinfection_ind_cd")
  private Character coinfectionIndCd;

  @Column(name = "add_user_id", nullable = false)
  private Long addUserId;

  @Column(name = "add_time", nullable = false)
  private Instant addTime;

  @Column(name = "last_chg_time")
  private Instant lastChgTime;

  @Column(name = "last_chg_user_id")
  private Long lastChgUserId;

  @Column(name = "code_set_group_id")
  protected Long codeSetGroupId;

  public abstract String getDataType();

  public abstract void update(QuestionCommand.Update command);

  protected WaQuestion(CreateQuestionCommand command) {
    // Defaults
    this.dataLocation = "NBS_CASE_ANSWER.ANSWER_TXT";
    this.standardQuestionIndCd = 'F';
    this.entryMethod = "USER";
    this.standardNndIndCd = 'F';
    this.futureDateIndCd = 'F';

    QuestionCommand.QuestionData data = command.questionData();
    if (data.questionOid() != null) {
      this.questionOid = data.questionOid().oid();
      this.questionOidSystemTxt = data.questionOid().system();
    }
    this.questionIdentifier =
        requireNonNull(command.questionData().localId(), "Question Identifier");
    this.questionLabel = requireNonNull(data.label(), "Question label");
    this.questionToolTip = requireNonNull(data.tooltip(), "Question tooltip");
    this.questionNm = requireNonNull(data.uniqueName(), "Question Name");
    this.subGroupNm = requireNonNull(data.subgroup(), "Question subgroup name");
    this.descTxt = requireNonNull(data.description(), "Description");
    this.nbsUiComponentUid = requireNonNull(data.displayControl(), "Question display control");
    this.questionType = requireNonNull(data.codeSet().toString(), "CodeSet");
    this.adminComment = data.adminComments();
  }

  /**
   * Can be used for both update and create as messaging data can always be changed
   *
   * @param data
   */
  public void setMessagingData(QuestionCommand.MessagingData data) {
    this.nndMsgInd = data.includedInMessage() ? 'T' : 'F';
    if (data.includedInMessage()) {
      this.questionIdentifierNnd = requireNonNull(data.messageVariableId(), "Message Variable Id");
      this.questionLabelNnd = requireNonNull(data.labelInMessage(), "LabelInMessage");
      this.standardNndIndCd = 'F';
      this.questionRequiredNnd = data.requiredInMessage() ? 'R' : 'O';
      this.questionDataTypeNnd = requireNonNull(data.hl7DataType(), "HL7 data type");
      this.hl7SegmentField = "OBX-3.0";
      this.orderGroupId = "2";
    } else {
      this.questionIdentifierNnd = null;
      this.questionLabelNnd = null;
      this.questionRequiredNnd = null;
      this.questionDataTypeNnd = null;
      this.hl7SegmentField = null;
      this.orderGroupId = null;
    }
  }

  /**
   * Used when creating a new entity.
   *
   * @param data
   */
  protected void setReportingData(QuestionCommand.CreateQuestionCommand command) {
    var data = command.reportingData();
    this.rdbColumnNm =
        formatAndValidateReportingField(requireNonNull(data.rdbColumnName(), "Rdb Column Name"));
    this.groupNm = "GROUP_INV";
    this.rptAdminColumnNm = requireNonNull(data.reportLabel(), "Report label");
    this.rdbTableNm = requireNonNull(data.defaultRdbTableName(), "Default RDB Table Name");
    this.userDefinedColumnNm = formatAndValidateReportingField(data.dataMartColumnName());
  }

  protected void setReportingData(QuestionCommand.Update command) {
    var data = command.reportingData();
    // Can only update rdb column name if the question is not in use
    if (!command.questionData().questionInUse()) {
      this.rdbColumnNm =
          formatAndValidateReportingField(requireNonNull(data.rdbColumnName(), "Rdb Column Name"));
    }
    this.rptAdminColumnNm = requireNonNull(data.reportLabel(), "Report label");
    this.userDefinedColumnNm = formatAndValidateReportingField(data.dataMartColumnName());
  }

  protected void created(QuestionCommand command) {
    this.addUserId = command.userId();
    this.addTime = command.requestedOn();
    this.lastChgUserId = command.userId();
    this.lastChgTime = command.requestedOn();
    this.versionCtrlNbr = 1;
    this.recordStatusCd = ACTIVE;
    this.recordStatusTime = command.requestedOn();
  }

  public void update(QuestionCommand.UpdatableQuestionData command) {
    if (!command.questionInUse() && getQuestionType().equals("LOCAL")) {
      this.questionNm = requireNonNull(command.uniqueName(), "Question Name");
    }
    this.descTxt = requireNonNull(command.description(), "Description");
    this.questionLabel = requireNonNull(command.label(), "Question label");
    this.questionToolTip = requireNonNull(command.tooltip(), "Question tooltip");
    this.nbsUiComponentUid = requireNonNull(command.displayControl(), "Question display control");
    this.adminComment = command.adminComments();
  }

  public void statusChange(QuestionCommand.SetStatus command) {
    this.recordStatusCd = command.active() ? ACTIVE : INACTIVE;
    this.recordStatusTime = command.requestedOn();
    changed(command);
  }

  protected void changed(QuestionCommand command) {
    this.lastChgUserId = command.userId();
    this.lastChgTime = command.requestedOn();
    this.versionCtrlNbr = this.getVersionCtrlNbr() + 1;
  }

  private String formatAndValidateReportingField(String s) {
    if (s == null) {
      return null;
    }
    validateAlphaNumericAndUnderscoreOnly(s);
    return s.toUpperCase().trim();
  }

  /**
   * Verify a string only contains alphanumeric or underscore characters
   *
   * @param s
   */
  private void validateAlphaNumericAndUnderscoreOnly(String s) {
    if (!s.matches("^[\\w]*$")) {
      throw new IllegalArgumentException(
          "Invalid characters specified for WaQuestion field. "
              + " Only alphanumeric and underscore characters are supported for RDB Column Name, and Data Mart Column Name."
              + " Supplied value: "
              + s);
    }
  }
}
