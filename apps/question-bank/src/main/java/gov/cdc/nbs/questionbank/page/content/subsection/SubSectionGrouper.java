package gov.cdc.nbs.questionbank.page.content.subsection;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UnGroupSubSectionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

@Component
@Transactional
public class SubSectionGrouper {

    private final EntityManager entityManager;

    public SubSectionGrouper(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public ResponseEntity<String> group(Long pageId, GroupSubSectionRequest request, Long userId) {
        if (request.blockName() == null) {
            throw new UpdateSubSectionException("SubSection Block Name is required");
        }
        int totalPercentage = 0;
        for (GroupSubSectionRequest.Batch b : request.batches()) {
            if (b.batchTableColumnWidth() == 0) {
                throw new UpdateSubSectionException("batchTableColumnWidth is required");
            }
            totalPercentage += b.batchTableColumnWidth();
        }
        if (totalPercentage != 100) {
            throw new UpdateSubSectionException("the total of batchTableColumnWidth must calculate to 100");
        }
        WaTemplate page = entityManager.find(WaTemplate.class, pageId);
        if (page == null) {
            throw new UpdateSubSectionException("Unable to find page with id: " + pageId);
        }
        WaUiMetadata section = page.groupSubSection(asCommand(userId, request));
        return ResponseEntity.ok("Subsection " + section.getId() + " is  Grouped Successfully , Block Name is " + section.getBlockNm());
    }

    public ResponseEntity<String> unGroup(Long pageId, UnGroupSubSectionRequest request, Long userId) {
        WaTemplate page = entityManager.find(WaTemplate.class, pageId);
        if (page == null) {
            throw new UpdateSubSectionException("Unable to find page with id: " + pageId);
        }
        WaUiMetadata section = page.unGroupSubSection(asCommand(userId, request));
        entityManager.flush();
        return ResponseEntity.ok("Subsection " + section.getId() + " is UnGrouped Successfully ");
    }

    private PageContentCommand.GroupSubsection asCommand(
            Long userId,
            GroupSubSectionRequest request) {
        return new PageContentCommand.GroupSubsection(
                request.id(),
                request.blockName(),
                request.batches(),
                userId,
                Instant.now());
    }

    private PageContentCommand.UnGroupSubsection asCommand(
            Long userId,
            UnGroupSubSectionRequest request) {
        return new PageContentCommand.UnGroupSubsection(
                request.id(),
                request.batches(),
                userId,
                Instant.now());
    }

}
