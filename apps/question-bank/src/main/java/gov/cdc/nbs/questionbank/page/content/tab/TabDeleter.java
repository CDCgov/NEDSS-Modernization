package gov.cdc.nbs.questionbank.page.content.tab;

import java.time.Instant;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;

@Component
@Transactional
public class TabDeleter {
    private final EntityManager entityManager;

    public TabDeleter(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void delete(Long page, Long tab, long userId) {
        WaTemplate template = entityManager.find(WaTemplate.class, page);
        if (template == null) {
            throw new DeleteTabException("Unable to find page with id: " + page);
        }

        template.deleteTab(new PageContentCommand.DeleteTab(
                tab,
                userId,
                Instant.now()));
    }

}
