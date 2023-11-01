package gov.cdc.nbs.questionbank.page.content.section;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;

@Component
public class SectionUpdater {

    private final EntityManager entityManager;

    public SectionUpdater(
            final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Section update(Long pageId, Long sectionId, UpdateSectionRequest request, Long userId) {
        // Verify request is valid
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new UpdateSectionException("Section Name is required");
        }

        WaTemplate page = entityManager.find(WaTemplate.class, pageId);

        if (page == null) {
            throw new UpdateSectionException("Unable to find page with id: " + pageId);
        }

        WaUiMetadata section = page.updateSection(asCommand(userId, sectionId, request));

        entityManager.flush();
        return new Section(
                section.getId(),
                section.getQuestionLabel(),
                "T".equals(section.getDisplayInd()));
    }

    private PageContentCommand.UpdateSection asCommand(Long userId, Long sectionId, UpdateSectionRequest request) {
        return new PageContentCommand.UpdateSection(
                request.name(),
                request.visible(),
                sectionId,
                userId,
                Instant.now());
    }
}
