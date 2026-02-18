package gov.cdc.nbs.questionbank.page.content.rule;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.rule.exceptions.DeleteRuleException;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageRuleDeleter {

  private final EntityManager entityManager;

  public PageRuleDeleter(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void delete(Long page, Long ruleId, long userId) {
    WaTemplate template = entityManager.find(WaTemplate.class, page);
    if (template == null) {
      throw new DeleteRuleException("Unable to find page with id: " + page);
    }
    template.deleteRule(new PageContentCommand.DeleteRule(ruleId, userId, Instant.now()));
  }
}
