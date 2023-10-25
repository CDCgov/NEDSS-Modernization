package gov.cdc.nbs.questionbank.page.content.section;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;

@Component
public class SectionUpdater {

    private final WaUiMetadataRepository repository;
    private final WaTemplateRepository templateRepository;

    public SectionUpdater(
            final WaUiMetadataRepository repository,
            final WaTemplateRepository templateRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
    }

    public Section update(Long page, Long sectionId, Long userId, UpdateSectionRequest request) {
        if (!templateRepository.isPageDraft(page)) {
            throw new UpdateSectionException("Unable to update section in a published page");
        }

        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new UpdateSectionException("Section Name is required");
        }

        WaUiMetadata section = repository.findById(sectionId)
                .orElseThrow(() -> new UpdateSectionException("Failed to find section with id: " + sectionId));
        section.update(asUpdate(request, userId));
        section = repository.save(section);

        return new Section(
                section.getId(),
                section.getQuestionLabel(),
                "T".equals(section.getDisplayInd()));
    }

    private PageContentCommand.UpdateSection asUpdate(UpdateSectionRequest request, Long userId) {
        return new PageContentCommand.UpdateSection(
                request.name(),
                request.visible(),
                userId,
                Instant.now());
    }
}
