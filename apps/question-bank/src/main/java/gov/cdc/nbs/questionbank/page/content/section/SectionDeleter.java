package gov.cdc.nbs.questionbank.page.content.section;

import java.util.Optional;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;

@Component
public class SectionDeleter {
    private static final long TAB = 1010L;
    private static final long SECTION = 1015L;

    private final WaUiMetaDataRepository repository;
    private final WaTemplateRepository templateRepository;

    public SectionDeleter(
            final WaUiMetaDataRepository repository,
            final WaTemplateRepository templateRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
    }

    public void deleteSection(Long page, Long sectionId) {
        if (!templateRepository.isPageDraft(page)) {
            throw new DeleteSectionException("Unable to remove section from a published page");
        }

        Integer orderNbr = repository.getOrderNumber(sectionId);
        Optional<Long> nextComponent = repository.findNextNbsUiComponentUid(orderNbr + 1, page);

        // If there is no next component, the section is empty.
        // If the next component is a tab or section, the section is empty
        if (nextComponent.isEmpty() || nextComponent.get() == TAB || nextComponent.get() == SECTION) {
            repository.deleteById(sectionId);
            repository.decrementOrderNumbers(orderNbr, sectionId);
        } else {
            throw new DeleteSectionException("Unable to delete a section with content");
        }
    }
}
