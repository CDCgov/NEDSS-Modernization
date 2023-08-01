package gov.cdc.nbs.questionbank.addsection;

import gov.cdc.nbs.questionbank.addsection.exception.AddSectionException;
import gov.cdc.nbs.questionbank.addsection.model.CreateSectionRequest;


import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class CreateSectionService implements CreateSectionInterface {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Override
    public CreateSectionResponse createSection(Long userId, CreateSectionRequest request) throws AddSectionException {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
            log.info("Saving Rule to DB");
            waUiMetaDataRepository.save(waUiMetadata);
            return new CreateSectionResponse(waUiMetadata.getId(), "Section Created Successfully");
        } catch(Exception exception) {
            throw new AddSectionException(exception.toString()+request.wa_ui_metadata_uid(), 1015);
        }

    }

    private WaUiMetadata createWaUiMetadata(Long uid, CreateSectionRequest request) {
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1015L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(request.page());
        Long nextOrderNumber = waUiMetaDataRepository.findOrderNbrForSection(request.wa_ui_metadata_uid());

        waUiMetadata.setDisplayInd(request.visible());
        waUiMetadata.setOrderNbr(Math.toIntExact(nextOrderNumber+1));
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