package gov.cdc.nbs.questionbank.page.content.subsection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubsectionException;

@Component
public class SubsectionDeleter {

    private final WaUiMetadataRepository repository;
    private final WaTemplateRepository templateRepository;

    public SubsectionDeleter(
            final WaUiMetadataRepository repository,
            final WaTemplateRepository templateRepository) {
        this.repository = repository;
        this.templateRepository = templateRepository;
    }

    private static final Set<Long> allowed = new HashSet<>(
            Arrays.asList(
                    1010l, // Tab
                    1015l, // Section
                    1016l // Subsection
            ));

    public void delete(Long page, Long subsection) {
        if (!templateRepository.isPageDraft(page)) {
            throw new DeleteSubsectionException("Unable to delete subsection if page is not draft");
        }

        Integer orderNbr = repository.findOrderNumber(subsection)
                .orElseThrow(() -> new DeleteSubsectionException("Failed to find subsection with id: " + subsection));
        Optional<Long> nextComponent = repository.findNbsUiComponentUid(orderNbr + 1, page);

        // If next component is empty, subsection is empty
        // If next component is tab, section, or subsection then subsection is empty
        if (!nextComponent.isEmpty() && !allowed.contains(nextComponent.get())) {
            throw new DeleteSubsectionException("Unable to delete subsection with content");
        }

        repository.deleteById(subsection);
        repository.decrementOrderNumbers(orderNbr, subsection);
    }
}
