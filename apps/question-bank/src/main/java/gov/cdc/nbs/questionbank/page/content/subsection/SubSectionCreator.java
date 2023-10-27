package gov.cdc.nbs.questionbank.page.content.subsection;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.CreateSubsectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;

@Service
@Transactional
public class SubSectionCreator {

    private final EntityManager entityManager;
    private final PageContentIdGenerator idGenerator;

    public SubSectionCreator(
            final EntityManager entityManager,
            final PageContentIdGenerator idGenerator) {
        this.entityManager = entityManager;
        this.idGenerator = idGenerator;
    }

    public Subsection create(long pageId, Long userId, CreateSubSectionRequest request) {
        // Verify the required fields are provided
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new CreateSubsectionException("Subsection Name is required");
        }

        // Find the page
        WaTemplate page = entityManager.find(WaTemplate.class, pageId);
        if (page == null) {
            throw new CreateSubsectionException("Failed to find page with id: " + pageId);
        }

        // Create the new section
        WaUiMetadata subsection = page.addSubSection(asAdd(
                idGenerator.next(),
                userId,
                request.sectionId(),
                request));

        // Persist the entities
        entityManager.flush();
        return new Subsection(subsection.getId(), subsection.getQuestionLabel(),
                "T".equals(subsection.getDisplayInd()));
    }

    private PageContentCommand.AddSubsection asAdd(
            String identifier,
            long userId,
            long sectionId,
            CreateSubSectionRequest request) {
        return new PageContentCommand.AddSubsection(
                request.name(),
                request.visible(),
                identifier,
                sectionId,
                userId,
                Instant.now());
    }
}
