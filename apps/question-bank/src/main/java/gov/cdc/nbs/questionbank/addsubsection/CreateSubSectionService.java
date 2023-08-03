package gov.cdc.nbs.questionbank.addsubsection;

import gov.cdc.nbs.questionbank.addsubsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.addsubsection.model.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class CreateSubSectionService implements CreateSubSectionInterface {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Override
    public CreateSubSectionResponse createSubSection(Long userId, CreateSubSectionRequest request) throws AddSubSectionException {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
            log.info("Saving Rule to DB");
            waUiMetaDataRepository.save(waUiMetadata);
            waUiMetaDataRepository.updateOrderNumber(waUiMetadata.getOrderNbr(), waUiMetadata.getId());
            return new CreateSubSectionResponse(waUiMetadata.getId(), "SubSection Created Successfully");
        } catch(Exception exception) {
            throw new AddSubSectionException("Add SubSection exception", 1015);
        }

    }

    private WaUiMetadata createWaUiMetadata(Long uid, CreateSubSectionRequest request) {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1016L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(request.page());
        Long nextOrderNumber = waUiMetaDataRepository.findOrderNbrForSubSection_first(request.wa_ui_metadata_uid(),
                request.page());
        if (nextOrderNumber == null) {
            nextOrderNumber = waUiMetaDataRepository.findOrderNbrForSubSection(request.wa_ui_metadata_uid(),
                    request.page());
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