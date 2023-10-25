package gov.cdc.nbs.questionbank.page.content.tab;

import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.id.IdGeneratorService;
import gov.cdc.nbs.id.IdGeneratorService.EntityType;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.CreateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;
import gov.cdc.nbs.questionbank.question.repository.NbsConfigurationRepository;

@Component
@Transactional
public class TabCreator {

    private final WaUiMetaDataRepository repository;
    private final WaTemplateRepository templateRepository;
    private final IdGeneratorService idGenerator;
    private final NbsConfigurationRepository configRepository;

    public TabCreator(
            final WaUiMetaDataRepository repository,
            final WaTemplateRepository templateRepository,
            final IdGeneratorService idGenerator,
            final NbsConfigurationRepository configRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
        this.idGenerator = idGenerator;
        this.configRepository = configRepository;
    }

    public Tab create(long page, Long userId, CreateTabRequest request) {
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

        return new WaUiMetadata(asAdd(
                page,
                request.name(),
                request.visible(),
                getIdentifier(),
                nextOrderNumber,
                user));
    }

    private Integer getCurrentHighestOrderNumber(Long waTemplateId) {
        return repository.findMaxOrderNumberByTemplateUid(waTemplateId)
                .orElseThrow(() -> new CreateTabException("Failed to find max order number for page"));
    }

    /**
     * <p>
     * Tab Ids are a combination of the <strong>NBS_ODSE.NBS_configuration.NBS_CLASS_CODE</strong> value and the next
     * valid Id from the <strong>Local_UID_generator NBS_QUESTION_ID_LDF</strong>
     * </p>
     * <p>
     * Example: GA10001
     * </p>
     * Id generation copied from NBS Classic PageManagementActionUtil #1729
     *
     * 
     * @return String
     */
    private String getIdentifier() {
        String classCode = configRepository.findById("NBS_CLASS_CODE")
                .orElseThrow(() -> new CreateTabException("Failed to lookup NBS_CLASS_CODE"))
                .getConfigValue();
        var id = idGenerator.getNextValidId(EntityType.NBS_QUESTION_ID_LDF).getId();
        return classCode + id;
    }

    private PageContentCommand.AddTab asAdd(
            WaTemplate page,
            String label,
            boolean visible,
            String identifier,
            int orderNumber,
            long user) {
        return new PageContentCommand.AddTab(page, label, visible, identifier, orderNumber, user, Instant.now());
    }
}
