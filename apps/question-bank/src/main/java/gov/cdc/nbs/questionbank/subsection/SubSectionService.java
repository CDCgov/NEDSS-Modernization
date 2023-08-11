package gov.cdc.nbs.questionbank.subsection;
import gov.cdc.nbs.questionbank.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.subsection.exception.DeleteSubSectionException;
import gov.cdc.nbs.questionbank.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.subsection.model.*;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class SubSectionService implements CreateSubSectionInterface {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Override
    public CreateSubSectionResponse createSubSection(Long userId, CreateSubSectionRequest request) {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
            log.info("Updating Wa_UI_metadata table by adding new subsection");
            waUiMetaDataRepository.save(waUiMetadata);
            waUiMetaDataRepository.updateOrderNumber(waUiMetadata.getOrderNbr(), waUiMetadata.getId());
            return new CreateSubSectionResponse(waUiMetadata.getId(), "SubSection Created Successfully");
        } catch(Exception exception) {
            throw new AddSubSectionException("Add SubSection exception", 1015);
        }

    }

    public UpdateSubSectionResponse updateSubSection(UpdateSubSectionRequest request) {
        try {
            log.info("Updating section");
            if (request.questionLabel() != null && request.visible() != null) {
                waUiMetaDataRepository.updateQuestionLabelAndVisibility(request.questionLabel(), request.visible(), request.subSectionId());
                return new UpdateSubSectionResponse(request.subSectionId(), "Sub Section Updated Successfully");
            } else if ( request.questionLabel() != null ) {
                waUiMetaDataRepository.updateQuestionLabel(request.questionLabel(), request.subSectionId());
                return new UpdateSubSectionResponse(request.subSectionId(), "Sub Section Updated Successfully");
            } else if ( request.visible() != null ) {
                waUiMetaDataRepository.updateVisibility(request.visible(), request.subSectionId());
                return new UpdateSubSectionResponse(request.subSectionId(), "Sub Section Updated Successfully");
            } else {
                return new UpdateSubSectionResponse(request.subSectionId(), "questionLabel or Visible is required to update sub section");
            }
        } catch(Exception exception) {
            throw new UpdateSubSectionException(exception.toString(), 1015);
        }

    }

    public DeleteSubSectionResponse deleteSubSection(DeleteSubSectionRequest request) {
        try {
            log.info("Deleting section");
            Integer order_nbr = waUiMetaDataRepository.getOrderNumber(request.subSectionId());
            waUiMetaDataRepository.deletefromTable(request.subSectionId());
            waUiMetaDataRepository.updateOrderNumberByDecreasing(order_nbr, request.subSectionId());
            return new DeleteSubSectionResponse(request.subSectionId(), "Sub Section Deleted Successfully");
        } catch(Exception exception) {
            throw new DeleteSubSectionException("Delete Sub Section exception", 1015);
        }

    }

    private WaUiMetadata createWaUiMetadata(Long uid, CreateSubSectionRequest request) {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1016L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(request.page());
        Long nextOrderNumber = waUiMetaDataRepository.findOrderNbr(request.sectionId(),
                request.page(), 1015L);
        if (nextOrderNumber == null) {
            nextOrderNumber = waUiMetaDataRepository.findOrderNbr_2(request.sectionId(),
                    request.page(), 1015L);
        }
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setDisplayInd(request.visible());
        waUiMetadata.setOrderNbr(Math.toIntExact(nextOrderNumber));
        waUiMetadata.setRequiredInd("F");
        waUiMetadata.setCoinfectionIndCd("F");
        waUiMetadata.setFutureDateIndCd("F");
        waUiMetadata.setStandardQuestionIndCd("F");
        waUiMetadata.setPublishIndCd("T");
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setEnableInd("T");
        waUiMetadata.setStandardNndIndCd("F");
        waUiMetadata.setAddTime(Instant.now());
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setLastChgTime(Instant.now());
        waUiMetadata.setLastChgUserId(uid);
        waUiMetadata.setRecordStatusCd("Active");
        waUiMetadata.setRecordStatusTime(Instant.now());
        waUiMetadata.setVersionCtrlNbr(1);
        waUiMetadata.setLocalId("NBS_1_15");
        waUiMetadata.setQuestionIdentifier("NBS_1_15");

        return waUiMetadata;

    }
}