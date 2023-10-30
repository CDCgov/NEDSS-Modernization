package gov.cdc.nbs.questionbank.page.content.subsection;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.SubSection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;

@Component
public class SubSectionUpdater {

    private final EntityManager entityManager;

    public SubSectionUpdater(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public SubSection update(Long pageId, Long subSectionId, UpdateSubSectionRequest request, Long userId) {
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new UpdateSectionException("SubSection Name is required");
        }

        WaTemplate page = entityManager.find(WaTemplate.class, pageId);

        if (page == null) {
            throw new UpdateSectionException("Unable to find page with id: " + pageId);
        }

        WaUiMetadata section = page.updateSubSection(asCommand(userId, subSectionId, request));

        entityManager.flush();
        return new SubSection(
                section.getId(),
                section.getQuestionLabel(),
                "T".equals(section.getDisplayInd()));
    }

    private PageContentCommand.UpdateSubsection asCommand(
            Long userId,
            long subSectionId,
            UpdateSubSectionRequest request) {
        return new PageContentCommand.UpdateSubsection(
                request.name(),
                request.visible(),
                subSectionId,
                userId,
                Instant.now());
    }
}
