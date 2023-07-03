package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;

public class QuestionEntityMother {

    private QuestionEntityMother() {

    }

    public static TextQuestionEntity textQuestion() {
        Instant now = Instant.now();
        TextQuestionEntity q = new TextQuestionEntity();
        q.setId(1L);
        q.setQuestionIdentifier("TEST9900001");
        q.setAdminComment("Text question admin comments");
        q.setQuestionType("LOCAL");
        q.setDescTxt("Text question description");
        q.setNbsUiComponentUid(1008L);
        q.setFieldSize("50");
        q.setQuestionLabel("Text question label");
        q.setMask("TXT");
        q.setSubGroupNm("ADMN");
        q.setQuestionToolTip("Text question tooltip");
        q.setQuestionNm("Text Question Unique Name");
        q.setAddUserId(1L);
        q.setAddTime(now);
        q.setLastChgUserId(1L);
        q.setLastChgTime(now);
        q.setRecordStatusCd(WaQuestion.ACTIVE);
        q.setRecordStatusTime(now);
        q.setVersionCtrlNbr(1);

        // Messaging
        q.setNndMsgInd('T');
        q.setQuestionIdentifierNnd("Text Question Msg Identifier");
        q.setQuestionLabelNnd("Text question message label");
        q.setStandardNndIndCd('F');
        q.setQuestionRequiredNnd('O');
        q.setQuestionDataTypeNnd("CE");

        // Data mart
        q.setRdbColumnNm("TEXT_RDB_COL_NM");
        q.setGroupNm("GROUP_INV");
        q.setRptAdminColumnNm("Text Report Label");
        q.setRdbTableNm("TXT_RDB_TABLE");
        q.setUserDefinedColumnNm("TXT_DATA_COL_NM");

        return q;
    }

    public static DateQuestionEntity dateQuestion() {
        Instant now = Instant.now();
        DateQuestionEntity q = new DateQuestionEntity();
        q.setId(2L);
        q.setQuestionIdentifier("TEST9900002");
        q.setAdminComment("Date question admin comments");
        q.setQuestionType("LOCAL");
        q.setDescTxt("Date question description");
        q.setNbsUiComponentUid(1009L);
        q.setFutureDateIndCd('T');
        q.setQuestionLabel("Date question label");
        q.setMask("DATE");
        q.setSubGroupNm("ADMN");
        q.setQuestionToolTip("Date question tooltip");
        q.setQuestionNm("Date Question Unique Name");
        q.setAddUserId(1L);
        q.setAddTime(now);
        q.setLastChgUserId(1L);
        q.setLastChgTime(now);
        q.setRecordStatusCd("Active");
        q.setRecordStatusTime(now);
        q.setVersionCtrlNbr(1);

        // Messaging
        q.setNndMsgInd('T');
        q.setQuestionIdentifierNnd("Date Question Msg Identifier");
        q.setQuestionLabelNnd("Date question message label");
        q.setStandardNndIndCd('F');
        q.setQuestionRequiredNnd('O');
        q.setQuestionDataTypeNnd("CE");

        // Data mart
        q.setRdbColumnNm("DATE_RDB_COL_NM");
        q.setGroupNm("GROUP_INV");
        q.setRptAdminColumnNm("Date Report Label");
        q.setRdbTableNm("DATE_RDB_TABLE");
        q.setUserDefinedColumnNm("DATE_DATA_COL_NM");

        return q;
    }


    public static NumericQuestionEntity numericQuestion() {
        Instant now = Instant.now();
        NumericQuestionEntity q = new NumericQuestionEntity();
        q.setId(3L);
        q.setQuestionIdentifier("TEST9900003");
        q.setAdminComment("Numeric question admin comments");
        q.setQuestionType("LOCAL");
        q.setDescTxt("Numeric question description");
        q.setNbsUiComponentUid(1008L);
        q.setFutureDateIndCd('T');
        q.setQuestionLabel("Numeric question label");
        q.setMask("NUM");
        q.setFieldSize("3");
        q.setDefaultValue("1");
        q.setMinValue(0L);
        q.setMaxValue(100L);
        q.setUnitTypeCd("Literal");
        q.setUnitValue("CCs");
        q.setSubGroupNm("ADMN");
        q.setQuestionToolTip("Numeric question tooltip");
        q.setQuestionNm("Numeric Question Unique Name");
        q.setAddUserId(1L);
        q.setAddTime(now);
        q.setLastChgUserId(1L);
        q.setLastChgTime(now);
        q.setRecordStatusCd("Active");
        q.setRecordStatusTime(now);
        q.setVersionCtrlNbr(1);

        // Messaging
        q.setNndMsgInd('T');
        q.setQuestionIdentifierNnd("Numeric Question Msg Identifier");
        q.setQuestionLabelNnd("Numeric question message label");
        q.setStandardNndIndCd('F');
        q.setQuestionRequiredNnd('O');
        q.setQuestionDataTypeNnd("CE");

        // Data mart
        q.setRdbColumnNm("NUM_RDB_COL_NM");
        q.setGroupNm("GROUP_INV");
        q.setRptAdminColumnNm("Numeric Report Label");
        q.setRdbTableNm("NUM_RDB_TABLE");
        q.setUserDefinedColumnNm("NUM_DATA_COL_NM");

        return q;
    }

    public static CodedQuestionEntity codedQuestion() {
        Instant now = Instant.now();
        CodedQuestionEntity q = new CodedQuestionEntity();
        q.setId(4L);
        q.setQuestionIdentifier("TEST9900004");
        q.setAdminComment("Coded question admin comments");
        q.setQuestionType("LOCAL");
        q.setDescTxt("Coded question description");
        q.setNbsUiComponentUid(1008L);
        q.setQuestionLabel("Coded question label");
        q.setDefaultValue("123");
        q.setCodeSetGroupId(900L);
        q.setSubGroupNm("ADMN");
        q.setQuestionToolTip("Coded question tooltip");
        q.setQuestionNm("Coded Question Unique Name");
        q.setAddUserId(1L);
        q.setAddTime(now);
        q.setLastChgUserId(1L);
        q.setLastChgTime(now);
        q.setRecordStatusCd("Active");
        q.setRecordStatusTime(now);
        q.setVersionCtrlNbr(1);

        // Messaging
        q.setNndMsgInd('T');
        q.setQuestionIdentifierNnd("Coded Question Msg Identifier");
        q.setQuestionLabelNnd("Coded question message label");
        q.setStandardNndIndCd('F');
        q.setQuestionRequiredNnd('O');
        q.setQuestionDataTypeNnd("CE");

        // Data mart
        q.setRdbColumnNm("CODE_RDB_COL_NM");
        q.setGroupNm("GROUP_INV");
        q.setRptAdminColumnNm("Coded Report Label");
        q.setRdbTableNm("CODE_RDB_TABLE");
        q.setUserDefinedColumnNm("CODE_DATA_COL_NM");

        return q;
    }
}
