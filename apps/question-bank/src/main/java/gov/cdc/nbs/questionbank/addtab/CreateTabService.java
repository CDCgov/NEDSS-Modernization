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

    private Long getCurrentHighestOrderNumber() {
        return waUiMetaDataRepository.findMaxOrderNumber();
    }

    @Override
    public CreateUiResponse createTab(Long userId, CreateTabRequest request) throws AddTabException {
        WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
        log.info("Saving Rule to DB");
        waUiMetaDataRepository.save(waUiMetadata);
        return new CreateUiResponse(waUiMetadata.getId(), "Tab Created Successfully");

    }
    private WaUiMetadata createWaUiMetadata(Long uid, CreateTabRequest request ) {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setWaTemplateUid(request.getWaTemplateUid());
        waUiMetadata.setNbsUiComponentUid(request.getNbsUiComponentUid());
        waUiMetadata.setQuestionLabel(request.getQuestionLabel());
        waUiMetadata.setEnableInd(request.getEnableInd());
        waUiMetadata.setDefaultValue(waUiMetadata.getDefaultValue());
        waUiMetadata.setDisplayInd(waUiMetadata.getDisplayInd());
        waUiMetadata.setOrderNbr(request.getOrderNbr());
        waUiMetadata.setRequiredInd(request.getRequiredInd());
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setDisplayInd(request.getDisplayInd());
        waUiMetadata.setLastChgTime(Instant.now());
        waUiMetadata.setLastChgUserId(uid);
        waUiMetadata.setRecordStatusCd(request.getRecordStatusCd());
        waUiMetadata.setRecordStatusTime(Instant.now());
        waUiMetadata.setMaxLength(1000L);
        waUiMetadata.setMinValue(1L);
        waUiMetadata.setVersionCtrlNbr(request.getVersionCtrlNbr());
        waUiMetadata.setStandardNndIndCd(request.getStandardNndIndCd());
        waUiMetadata.setQuestionIdentifier(request.getQuestionIdentifier());
        waUiMetadata.setAddTime(Instant.now());

        Long currentHighestOrderNumber = getCurrentHighestOrderNumber();
        waUiMetadata.setOrderNbr((int) (currentHighestOrderNumber + 1));
        return waUiMetadata;

    }
}
