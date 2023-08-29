package gov.cdc.nbs.questionbank.page.content.section;

import java.time.Instant;
import javax.persistence.EntityManager;

import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.page.content.section.response.DeleteSectionResponse;
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

    private static final long TAB = 1010L;
    private static final long SECTION = 1015L;

    public DeleteSectionResponse deleteSection(DeleteSectionRequest request) {
        try {
            log.info("Deleting section");
            Integer orderNbr = waUiMetaDataRepository.getOrderNumber(request.sectionId());
            Long pageNumber = waUiMetaDataRepository.findPageNumber(request.sectionId());
            Long nbsComponentUid = waUiMetaDataRepository.findNextNbsUiComponentUid(orderNbr+1, pageNumber);
            if(nbsComponentUid == TAB || nbsComponentUid == SECTION || nbsComponentUid == null ) {
                waUiMetaDataRepository.deleteById(request.sectionId());
                waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, request.sectionId());
                return new DeleteSectionResponse(request.sectionId(), "Section Deleted Successfully");
            } else {
                throw new DeleteSectionException("Conditions not satisfied");
            }
        } catch(Exception exception) {
            throw new DeleteSectionException(exception.toString());
        }

    }

    public UpdateSectionResponse updateSection(UpdateSectionRequest request) {
        try {
            log.info("Updating section");
            if (request.questionLabel() == null || request.visible() == null) {
                throw new UpdateSectionException("Label and visibility fields are required");
            }
            waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(), request.visible(), request.sectionId());
            return new UpdateSectionResponse(request.sectionId(), UPDATE_MESSAGE);
        } catch(Exception exception) {
            throw new UpdateSectionException(exception.toString());
        }

    }


    private WaUiMetadata createWaUiMetadata(long pageId, Long uid, CreateSectionRequest request) {
        WaTemplate page = entityManager.getReference(WaTemplate.class, pageId);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1015L);
        waUiMetadata.getNbsUiComponentUid();
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
}
