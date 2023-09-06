package gov.cdc.nbs.questionbank.page.content.tab;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.DeleteTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.CreateTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.DeleteTabResponse;
import gov.cdc.nbs.questionbank.page.content.tab.response.UpdateTabResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
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

    public DeleteTabResponse deleteTab(Long pageNumber, DeleteTabRequest request) {
        try {
            log.info("Deleting Tab");
            Integer orderNbr = waUiMetaDataRepository.getOrderNumber(request.tabId());
            Optional<Long> nbsComponentUidOptional =
                    waUiMetaDataRepository.findNextNbsUiComponentUid(orderNbr+1, pageNumber);
            if (nbsComponentUidOptional.isPresent()) {
                Long nbsComponentUid = nbsComponentUidOptional.get();
                if (nbsComponentUid == TAB || nbsComponentUid == null) {
                    waUiMetaDataRepository.deleteById(request.tabId());
                    waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, request.tabId());
                    return new DeleteTabResponse(request.tabId(), DELETE_MESSAGE);
                } else {
                    throw new DeleteTabException("Conditions not satisfied");
                }
            } else {
                waUiMetaDataRepository.deleteById(request.tabId());
                waUiMetaDataRepository.updateOrderNumberByDecreasing(orderNbr, request.tabId());
                return new DeleteTabResponse(request.tabId(), DELETE_MESSAGE);
            }
        }  catch(Exception exception) {
            throw new DeleteTabException("Delete Tab Exception");
        }

    }

    public UpdateTabResponse updateTab(UpdateTabRequest request) {
        try {
            log.info("Updating section");
            if (request.questionLabel() == null || request.visible() == null) {
                throw new UpdateSectionException("Label and visibility fields are required");
            }
            waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(), request.visible(), request.tabId());
            return new UpdateTabResponse(request.tabId(), UPDATE_MESSAGE);
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
}
