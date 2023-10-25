package gov.cdc.nbs.questionbank.page.content.subsection;

import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.CreateSubsectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubsectionRequest;

@Service
@Transactional
public class SubsectionCreator {

    private final WaUiMetadataRepository repository;
    private final WaTemplateRepository templateRepository;
    private final PageContentIdGenerator idGenerator;

    public SubsectionCreator(
            final WaUiMetadataRepository repository,
            final WaTemplateRepository templateRepository,
            final PageContentIdGenerator idGenerator) {
        this.repository = repository;
        this.templateRepository = templateRepository;
        this.idGenerator = idGenerator;
    }

    public Subsection create(long pageId, Long userId, CreateSubsectionRequest request) {
        // Verify a name is provided
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new CreateSubsectionException("Subsection Name is required");
        }

        // Find the page and verify it is a Draft
        WaTemplate page = templateRepository.findById(pageId)
                .orElseThrow(() -> new CreateSubsectionException("Failed to find page with id: " + pageId));

        if (!"Draft".equals(page.getTemplateType())) {
            throw new CreateSubsectionException("Unable to add subsection to non Draft page");
        }

        // Find the section to add the subsection to
        WaUiMetadata section = repository.findById(request.sectionId())
                .orElseThrow(
                        () -> new CreateSubsectionException("Unable to find Section with id: " + request.sectionId()));

        // Make room to insert the new subsection into the Section
        repository.incrementOrderNbrGreaterThanOrEqualTo(pageId, section.getOrderNbr() + 1);

        // Create the subsection entity
        WaUiMetadata subsection = new WaUiMetadata(
                page,
                asAdd(
                        pageId,
                        userId,
                        section.getOrderNbr() + 1,
                        request));

        // Insert it into the database at the now open position
        subsection = repository.save(subsection);

        return new Subsection(
                subsection.getId(),
                subsection.getQuestionLabel(),
                "T".equals(subsection.getDisplayInd()));
    }

    private PageContentCommand.AddSubsection asAdd(
            long pageId,
            long userId,
            Integer order,
            CreateSubsectionRequest request) {
        return new PageContentCommand.AddSubsection(
                pageId,
                request.name(),
                request.visible(),
                idGenerator.next(),
                order,
                userId,
                Instant.now());
    }
}
