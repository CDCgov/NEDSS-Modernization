package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.AddSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.response.CreateSubSectionResponse;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import javax.persistence.EntityManager;

@Slf4j
@Service
@Transactional
public class SubSectionCreator {

    @Autowired
    private WaUiMetaDataRepository waUiMetaDataRepository;

    @Autowired
    private EntityManager entityManager;

    public CreateSubSectionResponse createSubSection(long pageId, Long userId, CreateSubSectionRequest request) {
        try {
            WaUiMetadata waUiMetadata = createWaUiMetadata(pageId, userId, request);
            // Increment orderNbr for everything beginning at the new subSection orderNbr to "make room" to insert the subsection
            waUiMetaDataRepository.incrementOrderNumbers(waUiMetadata.getOrderNbr(), pageId);
            waUiMetaDataRepository.save(waUiMetadata);
            log.info("Updating Wa_UI_metadata table by adding new subsection");
            return new CreateSubSectionResponse(waUiMetadata.getId(), "SubSection Created Successfully");
        } catch (Exception exception) {
            throw new AddSubSectionException("Failed to add SubSection");
        }
    }

    private WaUiMetadata createWaUiMetadata(long pageId, Long uid, CreateSubSectionRequest request) {
        Instant now = Instant.now();
        WaTemplate page = entityManager.getReference(WaTemplate.class, pageId);
        WaUiMetadata waUiMetadata = new WaUiMetadata();
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setNbsUiComponentUid(1016L);
        waUiMetadata.getNbsUiComponentUid();
        waUiMetadata.setWaTemplateUid(page);

        WaUiMetadata section = waUiMetaDataRepository.findById(request.sectionId()).orElseThrow(
                () -> new AddSubSectionException("Failed to find section with id: " + request.sectionId()));

        // Find the orderNbr for the next section within a tab
        Long orderNbr = waUiMetaDataRepository.findOrderNbrOfNextSectionOrTab(section.getOrderNbr(), pageId);

        // If there are no more sections or tabs after the given orderNbr, find the max
        if (orderNbr == null) {
            orderNbr = waUiMetaDataRepository.findMaxOrderNumberByTemplateUid(pageId) + 1;
        }

        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setDisplayInd(request.visible() ? "T" : "F");
        waUiMetadata.setOrderNbr(orderNbr.intValue());
        waUiMetadata.setRequiredInd("F");
        waUiMetadata.setCoinfectionIndCd('F');
        waUiMetadata.setFutureDateIndCd('F');
        waUiMetadata.setStandardQuestionIndCd('F');
        waUiMetadata.setPublishIndCd('T');
        waUiMetadata.setQuestionLabel(request.name());
        waUiMetadata.setEnableInd("T");
        waUiMetadata.setStandardNndIndCd('F');
        waUiMetadata.setAddTime(now);
        waUiMetadata.setAddUserId(uid);
        waUiMetadata.setLastChgTime(now);
        waUiMetadata.setLastChgUserId(uid);
        waUiMetadata.setRecordStatusCd("Active");
        waUiMetadata.setRecordStatusTime(now);
        waUiMetadata.setVersionCtrlNbr(1);
        waUiMetadata.setLocalId("NBS_1_15");
        waUiMetadata.setQuestionIdentifier("NBS_1_15");

        return waUiMetadata;

    }
}
