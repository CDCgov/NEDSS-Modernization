package gov.cdc.nbs.questionbank.page.content.section;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.section.exception.CreateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;

@Component
@Transactional
public class SectionCreator {

    private final WaUiMetadataRepository repository;
    private final WaTemplateRepository templateRepository;
    private final PageContentIdGenerator idGenerator;

    public SectionCreator(
            final WaUiMetadataRepository repository,
            final WaTemplateRepository templateRepository,
            final PageContentIdGenerator idGenerator) {
        this.repository = repository;
        this.templateRepository = templateRepository;
        this.idGenerator = idGenerator;
    }

    public Section create(long pageId, Long userId, CreateSectionRequest request) {
        // Verify the required fields are provided
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new CreateSectionException("Section Name is required");
        }

        // Find the page and verify it is a Draft
        WaTemplate page = templateRepository.findById(pageId)
                .orElseThrow(() -> new CreateSectionException("Failed to find page with id: " + pageId));
        if (!"Draft".equals(page.getTemplateType())) {
            throw new CreateSectionException("Unable to add section to non Draft page");
        }

        // Find the tab to insert the Section
        WaUiMetadata tab = repository.findById(request.tabId())
                .orElseThrow(() -> new CreateSectionException("Failed to find Tab to insert section into"));

        // Create the section Entity
        WaUiMetadata section = new WaUiMetadata(page,
                asAdd(
                        page.getId(),
                        idGenerator.next(),
                        userId,
                        tab.getOrderNbr() + 1,
                        request));

        // Make room to insert the new section at the beginning of the tab
        repository.incrementOrderNbrGreaterThanOrEqualTo(page.getId(), tab.getOrderNbr() + 1);

        // Insert the new section into the open space
        section = repository.save(section);
        return new Section(section.getId(), section.getQuestionLabel(), "T".equals(section.getDisplayInd()));
    }

    private PageContentCommand.AddSection asAdd(
            Long page,
            String sectionId,
            Long userId,
            Integer order,
            CreateSectionRequest request) {
        return new PageContentCommand.AddSection(
                page,
                request.name(),
                request.visible(),
                sectionId,
                order,
                userId,
                Instant.now());
    }
}
