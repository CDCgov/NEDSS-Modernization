package gov.cdc.nbs.questionbank.section;

import gov.cdc.nbs.questionbank.section.exception.AddSectionException;
import gov.cdc.nbs.questionbank.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.section.model.CreateSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import gov.cdc.nbs.questionbank.section.model.CreateSectionResponse;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionRequest;
import gov.cdc.nbs.questionbank.section.model.DeleteSectionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.DestroyFailedException;
import java.time.Instant;

@Slf4j
@Service
public class SectionService implements CreateSectionInterface {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Override
    public CreateSectionResponse createSection(Long userId, CreateSectionRequest request) {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
            log.info("Updating Wa_UI_metadata table by adding new section");
            waUiMetaDataRepository.save(waUiMetadata);
            waUiMetaDataRepository.updateOrderNumber(waUiMetadata.getOrderNbr(), waUiMetadata.getId());
            return new CreateSectionResponse(waUiMetadata.getId(), "Section Created Successfully");
        } catch(Exception exception) {
            throw new AddSectionException("Add Section exception", 1015);
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

    private WaUiMetadata createWaUiMetadata(Long uid, CreateSectionRequest request) {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1015L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(request.page());
        Long nextOrderNumber = waUiMetaDataRepository.findOrderNbr(request.tabId(),
                request.page(), 1010L);
        if (nextOrderNumber == null) {
            nextOrderNumber = waUiMetaDataRepository.findOrderNbr_2(request.tabId(),
                    request.page(), 1010L);
        }
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setOrderNbr(Math.toIntExact(nextOrderNumber));
        waUiMetadata.setDisplayInd(request.visible());
        waUiMetadata.setPublishIndCd("T");
        waUiMetadata.setEnableInd("T");
        waUiMetadata.setRequiredInd("F");
        waUiMetadata.setCoinfectionIndCd("F");
        waUiMetadata.setFutureDateIndCd("F");
        waUiMetadata.setStandardQuestionIndCd("F");
        waUiMetadata.setStandardNndIndCd("F");
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