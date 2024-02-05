package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;

@Component
@Transactional
public class PageQuestionDeleter {

  private final EntityManager entityManager;

  public PageQuestionDeleter(
      final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public void deleteQuestion(Long pageId, Long questionId, Long user) {

    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new PageNotFoundException(pageId);
    }
    page.deleteQuestion(new PageContentCommand.DeleteQuestion(questionId, user, Instant.now()));
  }


}
