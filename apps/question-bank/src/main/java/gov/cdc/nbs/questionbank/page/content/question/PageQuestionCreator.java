package gov.cdc.nbs.questionbank.page.content.question;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddQuestion;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@Component
@Transactional
public class PageQuestionCreator {

    private final WaQuestionRepository questionRepository;
    private final WaUiMetadataRepository uiMetadatumRepository;
    private final EntityManager entityManager;

    public PageQuestionCreator(
            final WaQuestionRepository questionRepository,
            final WaUiMetadataRepository uiMetadatumRepository,
            final EntityManager entityManager) {
        this.questionRepository = questionRepository;
        this.uiMetadatumRepository = uiMetadatumRepository;
        this.entityManager = entityManager;
    }

    public Long addQuestion(Long pageId, AddQuestionRequest request, Long user) {
        if (pageId == null || request.orderNumber() == null) {
            throw new AddQuestionException("Page and order number are required");
        }
        WaTemplate template = entityManager.getReference(WaTemplate.class, pageId);
        // find the question
        WaQuestion question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new QuestionNotFoundException(request.questionId()));

        // check if question is already associated with page 
        Long count = uiMetadatumRepository.countByPageAndQuestionIdentifier(pageId, question.getQuestionIdentifier());
        if (count > 0) {
            throw new AddQuestionException("The specified question is already associated with the page");
        }

        // limit order number to max + 1 of existing questions
        Integer currentMaxOrder = uiMetadatumRepository.findMaxOrderNbrForPage(pageId);
        Integer orderNbr = request.orderNumber() > currentMaxOrder + 1 ? currentMaxOrder + 1 : request.orderNumber();

        // add 1 to any existing entry with 'order_nbr >= new entry order_nbr'
        uiMetadatumRepository.incrementOrderNbrGreaterThanOrEqualTo(pageId, request.orderNumber());

        // create an new entity 
        WaUiMetadata questionPageEntry = new WaUiMetadata(template, asAdd(template, question, user, orderNbr));

        // save the new entry
        return uiMetadatumRepository.save(questionPageEntry).getId();
    }

    private PageContentCommand.AddQuestion asAdd(
            WaTemplate page,
            WaQuestion question,
            Long user,
            Integer orderNumber) {
        return new AddQuestion(
                page.getId(),
                question,
                orderNumber,
                user,
                Instant.now());
    }

}
