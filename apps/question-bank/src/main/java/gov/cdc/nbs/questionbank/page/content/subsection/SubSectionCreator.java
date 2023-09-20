package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.OrderSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.DeleteSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.OrderSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.DeleteSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.OrderSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.UpdateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import javax.persistence.EntityManager;

@Slf4j
@Service
@Transactional
public class SubSectionCreator {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    private static final String UPDATE_MESSAGE = "SubSection Updated Successfully";
    @Autowired
    private EntityManager entityManager;

    public CreateSubSectionResponse createSubSection(long pageId, Long userId, CreateSubSectionRequest request) {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(pageId, userId, request);
            // Increment orderNbr for everything beginning at the new subSection orderNbr to "make room" to insert the subsection
            waUiMetaDataRepository.incrementOrderNumbers(waUiMetadata.getOrderNbr(), pageId);
            waUiMetaDataRepository.save(waUiMetadata);
            log.info("Updating Wa_UI_metadata table by adding new subsection");
            return new CreateSubSectionResponse(waUiMetadata.getId(), "SubSection Created Successfully");
        } catch (Exception exception) {
            throw new AddSubSectionException("Failed to add SubSection");
        }
    }

    private static final long TAB = 1010L;
    private static final long SECTION = 1015L;
    private static final long SUBSECTION = 1016L;

    public DeleteSubSectionResponse deleteSubSection(DeleteSubSectionRequest request) {
        try {
            log.info("Deleting sub section");
            Integer orderNbr = waUiMetaDataRepository.getOrderNumber(request.subSectionId());
            Long pageNumber = waUiMetaDataRepository.findPageNumber(request.subSectionId());
            Long nbsComponentUid = waUiMetaDataRepository.findNextNbsUiComponentUid(orderNbr+1, pageNumber);
            if(nbsComponentUid == TAB || nbsComponentUid == SECTION || nbsComponentUid == null || nbsComponentUid == SUBSECTION) {
                waUiMetaDataRepository.deleteById(request.subSectionId());
                waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, request.subSectionId());
                return new DeleteSubSectionResponse(request.subSectionId(), "Sub Section Deleted Successfully");
            } else {
                throw new DeleteSubSectionException("Conditions not satisfied");
            }
        } catch(Exception exception) {
            throw new DeleteSubSectionException("Delete Sub Section exception");
        }

    }

    public UpdateSubSectionResponse updateSubSection(UpdateSubSectionRequest request) {
        try {
            log.info("Updating subsection");
            if (request.questionLabel() == null || request.visible() == null) {
                throw new UpdateSubSectionException("Label and visibility fields are required");
            }
            waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(), request.visible(), request.subSectionId());
            return new UpdateSubSectionResponse(request.subSectionId(), UPDATE_MESSAGE);
        } catch(Exception exception) {
            throw new UpdateSubSectionException(exception.toString());
        }

    }


    private WaUiMetadata createWaUiMetadata(long pageId, Long uid, CreateSubSectionRequest request) {
        Instant now = Instant.now();
        WaTemplate page = entityManager.getReference(WaTemplate.class, pageId);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1016L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(page);

        WaUiMetadata section = waUiMetaDataRepository.findById(request.sectionId()).orElseThrow(
                () -> new AddSubSectionException("Failed to find section with id: " + request.sectionId()));

        // Find the orderNbr for the next section within a tab
        Long orderNbr = waUiMetaDataRepository.findOrderNbrOfNextSectionOrTab(section.getOrderNbr(), pageId);

        // If there are no more sections or tabs after the given orderNbr, find the max
        if (orderNbr == null) {
            orderNbr = waUiMetaDataRepository.findMaxOrderNumberByTemplateUid(pageId) + 1;
        }

        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setDisplayInd(request.visible() ? "T" : "F");
        waUiMetadata.setOrderNbr(orderNbr.intValue());
        waUiMetadata.setRequiredInd("F");
        waUiMetadata.setCoinfectionIndCd('F');
        waUiMetadata.setFutureDateIndCd('F');
        waUiMetadata.setStandardQuestionIndCd('F');
        waUiMetadata.setPublishIndCd('T');
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setEnableInd("T");
        waUiMetadata.setStandardNndIndCd('F');
        waUiMetadata.setAddTime(now);
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setLastChgTime(now);
        waUiMetadata.setLastChgUserId(uid);
        waUiMetadata.setRecordStatusCd("Active");
        waUiMetadata.setRecordStatusTime(now);
        waUiMetadata.setVersionCtrlNbr(1);
        waUiMetadata.setLocalId("NBS_1_15");
        waUiMetadata.setQuestionIdentifier("NBS_1_15");

        return waUiMetadata;

    }

    public OrderSubSectionResponse orderSubSection(Long page, OrderSubSectionRequest request) {
        Long sectionId = request.subSectionId();
        Integer currentPosition = request.currentPosition();
        Integer desiredPosition = request.desiredPosition();

        try {
            // Validate the positions and get order lists
            List<Integer> orderNumberList = validateSubSectionOrder(page, request);

            // Perform the subsection ordering
            performSubSectionOrder(page, currentPosition, desiredPosition, orderNumberList, request);

            return new OrderSubSectionResponse(sectionId, "The sub section is moved from " + currentPosition + " to " + desiredPosition + " successfully");
        } catch (Exception exception) {
            throw new OrderSubSectionException(exception.toString());
        }
    }

    private List<Integer> validateSubSectionOrder(Long page, OrderSubSectionRequest request) {
        Integer maxOrderNumber = waUiMetaDataRepository.getMaxOrderNumber(page);

        Integer currentPosition = request.currentPosition();
        Integer desiredPosition = request.desiredPosition();

        List<Integer> tabOrderNumberList = waUiMetaDataRepository.getOrderNumberList(page);
        Integer tabOrderNumber;
        if (tabOrderNumberList.size() == request.tabPosition()) {
            tabOrderNumber = maxOrderNumber;
        } else {
            tabOrderNumber = tabOrderNumberList.get(request.tabPosition());
        }

        List<Integer> sectionOrderNumberList = waUiMetaDataRepository.getSectionOrderNumberList(page,
                tabOrderNumberList.get(request.tabPosition() - 1),
                tabOrderNumber);

        Integer orderNumber;
        if ((sectionOrderNumberList.size() == request.sectionPosition()) && (tabOrderNumberList.size() == request.tabPosition())) {
            orderNumber = maxOrderNumber;
        } else if ((sectionOrderNumberList.size() == request.sectionPosition()) && (tabOrderNumberList.size() > request.tabPosition())) {
            orderNumber = tabOrderNumberList.get(request.tabPosition());
        } else {
            orderNumber = sectionOrderNumberList.get(request.sectionPosition());
        }

        List<Integer> orderNumberList = waUiMetaDataRepository.getSubSectionOrderNumberList(page,
                sectionOrderNumberList.get(request.sectionPosition() - 1),
                orderNumber);

        if (currentPosition <= 0 || desiredPosition <= 0
                || currentPosition > orderNumberList.size() || desiredPosition > orderNumberList.size()
                || request.tabPosition() <= 0 || request.tabPosition() > tabOrderNumberList.size()
                || request.sectionPosition() <= 0 || request.sectionPosition() > sectionOrderNumberList.size()) {
            throw new OrderSubSectionException("Invalid positions");
        }

        return orderNumberList;
    }

    private void performSubSectionOrder(Long page, Integer currentPosition, Integer desiredPosition, List<Integer> orderNumberList, OrderSubSectionRequest request) {
        Integer maxOrderNumber = waUiMetaDataRepository.getMaxOrderNumber(page);

        try {
            List<Integer> tabOrderNumberList = waUiMetaDataRepository.getOrderNumberList(page);
            List<Integer> sectionOrderNumberList = waUiMetaDataRepository.getSectionOrderNumberList(page,
                    tabOrderNumberList.get(request.tabPosition() - 1),
                    tabOrderNumberList.get(request.tabPosition()));

            if (currentPosition < desiredPosition) {
                // Forward reordering
                // Moving to last by adding max(order_number) to the current order number of the desired tab
                waUiMetaDataRepository.updateOrderNumber(maxOrderNumber,
                        orderNumberList.get(currentPosition - 1),
                        orderNumberList.get(currentPosition),
                        page);

                // Moving the tab to the desired position

                // Moving next tabs until the desired tab position backward to create space in between
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
            } else if (currentPosition > desiredPosition) {

                if (currentPosition == orderNumberList.size()) {
                    try {
                        orderNumberList.add(waUiMetaDataRepository.getSubSectionOrderNumberList(page,
                                sectionOrderNumberList.get(request.sectionPosition() - 1),
                                sectionOrderNumberList.get(request.sectionPosition())).get(request.currentPosition() - 1));
                    } catch (Exception exception) {
                        try {
                            orderNumberList.add(waUiMetaDataRepository.getSubSectionOrderNumberList(page,
                                    sectionOrderNumberList.get(request.sectionPosition() - 1),
                                    sectionOrderNumberList.get(request.sectionPosition())).get(request.desiredPosition() - 1));
                        } catch (Exception exceptionInCatch) {
                            orderNumberList.add(maxOrderNumber);
                        }
                    }
                }

                // Forward reordering
                // Moving to last by adding max(order_number) to the current order number of the desired tab
                waUiMetaDataRepository.updateOrderNumber(maxOrderNumber,
                        orderNumberList.get(currentPosition - 1),
                        orderNumberList.get(currentPosition),
                        page);

                // Moving next tabs until the desired tab position backward to create space in between
                waUiMetaDataRepository.updateOrderNumber(
                        (orderNumberList.get(currentPosition) -
                                orderNumberList.get(currentPosition - 1)),
                        orderNumberList.get(desiredPosition - 1),
                        orderNumberList.get(currentPosition),
                        page);

                // Moving the tab to the desired position
                waUiMetaDataRepository.updateOrderNumber((orderNumberList.get(currentPosition - 1) + maxOrderNumber -
                                orderNumberList.get(desiredPosition - 1)) * -1,
                        (orderNumberList.get(currentPosition - 1) + maxOrderNumber),
                        10000,
                        page);
            } else {
                throw new OrderSubSectionException("The Current position and Desired position are the same");
            }
        } catch (Exception exception) {
            throw new OrderSubSectionException("Something unknown occurred while reordering the section");
        }
    }

}

