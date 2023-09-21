package gov.cdc.nbs.questionbank.page.content.tab;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.OrderTabException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.OrderTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.OrderTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.DeleteTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.UpdateTabResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

@Slf4j
@Component
@Transactional
public class TabCreator {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    private static final String UPDATE_MESSAGE = " Tab Updated Successfully";

    private static final String DELETE_MESSAGE = "Tab Deleted Successfully";
    @Autowired
    private EntityManager entityManager;

    private static final long TAB = 1010L;

    private Long getCurrentHighestOrderNumber(Long waTemplateId) {
        Long maxOrderNumber = waUiMetaDataRepository.findMaxOrderNumberByTemplateUid(waTemplateId);
        return (maxOrderNumber != null) ? maxOrderNumber : 0L;
    }

    public CreateTabResponse createTab(long page, Long userId, CreateTabRequest request) throws AddTabException {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(page, userId, request);
            log.info("Saving Rule to DB");
            waUiMetaDataRepository.save(waUiMetadata);
            waUiMetaDataRepository.incrementOrderNumbers(waUiMetadata.getOrderNbr(), waUiMetadata.getId());
            return new CreateTabResponse(waUiMetadata.getId(), "Tab Created Successfully");
        } catch (Exception exception) {
            throw new AddTabException("Failed to add tab to page");
        }
    }

    public DeleteTabResponse deleteTab(Long pageNumber, Long tabId) {
        try {
            log.info("Deleting Tab");
            Integer orderNbr = waUiMetaDataRepository.getOrderNumber(tabId);
            Optional<Long> nbsComponentUidOptional =
                    waUiMetaDataRepository.findNextNbsUiComponentUid(orderNbr+1, pageNumber);
            if (nbsComponentUidOptional.isPresent()) {
                Long nbsComponentUid = nbsComponentUidOptional.get();
                if (nbsComponentUid == TAB || nbsComponentUid == null) {
                    waUiMetaDataRepository.deleteById(tabId);
                    waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, tabId);
                    return new DeleteTabResponse(tabId, DELETE_MESSAGE);
                } else {
                    throw new DeleteTabException("Conditions not satisfied");
                }
            } else {
                waUiMetaDataRepository.deleteById(tabId);
                waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, tabId);
                return new DeleteTabResponse(tabId, DELETE_MESSAGE);
            }
        }  catch(Exception exception) {
            throw new DeleteTabException("Delete Tab Exception");
        }

    }

    public UpdateTabResponse updateTab(Long tabId, UpdateTabRequest request) {
        try {
            log.info("Updating section");
            if (request.questionLabel() == null || request.visible() == null) {
                throw new UpdateSectionException("Label and visibility fields are required");
            }
            waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(),
                    request.visible(), tabId);
            return new UpdateTabResponse(tabId, UPDATE_MESSAGE);
        } catch(Exception exception) {
            throw new UpdateTabException("Update Tab Exception");
        }

    }

    private WaUiMetadata createWaUiMetadata(long pageId, Long uid, CreateTabRequest request) {
        WaTemplate page = entityManager.getReference(WaTemplate.class, pageId);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1010L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(page);
        Long nextOrderNumber = getCurrentHighestOrderNumber(pageId);

        waUiMetadata.setDisplayInd(request.visible() ? "T" : "F");
        waUiMetadata.setOrderNbr(Math.toIntExact(nextOrderNumber));
        waUiMetadata.setRequiredInd("F");
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setAddTime(Instant.now());
        waUiMetadata.setLastChgTime(Instant.now());
        waUiMetadata.setLastChgUserId(uid);
        waUiMetadata.setRecordStatusCd("Active");
        waUiMetadata.setRecordStatusTime(Instant.now());
        waUiMetadata.setVersionCtrlNbr(1);
        waUiMetadata.setStandardNndIndCd('F');
        waUiMetadata.setLocalId("NBS_1_14");
        waUiMetadata.setQuestionIdentifier("NBS_UI_4");
        waUiMetadata.setCoinfectionIndCd('F');
        waUiMetadata.setFutureDateIndCd('F');
        waUiMetadata.setStandardQuestionIndCd('F');
        waUiMetadata.setPublishIndCd('T');
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setEnableInd("T");

        return waUiMetadata;

    }

    public OrderTabResponse orderTab(Long page, OrderTabRequest request) {
        Long tabId = request.tabId();
        Integer currentPosition = request.currentPosition();
        Integer desiredPosition = request.desiredPosition();

        Integer maxOrderNumber = waUiMetaDataRepository.getMaxOrderNumber(page);
        List<Integer> orderNumberList = waUiMetaDataRepository.getOrderNumberList(page);

        if ((currentPosition <= 0) || (desiredPosition <= 0) ||
                (currentPosition > orderNumberList.size()) || (desiredPosition > orderNumberList.size())) {
            throw new OrderTabException("Invalid positions");
        }
        try {
            if(currentPosition < desiredPosition) {
                //Forward re ordering
                //Moving to last by adding max(order_number) to current order number of desired tab
                waUiMetaDataRepository.updateOrderNumber(maxOrderNumber,
                        orderNumberList.get(currentPosition - 1),
                        orderNumberList.get(currentPosition),
                        page);

                //Moving the tab to desired position
                if((desiredPosition) < orderNumberList.size()) {

                    //Moving next tabs till the desired tab position to backwards to create space in between
                    waUiMetaDataRepository.updateOrderNumber(
                            (orderNumberList.get(currentPosition) -
                                    orderNumberList.get(currentPosition - 1)) * -1,
                            orderNumberList.get(currentPosition),
                            orderNumberList.get(desiredPosition),
                            page);


                    waUiMetaDataRepository.updateOrderNumber(
                            -1 * (maxOrderNumber - orderNumberList.get(desiredPosition) + orderNumberList.get(currentPosition)),
                            (orderNumberList.get(currentPosition - 1) + maxOrderNumber),
                            10000,
                            page);
                } else {

                    //Moving next tabs till the desired tab position to backwards to create space in between
                    waUiMetaDataRepository.updateOrderNumber(
                            (orderNumberList.get(currentPosition) -
                                    orderNumberList.get(currentPosition - 1)) * -1,
                            orderNumberList.get(currentPosition),
                            maxOrderNumber + 1,
                            page);


                    waUiMetaDataRepository.updateOrderNumber(-1 *
                                    (orderNumberList.get(currentPosition) -1 ),
                            (orderNumberList.get(currentPosition - 1) + maxOrderNumber),
                            10000,
                            page);
                }

                return new OrderTabResponse(tabId, "The tab is moved from "+currentPosition+ " to "
                        +desiredPosition+" successfully");
            } else if (currentPosition > desiredPosition) {

                if(currentPosition == orderNumberList.size()) {
                    orderNumberList.add(maxOrderNumber);
                }
                //Forward re ordering
                //Moving to last by adding max(order_number) to current order number of desired tab
                waUiMetaDataRepository.updateOrderNumber(maxOrderNumber,
                        orderNumberList.get(currentPosition - 1),
                        orderNumberList.get(currentPosition),
                        page);

                //Moving next tabs till the desired tab position to backwards to create space in between
                waUiMetaDataRepository.updateOrderNumber(
                        (orderNumberList.get(currentPosition) -
                                orderNumberList.get(currentPosition - 1)),
                        orderNumberList.get(desiredPosition - 1),
                        orderNumberList.get(currentPosition),
                        page);

                //Moving the tab to desired position
                waUiMetaDataRepository.updateOrderNumber((orderNumberList.get(desiredPosition - 1) -
                                (orderNumberList.get(currentPosition - 1) + maxOrderNumber)),
                        (orderNumberList.get(currentPosition - 1) + maxOrderNumber),
                        10000,
                        page);

                return new OrderTabResponse(tabId, "The tab is moved from "+currentPosition+ " to "+desiredPosition+" successfully");
            } else {
                throw new OrderTabException("The Current position and Desired position is same");
            }
        } catch (Exception exception) {
            throw new OrderTabException("Something unknown occurred while re ordering the tab");
        }
    }
}
