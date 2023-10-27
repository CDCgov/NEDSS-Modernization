package gov.cdc.nbs.questionbank.page.content.section;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;

@Component
public class SectionDeleter {
    private final EntityManager entityManager;

    public SectionDeleter(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void deleteSection(Long page, Long sectionId) {
        WaTemplate template = entityManager.find(WaTemplate.class, page);
        if (template == null) {
            throw new DeleteSectionException("Unable to find page with id: " + page);
        }

        template.deleteSection(sectionId);
    }
}
