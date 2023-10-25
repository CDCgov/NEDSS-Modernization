package gov.cdc.nbs.questionbank.page.content.tab;

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
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.CreateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;

@Component
@Transactional
public class TabCreator {

    private final WaUiMetadataRepository repository;
    private final WaTemplateRepository templateRepository;
    private final PageContentIdGenerator idGenerator;


    public TabCreator(
            final WaUiMetadataRepository repository,
            final WaTemplateRepository templateRepository,
            final PageContentIdGenerator idGenerator) {
        this.repository = repository;
        this.templateRepository = templateRepository;
        this.idGenerator = idGenerator;
    }

    public Tab create(long page, Long userId, CreateTabRequest request) {
        if (request == null || !StringUtils.hasLength(request.name())) {
            throw new CreateTabException("Name is a required field");
        }

        if (!templateRepository.isPageDraft(page)) {
            throw new CreateTabException("Unable to add tab to published page");
        }

        WaUiMetadata waUiMetadata = createTabEntity(page, userId, request);
        repository.save(waUiMetadata);
        return new Tab(waUiMetadata.getId(), request.name(), request.visible());
    }

    private WaUiMetadata createTabEntity(long pageId, Long user, CreateTabRequest request) {
        WaTemplate page = templateRepository.getReferenceById(pageId);
        Integer nextOrderNumber = getCurrentHighestOrderNumber(pageId) + 1;

        return new WaUiMetadata(
                page,
                asAdd(
                        page.getId(),
                        request.name(),
                        request.visible(),
                        idGenerator.next(),
                        nextOrderNumber,
                        user));
    }

    private Integer getCurrentHighestOrderNumber(Long waTemplateId) {
        return repository.findMaxOrderNbrForPage(waTemplateId);
    }


    private PageContentCommand.AddTab asAdd(
            Long page,
            String label,
            boolean visible,
            String identifier,
            int orderNumber,
            long user) {
        return new PageContentCommand.AddTab(
                page,
                label,
                visible,
                identifier,
                orderNumber,
                user,
                Instant.now());
    }
}
