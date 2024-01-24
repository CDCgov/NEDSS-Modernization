package gov.cdc.nbs.questionbank.page.content.subsection;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;

@Component
@Transactional
public class SubSectionDeleter {

    private final EntityManager entityManager;

    public SubSectionDeleter(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void delete(Long page, Long subsection, long userId) {
        WaTemplate template = entityManager.find(WaTemplate.class, page);
        if (template == null) {
            throw new DeleteSubSectionException("Unable to find page with id: " + page);
        }

        template.deleteSubsection(new PageContentCommand.DeleteSubsection(
                subsection,
                userId,
                Instant.now()));
    }
}
