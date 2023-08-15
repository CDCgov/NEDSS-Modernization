package gov.cdc.nbs.questionbank.tab;

import gov.cdc.nbs.questionbank.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionResponse;
import gov.cdc.nbs.questionbank.section.model.UpdateSectionRequest;
import gov.cdc.nbs.questionbank.section.model.UpdateSectionResponse;
import gov.cdc.nbs.questionbank.tab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.tab.exceptions.DeleteTabException;
import gov.cdc.nbs.questionbank.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.tab.model.*;
import gov.cdc.nbs.questionbank.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class TabService implements CreateTabInterface{

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    private Long getCurrentHighestOrderNumber(Long waTemplateId) {
        Long maxOrderNumber = waUiMetaDataRepository.findMaxOrderNumberByTemplateUid(waTemplateId);
        return (maxOrderNumber != null) ? maxOrderNumber : 0L;
    }

    @Override
    public CreateTabResponse createTab(Long userId, CreateTabRequest request) throws AddTabException {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
            log.info("Saving Rule to DB");
            waUiMetaDataRepository.save(waUiMetadata);
            waUiMetaDataRepository.updateOrderNumber(waUiMetadata.getOrderNbr(), waUiMetadata.getId());
            return new CreateTabResponse(waUiMetadata.getId(), "Tab Created Successfully");
        } catch(Exception exception) {
            throw new AddTabException("Add tab exception", 1010);
        }

    }

    public DeleteTabResponse deleteTab(DeleteTabRequest request) {
        try {
            log.info("Deleting section");
            Integer order_nbr = waUiMetaDataRepository.getOrderNumber(request.tabId());
            waUiMetaDataRepository.deletefromTable(request.tabId());
            waUiMetaDataRepository.updateOrderNumberByDecreasing(order_nbr, request.tabId());
            return new DeleteTabResponse(request.tabId(), "Tab Deleted Successfully");
        } catch(Exception exception) {
            throw new DeleteTabException(exception.toString(), 1015);
        }

    }

    public UpdateTabResponse updateTab(UpdateTabRequest request) {
        try {
            log.info("Updating tab");
            if (request.questionLabel() != null && request.visible() != null) {
                waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(), request.visible(), request.tabId());
                return new UpdateTabResponse(request.tabId(), "Tab Updated Successfully");
            } else if ( request.questionLabel() != null ) {
                waUiMetaDataRepository.updateQuestionLabel(request.questionLabel(), request.tabId());
                return new UpdateTabResponse(request.tabId(), "Tab Updated Successfully");
            } else if ( request.visible() != null ) {
                waUiMetaDataRepository.updateVisibility(request.visible(), request.tabId());
                return new UpdateTabResponse(request.tabId(), "Tab Updated Successfully");
            } else {
                return new UpdateTabResponse(request.tabId(), "questionLabel or Visible is required to update tab");
            }
        } catch(Exception exception) {
            throw new UpdateTabException(exception.toString(), 1015);
        }

    }

    private WaUiMetadata createWaUiMetadata(Long uid, CreateTabRequest request ) {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1010L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(request.page());
        Long waTemplateUid = waUiMetadata.getWaTemplateUid();
        Long nextOrderNumber = getCurrentHighestOrderNumber(waTemplateUid) + 1;

        waUiMetadata.setDisplayInd(request.visible());
        waUiMetadata.setOrderNbr(Math.toIntExact(nextOrderNumber));
        waUiMetadata.setRequiredInd("F");
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setAddTime(Instant.now());
        waUiMetadata.setLastChgTime(Instant.now());
        waUiMetadata.setLastChgUserId(uid);
        waUiMetadata.setRecordStatusCd("Active");
        waUiMetadata.setRecordStatusTime(Instant.now());
        waUiMetadata.setVersionCtrlNbr(1);
        waUiMetadata.setStandardNndIndCd("F");
        waUiMetadata.setLocalId("NBS_1_14");
        waUiMetadata.setQuestionIdentifier("NBS_UI_4");
        waUiMetadata.setCoinfectionIndCd("F");
        waUiMetadata.setFutureDateIndCd("F");
        waUiMetadata.setStandardQuestionIndCd("F");
        waUiMetadata.setPublishIndCd("T");
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setEnableInd("T");

        return waUiMetadata;

    }
}