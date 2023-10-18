package gov.cdc.nbs.questionbank.page.content.tab;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;

@Component
@Transactional
public class TabUpdater {

    private final WaUiMetaDataRepository repository;
    private final WaTemplateRepository templateRepository;

    public TabUpdater(
            final WaUiMetaDataRepository repository,
            final WaTemplateRepository templateRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
    }

    public Tab update(Long page, Long tab, UpdateTabRequest request) {
        if (!templateRepository.isPageDraft(page)) {
            throw new UpdateTabException("Unable to update a published page");
        }
        if (request == null || request.label() == null) {
            throw new UpdateTabException("Label is a required field");
        }

        WaUiMetadata metadata = repository.findById(tab)
                .orElseThrow(() -> new UpdateTabException("Failed to find tab"));

        metadata.setDisplayInd(request.visible() ? "T" : "F");
        metadata.setQuestionLabel(request.label());
        metadata = repository.save(metadata);
        return new Tab(
                metadata.getId(),
                metadata.getQuestionLabel(),
                metadata.getDisplayInd().equals("T"));
    }
}
