package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.AddTextQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.MessagingData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.QuestionOid;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.ReportingData;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.Update;
import gov.cdc.nbs.questionbank.question.request.CreateTextQuestionRequest.TextMask;

class WaQuestionTest {

    @Test
    void rdb_column_name_should_allow_null() {
        // given a null rdb column name
        String rdbColumnName = null;

        // then setting the rdbColumnName
        WaQuestion question = emptyQuestion();
        question.setRdbColumnNm("NOT_NULL");
        assertEquals("NOT_NULL", question.getRdbColumnNm());
        question.setRdbColumnNm(rdbColumnName);

        // sets a null value
        assertNull(question.getRdbColumnNm());
    }

    @Test
    void rdb_column_name_should_only_allow_alphanumeric_or_underscore() {
        // given an rdb column name with invalid characters
        String rdbColumName = " !BAD_DATA";

        // then setting the rdbColumnNm throws an exception
        WaQuestion question = emptyQuestion();
        assertThrows(IllegalArgumentException.class, () -> question.setRdbColumnNm(rdbColumName));
    }

    @Test
    void should_uppercase_rdb_column_name() {
        // given lower case characters for the rdbColumnNm
        String rdbColumName = "something_lower_case";
        WaQuestion question = emptyQuestion();

        // then the characters are upper cased
        question.setRdbColumnNm(rdbColumName);
        assertEquals("SOMETHING_LOWER_CASE", question.getRdbColumnNm());
    }

    @Test
    void data_mart_column_name_should_only_allow_alphanumeric_or_underscore() {
        // given an rdb column name with invalid characters
        String dataMartColumn = " !BAD_DATA";

        // then setting the rdbColumnNm throws an exception
        WaQuestion question = emptyQuestion();
        assertThrows(IllegalArgumentException.class, () -> question.setUserDefinedColumnNm(dataMartColumn));
    }

    @Test
    void should_uppercase_data_mart_column_name() {
        // given lower case characters for the rdbColumnNm
        String rdbColumName = "something_lower_case";
        WaQuestion question = emptyQuestion();

        // then the characters are upper cased
        question.setUserDefinedColumnNm(rdbColumName);
        assertEquals("SOMETHING_LOWER_CASE", question.getUserDefinedColumnNm());
    }


    private WaQuestion emptyQuestion() {
        return new WaQuestion() {
            @Override
            public String getDataType() {
                return "test";
            }

            @Override
            public void update(Update command) {}
        };
    }

    @Test
    void should_initialize_correct_values() {
        AddTextQuestion command = createCommand();
        WaQuestion question = new TextQuestionEntity(command);

        assertEquals('F', question.getStandardQuestionIndCd().charValue());
        assertEquals("USER", question.getEntryMethod());
        assertEquals('F', question.getStandardNndIndCd().charValue());
        assertEquals("2", question.getOrderGroupId());
        assertEquals('F', question.getFutureDateIndCd().charValue());

        QuestionCommand.QuestionData questionData = command.questionData();
        assertEquals("NBS_CASE_ANSWER.ANSWER_TXT", question.getDataLocation());
        assertEquals(questionData.localId(), question.getQuestionIdentifier());
        assertEquals(questionData.questionOid().oid(), question.getQuestionOid());
        assertEquals(questionData.questionOid().system(), question.getQuestionOidSystemTxt());
        assertEquals(questionData.label(), question.getQuestionLabel());
        assertEquals(questionData.tooltip(), question.getQuestionToolTip());
        assertEquals(questionData.uniqueName(), question.getQuestionNm());
        assertEquals(questionData.subgroup(), question.getSubGroupNm());
        assertEquals(questionData.description(), question.getDescTxt());
        assertEquals(questionData.displayControl(), question.getNbsUiComponentUid());
        assertEquals(questionData.codeSet().toString(), question.getQuestionType());
        assertEquals(questionData.adminComments(), question.getAdminComment());

        validateMessageFields(question, command.messagingData());
        validateReportingFields(question, command.reportingData());
        validateAuditFields(question, command);
    }

    @Test
    void should_set_messaging_data_false() {
        WaQuestion question = emptyQuestion();
        question.setMessagingData(new MessagingData(
                true,
                "variableId",
                "label",
                "codeSystem",
                false,
                "hl7 type"));
        assertEquals('O', question.getQuestionRequiredNnd().charValue());
    }

    @Test
    void should_set_messaging_data_true() {
        WaQuestion question = emptyQuestion();
        question.setMessagingData(new MessagingData(
                true,
                "variableId",
                "label",
                "codeSystem",
                true,
                "hl7 type"));
        assertEquals('R', question.getQuestionRequiredNnd().charValue());
    }

    private void validateMessageFields(WaQuestion question, QuestionCommand.MessagingData data) {
        assertEquals(data.includedInMessage() ? 'T' : 'F', question.getNndMsgInd().charValue());
        assertEquals(data.messageVariableId(), question.getQuestionIdentifierNnd());
        assertEquals(data.labelInMessage(), question.getQuestionLabelNnd());
        assertEquals('F', question.getStandardNndIndCd().charValue());
        assertEquals(data.requiredInMessage() ? 'R' : 'O', question.getQuestionRequiredNnd().charValue());
        assertEquals(data.hl7DataType(), question.getQuestionDataTypeNnd());
        assertEquals("OBX-3.0", question.getHl7SegmentField());
    }

    private void validateReportingFields(WaQuestion question, QuestionCommand.ReportingData data) {
        assertEquals(data.rdbColumnName(), question.getRdbColumnNm());
        assertEquals("GROUP_INV", question.getGroupNm());
        assertEquals(data.reportLabel(), question.getRptAdminColumnNm());
        assertEquals(data.defaultRdbTableName(), question.getRdbTableNm());
        assertEquals(data.dataMartColumnName(), question.getUserDefinedColumnNm());
    }

    private void validateAuditFields(WaQuestion question, QuestionCommand command) {
        assertEquals(command.userId(), question.getAddUserId().longValue());
        assertEquals(command.requestedOn(), question.getAddTime());
        assertEquals(command.userId(), question.getLastChgUserId().longValue());
        assertEquals(command.requestedOn(), question.getLastChgTime());
        assertEquals(1, question.getVersionCtrlNbr().intValue());
        assertEquals("Active", question.getRecordStatusCd());
        assertEquals(command.requestedOn(), question.getRecordStatusTime());
    }

    private QuestionCommand.AddTextQuestion createCommand() {
        return new QuestionCommand.AddTextQuestion(
                TextMask.TXT,
                25,
                "default value",
                new QuestionCommand.QuestionData(
                        CodeSet.LOCAL,
                        "local id",
                        "unique name",
                        "subgroup",
                        "description",
                        "label",
                        "tooltip",
                        12L,
                        "admin comments",
                        questionOid()),
                reportingData(),
                messagingData(),
                9999000L,
                Instant.now());
    }

    private MessagingData messagingData() {
        return new MessagingData(
                true,
                "message variable id",
                "label in message",
                "code system",
                false,
                "hl7 type");
    }

    private ReportingData reportingData() {
        return new ReportingData(
                "report label",
                "RDB_TABLE_NAME",
                "RDB_COLUMN_NAME",
                "DATA_MART_COLUMN_NAME");
    }

    private QuestionOid questionOid() {
        return new QuestionOid(
                "oid",
                "oid system");
    }
}
