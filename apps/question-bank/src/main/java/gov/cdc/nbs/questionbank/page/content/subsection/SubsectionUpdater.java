package gov.cdc.nbs.questionbank.page.content.subsection;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;

@Component
public class SubsectionUpdater {

    private final WaUiMetadataRepository repository;
    private final WaTemplateRepository templateRepository;

    public SubsectionUpdater(
            final WaUiMetadataRepository repository,
            final WaTemplateRepository templateRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
    }

    public Subsection update(Long page, Long subSectionId, Long userId, UpdateSubSectionRequest request) {
        // Verify request is valid
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new UpdateSubSectionException("Subsection Name is required");
        }

        // Verify page exists and is a Draft
        if (!templateRepository.isPageDraft(page)) {
            throw new UpdateSubSectionException("Unable to update subsection on non Draft page");
        }

        // Update the entry
        WaUiMetadata subsection = repository.findById(subSectionId)
                .orElseThrow(() -> new UpdateSubSectionException("Failed to find subsection with id: " + subSectionId));

        subsection.update(asCommand(userId, request));
        subsection = repository.save(subsection);
        return new Subsection(
                subsection.getId(),
                subsection.getQuestionLabel(),
                "T".equals(subsection.getDisplayInd()));
    }

    private PageContentCommand.UpdateSubsection asCommand(Long userId, UpdateSubSectionRequest request) {
        return new PageContentCommand.UpdateSubsection(
                request.name(),
                request.visible(),
                userId,
                Instant.now());
    }
}
