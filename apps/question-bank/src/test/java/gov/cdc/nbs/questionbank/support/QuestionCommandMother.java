package gov.cdc.nbs.questionbank.support;

import java.time.Instant;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest.UnitType;

public class QuestionCommandMother {
    private QuestionCommandMother() {}

    public static QuestionCommand.Update update() {
        return new QuestionCommand.Update(
                questionData(),
                "defaultValue",
                "mask",
                "field length",
                true,
                -10L,
                50L,
                99L,
                UnitType.CODED,
                "unitValue",
                reportingData(),
                messagingData(),
                100L,
                Instant.now());
    }

    private static QuestionCommand.UpdatableQuestionData questionData() {
        return new QuestionCommand.UpdatableQuestionData(
                false,
                "uniqueName",
                "descritpion",
                "label",
                "tooltip",
                1L,
                "admin Comment",
                new QuestionCommand.QuestionOid("oid", "system"));
    }

    private static QuestionCommand.ReportingData reportingData() {
        return new QuestionCommand.ReportingData(
                "report label",
                "RDB_TABLE_NM",
                "RDB_COL_NM",
                "DATA_MRT_COL_NM");
    }

    private static QuestionCommand.MessagingData messagingData() {
        return new QuestionCommand.MessagingData(
                true,
                "var id",
                "label",
                "codeSystem",
                true,
                "hl7Type");
    }
}
