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


    public DeleteSectionResponse deleteSection(DeleteSectionRequest request) {
        try {
            log.info("Deleting section");
            Integer order_nbr = waUiMetaDataRepository.getOrderNumber(request.sectionId());
            waUiMetaDataRepository.deletefromTable(request.sectionId());
            waUiMetaDataRepository.updateOrderNumberByDecreasing(order_nbr, request.sectionId());
            return new DeleteSectionResponse(request.sectionId(), "Section Deleted Successfully");
        } catch(Exception exception) {
            throw new DeleteSectionException(exception.toString(), 1015);
        }

    }

    public UpdateSectionResponse updateSection(UpdateSectionRequest request) {
        try {
            log.info("Updating section");
            if (request.questionLabel() != null && request.visible() != null) {
                waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(), request.visible(), request.sectionId());
                return new UpdateSectionResponse(request.sectionId(), "Section Updated Successfully");
            } else if ( request.questionLabel() != null ) {
                waUiMetaDataRepository.updateQuestionLabel(request.questionLabel(), request.sectionId());
                return new UpdateSectionResponse(request.sectionId(), "Section Updated Successfully");
            } else if ( request.visible() != null ) {
                waUiMetaDataRepository.updateVisibility(request.visible(), request.sectionId());
                return new UpdateSectionResponse(request.sectionId(), "Section Updated Successfully");
            } else {
                return new UpdateSectionResponse(request.sectionId(), "questionLabel or Visible is required to update section");
            }
        } catch(Exception exception) {
            throw new UpdateSectionException(exception.toString(), 1015);
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
