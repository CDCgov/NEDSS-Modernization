package gov.cdc.nbs.questionbank.entity.question;

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

    @Column(name = "add_user_id", nullable = false)
    private Long addUserId;

    @Column(name = "add_time", nullable = false)
    private Instant addTime;

    @Column(name = "last_chg_time")
    private Instant lastChgTime;

    @Column(name = "last_chg_user_id")
    private Long lastChgUserId;

    public abstract String getDataType();

    public WaQuestion(QuestionCommand command) {
        setDataLocation("NBS_CASE_ANSWER.ANSWER_TXT");
        setQuestionIdentifier(command.localId());
        setQuestionOid(command.questionOid().oid());
        setQuestionOidSystemTxt(command.questionOid().system());
        setQuestionLabel(command.label());
        setQuestionToolTip(command.tooltip());
        setQuestionNm(command.uniqueName());
        setSubGroupNm(command.subgroup());
        setDescTxt(command.description());
        setNbsUiComponentUid(command.displayControl());
        setStandardQuestionIndCd('F');
        setEntryMethod("USER");
        setQuestionType(command.codeSet());
        setAdminComment(command.adminComments());
        setStandardQuestionIndCd('F');
        setOrderGroupId("2");
        setFutureDateIndCd('F');
    }

    public void setMessagingData(QuestionCommand.MessagingData messagingData) {
        setNndMsgInd(messagingData.includedInMessage() ? 'T' : 'F');
        setQuestionIdentifierNnd(messagingData.messageVariableId());
        setQuestionLabelNnd(messagingData.labelInMessage());
        setStandardNndIndCd('F');
        setQuestionRequiredNnd(messagingData.requiredInMessage() ? 'R' : 'O');
        setQuestionDataTypeNnd(messagingData.hl7DataType());
        setHl7SegmentField("OBX-3.0");
    }

    public void setReportingData(QuestionCommand.ReportingData reportingData) {
        setRdbColumnNm(reportingData.rdbColumnName());
        setGroupNm("GROUP_INV");
        setRptAdminColumnNm(reportingData.reportLabel());
        setRdbTableNm(reportingData.defaultRdbTableName());
        setUserDefinedColumnNm(reportingData.dataMartColumnName());
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
        if (!s.matches("^[a-zA-Z0-9_]*$")) {
            throw new IllegalArgumentException(
                    "Invalid characters specified for WaQuestion field. Only alphanumeric and underscore characters are supported. Supplied value: "
                            + s);
        }
    }

}
