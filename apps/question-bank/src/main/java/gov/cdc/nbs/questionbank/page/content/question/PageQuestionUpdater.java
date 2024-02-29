package gov.cdc.nbs.questionbank.page.content.question;


import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateCodedQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateDateQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateNumericQuestion;
import gov.cdc.nbs.questionbank.page.content.question.exception.UpdatePageQuestionException;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageCodedQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageDateQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageNumericQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageTextQuestionRequest;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.MessagingInfo;
import gov.cdc.nbs.questionbank.valueset.concept.ConceptFinder;
import gov.cdc.nbs.questionbank.valueset.model.Concept;

@Component
@Transactional
public class PageQuestionUpdater {

  private final EntityManager entityManager;
  private final ConceptFinder conceptFinder;

  public PageQuestionUpdater(
      final EntityManager entityManager,
      final ConceptFinder conceptFinder) {
    this.entityManager = entityManager;
    this.conceptFinder = conceptFinder;
  }

  public PagesQuestion update(
      Long pageId,
      Long questionId,
      UpdatePageQuestionRequest request,
      Long user) {
    validateRequest(request);
    Concept codeSystem = findCodeSystem(request.messagingInfo());
    WaTemplate page = findPage(pageId);
    WaUiMetadata metadata = page.updateQuestion(asUpdate(request, codeSystem, questionId, user));
    return toPagesQuestion(metadata);
  }


  private PageContentCommand.QuestionUpdate asUpdate(
      UpdatePageQuestionRequest request,
      Concept codeSystem,
      Long questionId,
      Long user) {
    if (request instanceof UpdatePageTextQuestionRequest textRequest) {
      return asUpdate(textRequest, codeSystem, questionId, user);
    } else if (request instanceof UpdatePageNumericQuestionRequest numericRequest) {
      return asUpdate(numericRequest, codeSystem, questionId, user);
    } else if (request instanceof UpdatePageDateQuestionRequest dateRequest) {
      return asUpdate(dateRequest, codeSystem, questionId, user);
    } else if (request instanceof UpdatePageCodedQuestionRequest codedRequest) {
      return asUpdate(codedRequest, codeSystem, questionId, user);
    } else {
      throw new UpdatePageQuestionException("Failed to process update question");
    }
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
    if (request.datamartInfo().reportLabel() == null || request.datamartInfo().reportLabel().trim().length() == 0) {
      throw new UpdatePageQuestionException("Default label in report is a required field");
    }
    if (request.messagingInfo() == null) {
      throw new UpdatePageQuestionException("Included in Message is a required field");
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
      return conceptFinder.find("CODE_SYSTEM", conceptId)
          .orElseThrow(() -> new UpdatePageQuestionException("Failed to find code system: " + conceptId));
    }
    return null;
  }

  private PageContentCommand.UpdateTextQuestion asUpdate(
      UpdatePageTextQuestionRequest request,
      Concept codeSystem,
      Long questionId,
      Long user) {
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
        request.datamartInfo(),
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
      UpdatePageNumericQuestionRequest request,
      Concept codeSystem,
      Long questionId,
      Long user) {
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
        request.datamartInfo(),
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
      UpdatePageDateQuestionRequest request,
      Concept codeSystem,
      Long questionId,
      Long user) {
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
        request.datamartInfo(),
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
      UpdatePageCodedQuestionRequest request,
      Concept codeSystem,
      Long questionId,
      Long user) {
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
        request.datamartInfo(),
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

  private PagesQuestion toPagesQuestion(WaUiMetadata waUiMetadata) {
    return new PagesQuestion(
        waUiMetadata.getId(),
        waUiMetadata.getStandardQuestionIndCd() != null && waUiMetadata.getStandardQuestionIndCd() == 'T',
        waUiMetadata.getQuestionType(),
        waUiMetadata.getQuestionIdentifier(),
        waUiMetadata.getQuestionLabel(),
        waUiMetadata.getOrderNbr(),
        waUiMetadata.getSubGroupNm(),
        waUiMetadata.getDescTxt(),
        waUiMetadata.getCoinfectionIndCd() != null && waUiMetadata.getCoinfectionIndCd() == 'T',
        waUiMetadata.getDataType(),
        waUiMetadata.getMask(),
        waUiMetadata.getFutureDateIndCd() != null && waUiMetadata.getFutureDateIndCd() == 'T',
        waUiMetadata.getQuestionToolTip(),
        "T".equals(waUiMetadata.getDisplayInd()),
        "T".equals(waUiMetadata.getEnableInd()),
        "T".equals(waUiMetadata.getRequiredInd()),
        waUiMetadata.getDefaultValue(),
        waUiMetadata.getCodeset() != null ? waUiMetadata.getCodeset().getValueSetNm() : null,
        waUiMetadata.getNbsUiComponentUid(),
        waUiMetadata.getAdminComment(),
        waUiMetadata.getFieldSize(),
        waUiMetadata.getWaRdbMetadatum() != null ? waUiMetadata.getWaRdbMetadatum().getRdbTableNm() : null,
        waUiMetadata.getWaRdbMetadatum() != null ? waUiMetadata.getWaRdbMetadatum().getRdbColumnNm() : null,
        waUiMetadata.getWaRdbMetadatum() != null ? waUiMetadata.getWaRdbMetadatum().getRptAdminColumnNm() : null,
        waUiMetadata.getWaRdbMetadatum() != null ? waUiMetadata.getWaRdbMetadatum().getUserDefinedColumnNm() : null,
        Character.valueOf('T').equals(waUiMetadata.getPublishIndCd()));
  }

}
