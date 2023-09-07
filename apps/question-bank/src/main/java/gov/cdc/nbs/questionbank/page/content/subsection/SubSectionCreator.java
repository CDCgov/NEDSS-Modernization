package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.DeleteSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.subsection.response.UpdateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Optional;
import javax.persistence.EntityManager;

@Slf4j
@Service
@Transactional
public class SubSectionCreator {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    private static final String UPDATE_MESSAGE = "SubSection Updated Successfully";
    private static final String DELETE_MESSAGE = "SubSection Deleted Successfully";

    private static final long TAB = 1010L;
    private static final long SECTION = 1015L;
    private static final long SUBSECTION = 1016L;

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
    public DeleteSubSectionResponse deleteSubSection(Long pageNumber, Long subSectionId) {
        try {
            log.info("Deleting Sub Section");
            Integer orderNbr = waUiMetaDataRepository.getOrderNumber(subSectionId);
            Optional<Long> nbsComponentUidOptional =
                    waUiMetaDataRepository.findNextNbsUiComponentUid(orderNbr+1, pageNumber);
            if (nbsComponentUidOptional.isPresent()) {
                Long nbsComponentUid = nbsComponentUidOptional.get();
                if (nbsComponentUid == TAB ||nbsComponentUid == SECTION
                        ||nbsComponentUid == SUBSECTION || nbsComponentUid == null) {
                    waUiMetaDataRepository.deleteById(subSectionId);
                    waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, subSectionId);
                    return new DeleteSubSectionResponse(subSectionId, DELETE_MESSAGE);
                } else {
                    throw new DeleteSubSectionException("Conditions not satisfied");
                }
            } else {
                waUiMetaDataRepository.deleteById(subSectionId);
                waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, subSectionId);
                return new DeleteSubSectionResponse(subSectionId, DELETE_MESSAGE);
            }
        } catch(Exception exception) {
            throw new DeleteSubSectionException("Delete SubSection exception");
        }

    }

    public UpdateSubSectionResponse updateSubSection(Long subSectionId, UpdateSubSectionRequest request) {
        try {
            log.info("Updating subsection");
            if (request.questionLabel() == null || request.visible() == null) {
                throw new UpdateSubSectionException("Label and visibility fields are required");
            }
            waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(),
                    request.visible(), subSectionId);
            return new UpdateSubSectionResponse(subSectionId, UPDATE_MESSAGE);
        } catch(Exception exception) {
            throw new UpdateSubSectionException("Update SubSection Exception");
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
}
