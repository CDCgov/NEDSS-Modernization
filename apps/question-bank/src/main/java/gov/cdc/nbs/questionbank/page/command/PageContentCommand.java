package gov.cdc.nbs.questionbank.page.command;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import java.time.Instant;
import java.util.List;

@SuppressWarnings({"javaarchitecture:S7091"}) //  Sealed interfaces require listing implementations
public sealed interface PageContentCommand {
  long userId();

  Instant requestedOn();

  public record AddLineSeparator(
      WaTemplate page, Integer orderNumber, long userId, String adminComments, Instant requestedOn)
      implements PageContentCommand {}

  public record UpdateDefaultStaticElement(long userId, String adminComments, Instant requestedOn)
      implements PageContentCommand {}

  public record AddReadOnlyParticipantsList(
      WaTemplate page, Integer orderNumber, long userId, String adminComments, Instant requestedOn)
      implements PageContentCommand {}

  public record AddOrignalElectronicDocList(
      WaTemplate page, Integer orderNumber, long userId, String adminComments, Instant requestedOn)
      implements PageContentCommand {}

  public record AddHyperLink(
      WaTemplate page,
      Integer orderNumber,
      long userId,
      String adminComments,
      String label,
      String linkUrl,
      Instant requestedOn)
      implements PageContentCommand {}

  public record UpdateHyperlink(
      long userId, String adminComments, String label, String linkUrl, Instant requestedOn)
      implements PageContentCommand {}

  public record AddReadOnlyComments(
      WaTemplate page,
      Integer orderNumber,
      long userId,
      String comments,
      String adminComments,
      Instant requestedOn)
      implements PageContentCommand {}

  public record UpdateReadOnlyComments(
      long userId, String comments, String adminComments, Instant requestedOn)
      implements PageContentCommand {}

  public record AddQuestion(
      Long page, WaQuestion question, long subsection, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record AddTab(
      String label, boolean visible, String identifier, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record UpdateTab(String label, boolean visible, long tab, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record DeleteTab(long tabId, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record AddSection(
      String label, boolean visible, String identifier, long tab, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record UpdateSection(
      String label, boolean visible, long sectionId, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record DeleteSection(long setionId, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record AddSubsection(
      String label,
      boolean visible,
      String identifier,
      long section,
      long userId,
      Instant requestedOn)
      implements PageContentCommand {}

  public record UpdateSubsection(
      String label, boolean visible, long subsection, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record DeleteSubsection(long subsectionId, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record GroupSubsection(
      long subsection,
      String blockName,
      List<GroupSubSectionRequest.Batch> batches,
      Integer repeatingNbr,
      long userId,
      Instant requestedOn)
      implements PageContentCommand {}

  public record UnGroupSubsection(long subsection, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record DeleteQuestion(Long question, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record AddRule(
      String ruleCd,
      String errMsgTxt,
      String recordStatusCd,
      String javascriptFunction,
      String javascriptFunctionNm,
      long userId,
      Instant requestedOn)
      implements PageContentCommand {}

  public record DeleteRule(long ruleId, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public sealed interface QuestionUpdate extends PageContentCommand {
    long question();

    String label();

    String tooltip();

    boolean visible();

    boolean enabled();

    boolean required();

    long displayControl();

    ReportingInfo datamartInfo();

    boolean includedInMessage();

    String messageVariableId();

    String labelInMessage();

    String codeSystemOid();

    String codeSystemName();

    boolean requiredInMessage();

    String hl7DataType();

    String adminComments();
  }

  public record UpdateTextQuestion(
      long question,
      String label,
      String tooltip,
      boolean visible,
      boolean enabled,
      boolean required,
      long displayControl,
      // text specific
      String defaultValue,
      Integer fieldLength,
      // reporting
      ReportingInfo datamartInfo,
      // messaging
      boolean includedInMessage,
      String messageVariableId,
      String labelInMessage,
      String codeSystemOid,
      String codeSystemName,
      boolean requiredInMessage,
      String hl7DataType,
      // admin
      String adminComments,
      long userId,
      Instant requestedOn)
      implements QuestionUpdate {}

  public record UpdateNumericQuestion(
      long question,
      String label,
      String tooltip,
      boolean visible,
      boolean enabled,
      boolean required,
      long displayControl,
      // numeric specific
      String mask,
      Integer fieldLength,
      Long defaultValue,
      Long minValue,
      Long maxValue,
      String relatedUnitsLiteral,
      Long relatedUnitsValueSet,
      // reporting
      ReportingInfo datamartInfo,
      // messaging
      boolean includedInMessage,
      String messageVariableId,
      String labelInMessage,
      String codeSystemOid,
      String codeSystemName,
      boolean requiredInMessage,
      String hl7DataType,
      // admin
      String adminComments,
      long userId,
      Instant requestedOn)
      implements QuestionUpdate {}

  public record UpdateDateQuestion(
      long question,
      String label,
      String tooltip,
      boolean visible,
      boolean enabled,
      boolean required,
      long displayControl,
      // date specific
      String mask,
      boolean allowFutureDates,
      // reporting
      ReportingInfo datamartInfo,
      // messaging
      boolean includedInMessage,
      String messageVariableId,
      String labelInMessage,
      String codeSystemOid,
      String codeSystemName,
      boolean requiredInMessage,
      String hl7DataType,
      // admin
      String adminComments,
      long userId,
      Instant requestedOn)
      implements QuestionUpdate {}

  public record UpdateCodedQuestion(
      long question,
      String label,
      String tooltip,
      boolean visible,
      boolean enabled,
      boolean required,
      long displayControl,
      // coded specific
      String defaultValue,
      long valueset,
      // reporting
      ReportingInfo datamartInfo,
      // messaging
      boolean includedInMessage,
      String messageVariableId,
      String labelInMessage,
      String codeSystemOid,
      String codeSystemName,
      boolean requiredInMessage,
      String hl7DataType,
      // admin
      String adminComments,
      long userId,
      Instant requestedOn)
      implements QuestionUpdate {}

  public record UpdateCodedQuestionValueset(
      long question, long valueset, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record SetQuestionRequired(
      boolean required, long question, long userId, Instant requestedOn)
      implements PageContentCommand {}

  public record AddRuleCommand(
      long ruleId,
      String targetType,
      String ruleFunction,
      String description,
      String comparator,
      String sourceIdentifier,
      String sourceValues,
      String targetIdentifiers,
      String errorMessage,
      String javascript,
      String javascriptName,
      String expression,
      long page,
      long userId,
      Instant requestedOn)
      implements PageContentCommand {}

  public record UpdateRuleCommand(
      String targetType,
      String description,
      String comparator,
      String sourceIdentifier,
      String sourceValues,
      String targetIdentifiers,
      String errorMessage,
      String javascript,
      String javascriptName,
      String expression,
      long userId,
      Instant requestedOn)
      implements PageContentCommand {}
}
