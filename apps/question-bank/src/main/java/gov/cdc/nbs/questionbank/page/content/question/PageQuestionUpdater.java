package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.SetQuestionRequired;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateCodedQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateCodedQuestionValueset;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateDateQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateNumericQuestion;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.question.exception.UpdatePageQuestionException;
import gov.cdc.nbs.questionbank.page.content.question.model.EditableQuestion;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageCodedQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageCodedQuestionValuesetRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageDateQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageNumericQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequiredRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageQuestionUpdater {

  private final EntityManager entityManager;
  private final ConceptFinder conceptFinder;
  private final EditableQuestionFinder finder;

  public PageQuestionUpdater(
      final EntityManager entityManager,
      final ConceptFinder conceptFinder,
      final EditableQuestionFinder finder) {
    this.entityManager = entityManager;
    this.conceptFinder = conceptFinder;
    this.finder = finder;
  }

  public EditableQuestion update(
      Long pageId, Long questionId, UpdatePageQuestionRequest request, Long user) {
    validateRequest(request);
    Concept codeSystem = findCodeSystem(request.messagingInfo());
    WaTemplate page = findPage(pageId);
    page.updateQuestion(asUpdate(request, codeSystem, questionId, user));
    entityManager.flush();
    return finder.find(pageId, questionId);
  }

  public EditableQuestion setRequired(
      Long pageId, Long questionId, UpdatePageQuestionRequiredRequest request, long user) {
    WaTemplate page = findPage(pageId);
    page.updateRequired(
        asUpdate(request.required(), questionId, user), question -> findQuestion(question, pageId));

    return finder.find(pageId, questionId);
  }

  WaUiMetadata findQuestion(long questionId, long pageId) {
    WaUiMetadata metadata = entityManager.find(WaUiMetadata.class, questionId);
    if (metadata == null || metadata.getWaTemplateUid().getId() != pageId) {
      throw new PageContentModificationException("Failed to find question");
    }
    return metadata;
  }

  public EditableQuestion update(
      Long pageId, Long questionId, UpdatePageCodedQuestionValuesetRequest request, Long user) {
    if (request == null) {
      throw new UpdatePageQuestionException("Invalid request");
    }

    WaTemplate page = findPage(pageId);
    page.updateQuestionValueset(asUpdate(questionId, request, user));
    return finder.find(pageId, questionId);
  }

  private PageContentCommand.QuestionUpdate asUpdate(
      UpdatePageQuestionRequest request, Concept codeSystem, Long questionId, Long user) {
    return switch (request) {
      case UpdatePageTextQuestionRequest textRequest ->
          asUpdate(textRequest, codeSystem, questionId, user);
      case UpdatePageNumericQuestionRequest numericRequest ->
          asUpdate(numericRequest, codeSystem, questionId, user);
      case UpdatePageDateQuestionRequest dateRequest ->
          asUpdate(dateRequest, codeSystem, questionId, user);
      case UpdatePageCodedQuestionRequest codedRequest ->
          asUpdate(codedRequest, codeSystem, questionId, user);
      case null, default ->
          throw new UpdatePageQuestionException("Failed to process update question");
    };
  }

  private void validateRequest(UpdatePageQuestionRequest request) {
    if (request == null) {
      throw new UpdatePageQuestionException("Invalid request");
    }
    if (request.label() == null) {
      throw new UpdatePageQuestionException("Question label is a required field");
    }
    if (request.tooltip() == null) {
      throw new UpdatePageQuestionException("Tooltip is a required field");
    }

    if (request.displayControl() != 1026) { // Readonly User entered text, number, or date
      if (request.dataMartInfo() == null
          || request.dataMartInfo().reportLabel() == null
          || request.dataMartInfo().reportLabel().trim().isEmpty()) {
        throw new UpdatePageQuestionException("Default label in report is a required field");
      }
      if (request.messagingInfo() == null) {
        throw new UpdatePageQuestionException("Included in Message is a required field");
      }
    }
  }

  private WaTemplate findPage(Long pageId) {
    final WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new UpdatePageQuestionException("Failed to find page");
    }
    return page;
  }

  private Concept findCodeSystem(MessagingInfo info) {
    if (info.includedInMessage()) {
      String conceptId = info.codeSystem();
      return conceptFinder
          .find("CODE_SYSTEM", conceptId)
          .orElseThrow(
              () -> new UpdatePageQuestionException("Failed to find code system: " + conceptId));
    }
    return null;
  }

  private PageContentCommand.UpdateTextQuestion asUpdate(
      UpdatePageTextQuestionRequest request, Concept codeSystem, Long questionId, Long user) {
    return new PageContentCommand.UpdateTextQuestion(
        questionId,
        request.label(),
        request.tooltip(),
        request.visible(),
        request.enabled(),
        request.required(),
        request.displayControl(),
        request.defaultValue(),
        request.fieldLength(),
        request.dataMartInfo(),
        request.messagingInfo().includedInMessage(),
        request.messagingInfo().messageVariableId(),
        request.messagingInfo().labelInMessage(),
        codeSystem != null ? codeSystem.longName() : null,
        codeSystem != null ? codeSystem.display() : null,
        request.messagingInfo().requiredInMessage(),
        request.messagingInfo().hl7DataType(),
        request.adminComments(),
        user,
        Instant.now());
  }

  private UpdateNumericQuestion asUpdate(
      UpdatePageNumericQuestionRequest request, Concept codeSystem, Long questionId, Long user) {
    return new PageContentCommand.UpdateNumericQuestion(
        questionId,
        request.label(),
        request.tooltip(),
        request.visible(),
        request.enabled(),
        request.required(),
        request.displayControl(),
        request.mask().toString(),
        request.fieldLength(),
        request.defaultValue(),
        request.minValue(),
        request.maxValue(),
        request.relatedUnitsLiteral(),
        request.relatedUnitsValueSet(),
        request.dataMartInfo(),
        request.messagingInfo().includedInMessage(),
        request.messagingInfo().messageVariableId(),
        request.messagingInfo().labelInMessage(),
        codeSystem != null ? codeSystem.longName() : null,
        codeSystem != null ? codeSystem.display() : null,
        request.messagingInfo().requiredInMessage(),
        request.messagingInfo().hl7DataType(),
        request.adminComments(),
        user,
        Instant.now());
  }

  private UpdateDateQuestion asUpdate(
      UpdatePageDateQuestionRequest request, Concept codeSystem, Long questionId, Long user) {
    return new PageContentCommand.UpdateDateQuestion(
        questionId,
        request.label(),
        request.tooltip(),
        request.visible(),
        request.enabled(),
        request.required(),
        request.displayControl(),
        request.mask().toString(),
        request.allowFutureDates(),
        request.dataMartInfo(),
        request.messagingInfo().includedInMessage(),
        request.messagingInfo().messageVariableId(),
        request.messagingInfo().labelInMessage(),
        codeSystem != null ? codeSystem.longName() : null,
        codeSystem != null ? codeSystem.display() : null,
        request.messagingInfo().requiredInMessage(),
        request.messagingInfo().hl7DataType(),
        request.adminComments(),
        user,
        Instant.now());
  }

  private UpdateCodedQuestion asUpdate(
      UpdatePageCodedQuestionRequest request, Concept codeSystem, Long questionId, Long user) {
    return new PageContentCommand.UpdateCodedQuestion(
        questionId,
        request.label(),
        request.tooltip(),
        request.visible(),
        request.enabled(),
        request.required(),
        request.displayControl(),
        request.defaultValue(),
        request.valueSet(),
        request.dataMartInfo(),
        request.messagingInfo().includedInMessage(),
        request.messagingInfo().messageVariableId(),
        request.messagingInfo().labelInMessage(),
        codeSystem != null ? codeSystem.longName() : null,
        codeSystem != null ? codeSystem.display() : null,
        request.messagingInfo().requiredInMessage(),
        request.messagingInfo().hl7DataType(),
        request.adminComments(),
        user,
        Instant.now());
  }

  private SetQuestionRequired asUpdate(boolean required, long questionId, long user) {
    return new PageContentCommand.SetQuestionRequired(required, questionId, user, Instant.now());
  }

  private UpdateCodedQuestionValueset asUpdate(
      long questionId, UpdatePageCodedQuestionValuesetRequest request, Long user) {
    return new PageContentCommand.UpdateCodedQuestionValueset(
        questionId, request.valueset(), user, Instant.now());
  }
}
