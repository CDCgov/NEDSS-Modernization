package gov.cdc.nbs.questionbank.page.content.question;

import java.time.Instant;
import java.util.List;
import javax.persistence.EntityManager;

import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.question.exception.OrderQuestionException;
import gov.cdc.nbs.questionbank.page.content.question.request.OrderQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.OrderQuestionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
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

    private final WaUiMetaDataRepository waUiMetaDataRepository;

    public PageQuestionCreator(
            final WaQuestionRepository questionRepository,
            final WaUiMetadataRepository uiMetadatumRepository,
            final EntityManager entityManager,
            final WaUiMetaDataRepository waUiMetadataRepository) {
        this.questionRepository = questionRepository;
        this.uiMetadatumRepository = uiMetadatumRepository;
        this.entityManager = entityManager;
        this.waUiMetaDataRepository = waUiMetadataRepository;
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
        WaUiMetadata questionPageEntry = new WaUiMetadata(asAdd(template, question, user, orderNbr));

        // save the new entry
        return uiMetadatumRepository.save(questionPageEntry).getId();
    }

    private PageContentCommand.AddQuestion asAdd(
            WaTemplate page,
            WaQuestion question,
            Long user,
            Integer orderNumber) {
        return new AddQuestion(page, question, orderNumber, user, Instant.now());
    }
    public OrderQuestionResponse orderQuestion(Long page, OrderQuestionRequest request) {
        Long questionId = request.questionId();
        Integer currentPosition = request.currentPosition();
        Integer desiredPosition = request.desiredPosition();

        try {
            // Validate the positions and get order lists
            List<Integer> orderNumberList = validateQuestionOrder(page, request);

            // Perform the question ordering
            performQuestionOrder(page, currentPosition, desiredPosition, orderNumberList);

            return new OrderQuestionResponse(questionId, "The question moved from " + currentPosition + " to " + desiredPosition + " successfully");
        } catch (Exception exception) {
            throw new OrderQuestionException(exception.toString());
        }
    }

    private List<Integer> validateQuestionOrder(Long page, OrderQuestionRequest request) {
        Integer tabPosition = request.tabPosition();
        Integer sectionPosition = request.sectionPosition();
        Integer subSectionPosition = request.subSectionPosition();
        int currentPosition = request.currentPosition();
        int desiredPosition = request.desiredPosition();

        Integer maxOrderNumber = waUiMetaDataRepository.getMaxOrderNumber(page);

        List<Integer> tabOrderNumberList = waUiMetaDataRepository.getOrderNumberList(page);
        if (tabPosition <= 0 || tabPosition > tabOrderNumberList.size()) {
            throw new OrderQuestionException("Invalid Tab Position");
        }
        if (tabPosition == tabOrderNumberList.size()) {
            tabOrderNumberList.add(maxOrderNumber);
        }

        List<Integer> sectionOrderNumberList = waUiMetaDataRepository.getSectionOrderNumberList(page,
                tabOrderNumberList.get(tabPosition - 1),
                tabOrderNumberList.get(tabPosition));
        if (sectionPosition <= 0 || sectionPosition > sectionOrderNumberList.size()) {
            throw new OrderQuestionException("Invalid Section Position");
        }

        if (sectionPosition == sectionOrderNumberList.size()) {
            sectionOrderNumberList.add(tabOrderNumberList.get(tabPosition));
        }

        List<Integer> subSectionOrderNumberList = waUiMetaDataRepository.getSubSectionOrderNumberList(page,
                sectionOrderNumberList.get(sectionPosition - 1),
                sectionOrderNumberList.get(sectionPosition));
        if (subSectionPosition <= 0 || subSectionPosition > subSectionOrderNumberList.size()) {
            throw new OrderQuestionException("Invalid Subsection Position" + sectionOrderNumberList);
        }

        if (subSectionPosition == subSectionOrderNumberList.size()) {
            subSectionOrderNumberList.add(sectionOrderNumberList.get(sectionPosition));
        }

        List<Integer> orderNumberList = waUiMetaDataRepository.getQuestionOrderNumberList(page,
                subSectionOrderNumberList.get(subSectionPosition - 1),
                subSectionOrderNumberList.get(subSectionPosition));

        if (currentPosition <= 0 || currentPosition > orderNumberList.size() || desiredPosition <= 0 || desiredPosition > orderNumberList.size()) {
            throw new OrderQuestionException("Invalid question position" + orderNumberList);
        }
        if (desiredPosition == orderNumberList.size()) {
            orderNumberList.add(subSectionOrderNumberList.get(subSectionPosition));
        }

        return orderNumberList;
    }

    private void performQuestionOrder(Long page, Integer currentPosition, Integer desiredPosition, List<Integer> orderNumberList) {
        Integer maxOrderNumber = waUiMetaDataRepository.getMaxOrderNumber(page);

        if (!(currentPosition.equals(desiredPosition))) {
            waUiMetaDataRepository.updateQuestionOrderNumber(maxOrderNumber,
                    orderNumberList.get(currentPosition - 1),
                    page);

            if (currentPosition < desiredPosition) {
                waUiMetaDataRepository.updateOrderNumber(
                        -1,
                        orderNumberList.get(currentPosition),
                        orderNumberList.get(desiredPosition),
                        page);
            } else {
                waUiMetaDataRepository.updateOrderNumber(1,
                        orderNumberList.get(desiredPosition - 1),
                        orderNumberList.get(currentPosition - 1),
                        page);
            }

            waUiMetaDataRepository.updateQuestionOrderNumber(
                    desiredPosition - currentPosition - maxOrderNumber,
                    maxOrderNumber + orderNumberList.get(currentPosition - 1),
                    page);
        } else {
            throw new OrderQuestionException("Current and Desired positions are the same");
        }
    }

}

