package gov.cdc.nbs.questionbank.entity.question;

import static gov.cdc.nbs.questionbank.question.util.QuestionUtil.requireNonNull;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.CreateQuestionCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "WA_question", catalog = "NBS_ODSE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "data_type", discriminatorType = DiscriminatorType.STRING)
public abstract class WaQuestion {

    @Id
    @Column(name = "wa_question_uid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "data_use_cd", length = 20)
    private String dataUseCd;

    @Column(name = "question_label", length = 300)
    private String questionLabel;

    @Column(name = "question_tool_tip", length = 2000)
    private String questionToolTip;

    @Column(name = "rdb_column_nm", length = 30)
    private String rdbColumnNm;

    public void setRdbColumnNm(String rdbColumnNm) {
        this.rdbColumnNm = formatAndValidateReportingField(rdbColumnNm);
    }

    @Column(name = "part_type_cd", length = 50)
    private String partTypeCd;

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

    public void setUserDefinedColumnNm(String userDefinedColumnNm) {
        this.userDefinedColumnNm = formatAndValidateReportingField(userDefinedColumnNm);
    }

    @Column(name = "standard_nnd_ind_cd")
    private Character standardNndIndCd;

    @Column(name = "legacy_question_identifier", length = 50)
    private String legacyQuestionIdentifier;

    @Column(name = "other_value_ind_cd")
    private Character otherValueIndCd;

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

    public abstract String getDataType();

    protected WaQuestion(CreateQuestionCommand command) {
        // Defaults
        setDataLocation("NBS_CASE_ANSWER.ANSWER_TXT");
        setStandardQuestionIndCd('F');
        setEntryMethod("USER");
        setStandardQuestionIndCd('F');
        setOrderGroupId("2");
        setFutureDateIndCd('F');

        QuestionCommand.QuestionData data = command.questionData();
        if (data.questionOid() != null) {
            setQuestionOid(data.questionOid().oid());
            setQuestionOidSystemTxt(data.questionOid().system());
        }
        setQuestionIdentifier(requireNonNull(data.localId(), "LocalId must not be null"));
        setQuestionLabel(requireNonNull(data.label(), "Label must not be null"));
        setQuestionToolTip(requireNonNull(data.tooltip(), "Tooltip must not be null"));
        setQuestionNm(requireNonNull(data.uniqueName(), "UniqueName must not be null"));
        setSubGroupNm(requireNonNull(data.subgroup(), "Subgroup must not be null"));
        setDescTxt(requireNonNull(data.description(), "Description must not be null"));
        setNbsUiComponentUid(requireNonNull(data.displayControl(), "DisplayControl must not be null"));
        setQuestionType(requireNonNull(data.codeSet(), "CodeSet must not be null"));
        setAdminComment(data.adminComments());
    }

    public void setMessagingData(QuestionCommand.MessagingData data) {
        setNndMsgInd(data.includedInMessage() ? 'T' : 'F');
        if (data.includedInMessage()) {
            setQuestionIdentifierNnd(
                    requireNonNull(data.messageVariableId(), "Message Variable Id must not be null"));
            setQuestionLabelNnd(requireNonNull(data.labelInMessage(), "LabelInMessage must not be null"));
            setStandardNndIndCd('F');
            setQuestionRequiredNnd(data.requiredInMessage() ? 'R' : 'O');
            setQuestionDataTypeNnd(requireNonNull(data.hl7DataType(), "HL7 data type must not be null"));
            setHl7SegmentField("OBX-3.0");
        }
    }

    public void setReportingData(QuestionCommand.ReportingData data) {
        setRdbColumnNm(requireNonNull(data.rdbColumnName(), "Rdb Column Name must not be null"));
        setGroupNm("GROUP_INV");
        setRptAdminColumnNm(requireNonNull(data.reportLabel(), "Report label must not be null"));
        setRdbTableNm(requireNonNull(data.defaultRdbTableName(), "Default RDB Table Name must not be null"));
        setUserDefinedColumnNm(data.dataMartColumnName());
    }

    public void created(QuestionCommand command) {
        setAddUserId(command.userId());
        setAddTime(command.requestedOn());
        setLastChgUserId(command.userId());
        setLastChgTime(command.requestedOn());
        setVersionCtrlNbr(1);
        setRecordStatusCd("Active");
        setRecordStatusTime(command.requestedOn());
    }

    public void statusChange(QuestionCommand.SetStatus command) {
        setRecordStatusCd(command.active() ? "Active" : "Inactive");
        setRecordStatusTime(command.requestedOn());
        setLastChgUserId(command.userId());
        setLastChgTime(command.requestedOn());
        setVersionCtrlNbr(this.getVersionCtrlNbr() + 1);
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
                    "Invalid characters specified for WaQuestion field. " +
                            " Only alphanumeric and underscore characters are supported for RDB Column Name, and Data Mart Column Name."
                            + " Supplied value: " + s);
        }
    }

}
