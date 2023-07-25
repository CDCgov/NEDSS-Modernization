package gov.cdc.nbs.questionbank.addtab;

import gov.cdc.nbs.questionbank.addtab.exceptions.AddTabException;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import gov.cdc.nbs.questionbank.addtab.model.CreateTabRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class CreateTabService implements CreateTabInterface{

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    private Long getCurrentHighestOrderNumber(Long nbsUiComponentUid) {
        return waUiMetaDataRepository.findMaxOrderNumberByNbsUiComponentUid(nbsUiComponentUid);
    }

    @Override
    public CreateUiResponse createTab(Long userId, CreateTabRequest request) throws AddTabException {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
            log.info("Saving Rule to DB");
            waUiMetaDataRepository.save(waUiMetadata);
            return new CreateUiResponse(waUiMetadata.getId(), "Tab Created Successfully");
        } catch(Exception exception) {
            throw new AddTabException("Add tab exception", 1010);
        }

    }
    private WaUiMetadata createWaUiMetadata(Long uid, CreateTabRequest request ) {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setWaTemplateUid(request.page());
        waUiMetadata.setNbsUiComponentUid(1010L);
        Long nbsUiComponentUid = waUiMetadata.getNbsUiComponentUid();
        Long nextOrderNumber = null;

        if (nbsUiComponentUid.equals(1010L)) {
            // Get the next order number for NBS_UI component with nbsUiComponentUid 1010
            nextOrderNumber = getCurrentHighestOrderNumber(1010L) + 1;
        }

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