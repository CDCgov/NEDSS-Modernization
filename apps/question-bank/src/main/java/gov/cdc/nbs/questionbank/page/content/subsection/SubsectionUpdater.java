package gov.cdc.nbs.questionbank.page.content.subsection;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;

@Component
public class SubSectionUpdater {

    private final EntityManager entityManager;

    public SubSectionUpdater(
            final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Subsection update(Long pageId, Long subsectionId, Long userId, UpdateSubSectionRequest request) {
        // Verify request is valid
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new UpdateSubSectionException("Subsection Name is required");
        }

        WaTemplate page = entityManager.find(WaTemplate.class, pageId);

        if (page == null) {
            throw new UpdateSubSectionException("Unable to find page with id: " + pageId);
        }

        WaUiMetadata subsection = page.updateSubSection(asCommand(userId, subsectionId, request));

        entityManager.flush();
        return new Subsection(
                subsection.getId(),
                subsection.getQuestionLabel(),
                "T".equals(subsection.getDisplayInd()));
    }

    private PageContentCommand.UpdateSubsection asCommand(
            Long userId,
            Long subsectionId,
            UpdateSubSectionRequest request) {
        return new PageContentCommand.UpdateSubsection(
                request.name(),
                request.visible(),
                subsectionId,
                userId,
                Instant.now());
    }
}
