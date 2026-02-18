package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.entity.question.CodeSet;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand;
import gov.cdc.nbs.questionbank.question.request.create.DateMask;
import gov.cdc.nbs.questionbank.question.request.create.NumericMask;
import gov.cdc.nbs.questionbank.question.request.create.TextMask;
import java.time.Instant;

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
        "unitValue",
        1000l,
        reportingData(),
        messagingData(),
        100L,
        Instant.now());
  }

  public static QuestionCommand.AddTextQuestion addTextQuestion() {
    return new QuestionCommand.AddTextQuestion(
        TextMask.TXT,
        50,
        "default value",
        new QuestionCommand.QuestionData(
            CodeSet.LOCAL,
            "TEST9900001",
            "Text Question Unique Name",
            "ADMN",
            "Text question description",
            "Text question label",
            "Text question tooltip",
            1008L,
            "Text question admin comments",
            questionOid()),
        reportingData(),
        messagingData(),
        9999000L,
        Instant.now());
  }

  public static QuestionCommand.AddDateQuestion addDateQuestion() {
    return new QuestionCommand.AddDateQuestion(
        DateMask.DATE,
        true,
        new QuestionCommand.QuestionData(
            CodeSet.PHIN,
            "TEST9900002",
            "Date Question Unique Name",
            "INV",
            "Date question description",
            "Date question label",
            "Date question tooltip",
            1008L,
            "Date question admin comments",
            questionOid()),
        reportingData(),
        messagingData(),
        9999000L,
        Instant.now());
  }

  public static QuestionCommand.AddNumericQuestion addNumericQuestion() {
    return new QuestionCommand.AddNumericQuestion(
        NumericMask.NUM,
        3,
        1l,
        0l,
        100l,
        "CCs",
        null,
        new QuestionCommand.QuestionData(
            CodeSet.LOCAL,
            "TEST9900003",
            "Numeric Question Unique Name",
            "ADMN",
            "Numeric question description",
            "Numeric question label",
            "Numeric question tooltip",
            1008L,
            "Numeric question admin comments",
            questionOid()),
        reportingData(),
        messagingData(),
        9999000L,
        Instant.now());
  }

  public static QuestionCommand.AddCodedQuestion addCodedQuestion() {
    return new QuestionCommand.AddCodedQuestion(
        900l,
        "123",
        new QuestionCommand.QuestionData(
            CodeSet.LOCAL,
            "TEST9900004",
            "Coded Question Unique Name",
            "ADMN",
            "Coded question description",
            "Coded question label",
            "Coded question tooltip",
            1008L,
            "Coded question admin comments",
            questionOid()),
        reportingData(),
        messagingData(),
        9999000L,
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
        "report label", "RDB_TABLE_NM", "ADM_RDB_COL_NM", "DATA_MRT_COL_NM");
  }

  private static QuestionCommand.MessagingData messagingData() {
    return new QuestionCommand.MessagingData(
        true, "var id", "label", "codeSystem", true, "hl7Type");
  }

  private static QuestionCommand.QuestionOid questionOid() {
    return new QuestionCommand.QuestionOid("oid", "oid system");
  }
}
