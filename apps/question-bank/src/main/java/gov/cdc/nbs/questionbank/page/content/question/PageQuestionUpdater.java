package gov.cdc.nbs.questionbank.page.content.question;


import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequest;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

@Component
@Transactional
public class PageQuestionUpdater {

  private final EntityManager entityManager;


  public PageQuestionUpdater(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public PagesQuestion updatePageQuestion(Long pageId, Long questionId, UpdatePageQuestionRequest request, Long user) {
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new PageNotFoundException(pageId);
    }
    WaUiMetadata waUiMetadata = page.updatePageQuestion(asUpdatePageQuestion(questionId, request, user));
    return toPagesQuestion(waUiMetadata);
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
        waUiMetadata.getWaRdbMetadatum()!=null?waUiMetadata.getWaRdbMetadatum().getRdbTableNm():null,
        waUiMetadata.getWaRdbMetadatum()!=null?waUiMetadata.getWaRdbMetadatum().getRdbColumnNm():null,
        waUiMetadata.getWaRdbMetadatum()!=null?waUiMetadata.getWaRdbMetadatum().getRptAdminColumnNm():null
    );

  }

  private PageContentCommand.UpdatePageQuestion asUpdatePageQuestion(Long questionId,
      UpdatePageQuestionRequest updatePageQuestionRequest, Long user) {
    return new PageContentCommand.UpdatePageQuestion(questionId, updatePageQuestionRequest, user, Instant.now());
  }

}
