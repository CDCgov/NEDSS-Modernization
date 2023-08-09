package gov.cdc.nbs.questionbank.addsection;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.addsection.exception.AddSectionException;
import gov.cdc.nbs.questionbank.addsection.model.CreateSectionRequest;
import gov.cdc.nbs.questionbank.addtab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Transactional
public class CreateSectionService implements CreateSectionInterface {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public CreateSectionResponse createSection(Long userId, CreateSectionRequest request) {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(userId, request);
            log.info("Updating Wa_UI_metadata table by adding new section");
            waUiMetaDataRepository.incrementOrderNumbers(waUiMetadata.getOrderNbr(), waUiMetadata.getId());
            waUiMetaDataRepository.save(waUiMetadata);
            return new CreateSectionResponse(waUiMetadata.getId(), "Section Created Successfully");
        } catch (Exception exception) {
            throw new AddSectionException("Add Section exception");
        }

    }

    private WaUiMetadata createWaUiMetadata(Long uid, CreateSectionRequest request) {
        WaTemplate page = entityManager.getReference(WaTemplate.class, request.page());
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1015L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(page);
        Long nextOrderNumber = waUiMetaDataRepository.findOrderNbr(request.tabId(),
                request.page(), 1010L);
        if (nextOrderNumber == null) {
            nextOrderNumber = waUiMetaDataRepository.findOrderNbr_2(request.tabId(),
                    request.page(), 1010L);
        }
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setOrderNbr(Math.toIntExact(nextOrderNumber));
        waUiMetadata.setDisplayInd(request.visible());
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
