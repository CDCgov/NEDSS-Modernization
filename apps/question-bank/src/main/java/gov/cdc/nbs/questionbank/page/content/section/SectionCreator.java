package gov.cdc.nbs.questionbank.page.content.section;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.page.content.section.exception.OrderSectionException;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.OrderSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.DeleteSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.OrderSectionResponse;
import gov.cdc.nbs.questionbank.page.content.section.response.UpdateSectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.section.exception.AddSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.CreateSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
public class SectionCreator {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    private static final String UPDATE_MESSAGE = "Section updated successfully";

    private static final String DELETE_MESSAGE = "Section deleted successfully";

    private static final long TAB = 1010L;
    private static final long SECTION = 1015L;

    @Autowired
    private EntityManager entityManager;

    public CreateSectionResponse createSection(long pageId, Long userId, CreateSectionRequest request) {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(pageId, userId, request);
            log.info("Updating Wa_UI_metadata table by adding new section");
            waUiMetaDataRepository.incrementOrderNumbers(waUiMetadata.getOrderNbr(), waUiMetadata.getId());
            waUiMetaDataRepository.save(waUiMetadata);
            return new CreateSectionResponse(waUiMetadata.getId(), "Section Created Successfully");
        } catch (Exception exception) {
            throw new AddSectionException("Add Section exception");
        }

    }

    public DeleteSectionResponse deleteSection(Long pageNumber, Long sectionId) {
        try {
            log.info("Deleting Section");
            Integer orderNbr = waUiMetaDataRepository.getOrderNumber(sectionId);
            Optional<Long> nbsComponentUidOptional =
                    waUiMetaDataRepository.findNextNbsUiComponentUid(orderNbr+1, pageNumber);
            if (nbsComponentUidOptional.isPresent()) {
                long nbsComponentUid = nbsComponentUidOptional.get();
                if (nbsComponentUid == TAB ||nbsComponentUid == SECTION) {
                    waUiMetaDataRepository.deleteById(sectionId);
                    waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, sectionId);
                    return new DeleteSectionResponse(sectionId, DELETE_MESSAGE);
                } else {
                    throw new DeleteSectionException("Conditions not satisfied");
                }
            } else {
                waUiMetaDataRepository.deleteById(sectionId);
                waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, sectionId);
                return new DeleteSectionResponse(sectionId, DELETE_MESSAGE);
            }
        } catch(Exception exception) {
            throw new DeleteSectionException("Delete Section Exception");
        }

    }
    public UpdateSectionResponse updateSection(Long sectionId, UpdateSectionRequest request) {
        try {
            log.info("Updating section");
            if (request.questionLabel() == null || request.visible() == null) {
                throw new UpdateSectionException("Label and visibility fields are required");
            }
            waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(),
                    request.visible(), sectionId);
            return new UpdateSectionResponse(sectionId, UPDATE_MESSAGE);
        } catch(Exception exception) {
            throw new UpdateSectionException("Update Section Exception");
        }

    }


    private WaUiMetadata createWaUiMetadata(long pageId, Long uid, CreateSectionRequest request) {
        WaTemplate page = entityManager.getReference(WaTemplate.class, pageId);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1015L);
        waUiMetadata.setWaTemplateUid(page);
        Long nextOrderNumber = waUiMetaDataRepository.findOrderNbr(
                request.tabId(),
                pageId,
                1010L);
        if (nextOrderNumber == null) {
            nextOrderNumber = waUiMetaDataRepository.findOrderNbr_2(
                    request.tabId(),
                    pageId,
                    1010L);
        }
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setOrderNbr(Math.toIntExact(nextOrderNumber));
        waUiMetadata.setDisplayInd(request.visible() ? "T" : "F");
        waUiMetadata.setPublishIndCd('T');
        waUiMetadata.setEnableInd("T");
        waUiMetadata.setRequiredInd("F");
        waUiMetadata.setCoinfectionIndCd('F');
        waUiMetadata.setFutureDateIndCd('F');
        waUiMetadata.setStandardQuestionIndCd('F');
        waUiMetadata.setStandardNndIndCd('F');
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setAddTime(Instant.now());
        waUiMetadata.setLastChgTime(Instant.now());
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setRecordStatusCd("Active");
        waUiMetadata.setLastChgUserId(uid);
        waUiMetadata.setVersionCtrlNbr(1);
        waUiMetadata.setRecordStatusTime(Instant.now());
        waUiMetadata.setQuestionIdentifier("NBS_1_15");
        waUiMetadata.setLocalId("NBS_1_15");

        return waUiMetadata;

    }


    public OrderSectionResponse orderSection(Long page, OrderSectionRequest request) {
        Long sectionId = request.sectionId();
        Integer currentPosition = request.currentPosition();
        Integer desiredPosition = request.desiredPosition();

        Integer maxOrderNumber = waUiMetaDataRepository.getMaxOrderNumber(page);

        List<Integer> tabOrderNumberList = waUiMetaDataRepository.getOrderNumberList(page);
        Integer orderNumber;
        if (tabOrderNumberList.size() == request.tabPosition()) {
            orderNumber = maxOrderNumber;
        } else {
            orderNumber = tabOrderNumberList.get(request.tabPosition());
        }
        List<Integer> orderNumberList = waUiMetaDataRepository.getSectionOrderNumberList(page,
                tabOrderNumberList.get(request.tabPosition() - 1),
                orderNumber);

        if (currentPosition <= 0 || desiredPosition <=0
        ||currentPosition > orderNumberList.size() || desiredPosition >= orderNumberList.size() ||
        request.tabPosition() <= 0 || request.tabPosition() > tabOrderNumberList.size()) {
            throw new OrderSectionException("Invalid Positions");
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



                    //Moving next tabs till the desired tab position to backwards to create space in between
                    waUiMetaDataRepository.updateOrderNumber(
                            (orderNumberList.get(currentPosition - 1) -
                                    orderNumberList.get(currentPosition)),
                            orderNumberList.get(currentPosition),
                            orderNumberList.get(desiredPosition),
                            page);


                    waUiMetaDataRepository.updateOrderNumber(
                            orderNumberList.get(desiredPosition) -
                                    orderNumberList.get(currentPosition)
                                    - maxOrderNumber,
                            (orderNumberList.get(currentPosition - 1) + maxOrderNumber),
                            10000,
                            page);

                return new OrderSectionResponse(sectionId, "The section is moved from "+currentPosition+ " to "
                        +desiredPosition+" successfully");
            } else if (currentPosition > desiredPosition) {

                if(currentPosition == orderNumberList.size()) {
                    if (request.tabPosition() < tabOrderNumberList.size()) {
                        orderNumberList.add(tabOrderNumberList.get(request.tabPosition()));
                    } else if (request.tabPosition() == tabOrderNumberList.size()){
                        orderNumberList.add(maxOrderNumber);
                    } else {
                        throw new OrderSectionException("Invalid Tab position");
                    }
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
                waUiMetaDataRepository.updateOrderNumber((orderNumberList.get(currentPosition - 1) + maxOrderNumber -
                                orderNumberList.get(desiredPosition - 1)) * -1,
                        (orderNumberList.get(currentPosition - 1) + maxOrderNumber),
                        10000,
                        page);

                return new OrderSectionResponse(sectionId, "The section is moved from "+currentPosition+ " to "+desiredPosition+" successfully");
            } else {
                throw new OrderSectionException("The Current position and Desired position is same");
            }
        } catch (Exception exception) {
            throw new OrderSectionException("Something unknown occurred re ordering moving section");
        }
    }
}

