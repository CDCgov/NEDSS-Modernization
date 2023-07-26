package gov.cdc.nbs.questionbank.page.services;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadatumRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddQuestion;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.page.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@Component
@Transactional
public class PageContentManager {

    private final WaQuestionRepository questionRepository;
    private final WaUiMetadatumRepository uiMetadatumRepository;
    private final EntityManager entityManager;

    public PageContentManager(
            final WaQuestionRepository questionRepository,
            final WaUiMetadatumRepository uiMetadatumRepository,
            final EntityManager entityManager) {
        this.questionRepository = questionRepository;
        this.uiMetadatumRepository = uiMetadatumRepository;
        this.entityManager = entityManager;
    }

    public Long addQuestion(Long pageId, AddQuestionRequest request, Long user) {
        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);
        // find the question
        WaQuestion question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new QuestionNotFoundException(request.questionId()));

        // add an entry to the wa_ui_metadata table for the specified page
        WaUiMetadatum questionPageEntry = new WaUiMetadatum(asAdd(template, question, user, request.orderNumber()));

        // update any entry with order_nbr >= new question order_nbr to be + 1
        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, request.orderNumber());

        // save the new entry
        return uiMetadatumRepository.save(questionPageEntry).getId();
    }

    private PageContentCommand.AddQuestion asAdd(WaTemplate page, WaQuestion question, Long user, Integer orderNumber) {
        if (question instanceof TextQuestionEntity t) {
            return this.asAddTextQuestion(page, t, user, orderNumber);
        } else if (question instanceof DateQuestionEntity d) {
            return this.asAddDateQuestion(page, d, user, orderNumber);
        } else if (question instanceof NumericQuestionEntity n) {
            return this.asAddNumericQuestion(page, n, user, orderNumber);
        } else if (question instanceof CodedQuestionEntity c) {
            return this.asAddCodedQuestion(page, c, user, orderNumber);
        } else {
            throw new AddQuestionException("Failed to determine question type");
        }
    }

    private AddQuestion.AddTextQuestion asAddTextQuestion(WaTemplate page, TextQuestionEntity question, Long user,
            Integer orderNumber) {
        return new AddQuestion.AddTextQuestion(
                question.getDefaultValue(),
                question.getMask(),
                question.getFieldSize(),
                page,
                question.getNbsUiComponentUid(),
                question.getQuestionLabel(),
                question.getQuestionToolTip(),
                orderNumber,
                question.getAdminComment(),
                question.getDataLocation(),
                question.getDescTxt(),
                question.getDataType(),
                question.getQuestionNm(),
                question.getQuestionIdentifier(),
                question.getQuestionOid(),
                question.getQuestionOidSystemTxt(),
                question.getGroupNm(),
                user,
                Instant.now());
    }

    private AddQuestion.AddDateQuestion asAddDateQuestion(WaTemplate page, DateQuestionEntity question, Long user,
            Integer orderNumber) {
        return new AddQuestion.AddDateQuestion(
                question.getMask(),
                question.getFutureDateIndCd(),
                page,
                question.getNbsUiComponentUid(),
                question.getQuestionLabel(),
                question.getQuestionToolTip(),
                orderNumber,
                question.getAdminComment(),
                question.getDataLocation(),
                question.getDescTxt(),
                question.getDataType(),
                question.getQuestionNm(),
                question.getQuestionIdentifier(),
                question.getQuestionOid(),
                question.getQuestionOidSystemTxt(),
                question.getGroupNm(),
                user,
                Instant.now());
    }

    private AddQuestion.AddNumericQuestion asAddNumericQuestion(WaTemplate page, NumericQuestionEntity question,
            Long user,
            Integer orderNumber) {
        return new AddQuestion.AddNumericQuestion(
                question.getMask(),
                question.getFieldSize(),
                question.getDefaultValue(),
                question.getMinValue(),
                question.getMaxValue(),
                question.getUnitTypeCd(),
                question.getUnitValue(),
                page,
                question.getNbsUiComponentUid(),
                question.getQuestionLabel(),
                question.getQuestionToolTip(),
                orderNumber,
                question.getAdminComment(),
                question.getDataLocation(),
                question.getDescTxt(),
                question.getDataType(),
                question.getQuestionNm(),
                question.getQuestionIdentifier(),
                question.getQuestionOid(),
                question.getQuestionOidSystemTxt(),
                question.getGroupNm(),
                user,
                Instant.now());
    }

    private AddQuestion.AddCodedQuestion asAddCodedQuestion(
            WaTemplate page,
            CodedQuestionEntity question,
            Long user,
            Integer orderNumber) {
        return new AddQuestion.AddCodedQuestion(
                question.getCodeSetGroupId(),
                question.getDefaultValue(),
                page,
                question.getNbsUiComponentUid(),
                question.getQuestionLabel(),
                question.getQuestionToolTip(),
                orderNumber,
                question.getAdminComment(),
                question.getDataLocation(),
                question.getDescTxt(),
                question.getDataType(),
                question.getQuestionNm(),
                question.getQuestionIdentifier(),
                question.getQuestionOid(),
                question.getQuestionOidSystemTxt(),
                question.getGroupNm(),
                user,
                Instant.now());
    }


}
