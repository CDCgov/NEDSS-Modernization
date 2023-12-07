package gov.cdc.nbs.questionbank.page.content.question;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddQuestion;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;

@Component
class WaUiMetadataCreator {

  private final EntityManager entityManager;

  public WaUiMetadataCreator(
      final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Transactional
  public List<WaUiMetadata> createUiMetadata(Collection<Long> questions, WaTemplate page, Long subsection, Long user) {
    List<WaUiMetadata> uiMetadata = new ArrayList<>();
    questions.forEach(id -> {

      // find the question
      WaQuestion question = entityManager.find(WaQuestion.class, id);
      if (question == null) {
        throw new QuestionNotFoundException(id);
      }

      // Create the new question metadata entry
      WaUiMetadata metadata = page.addQuestion(asAdd(page.getId(), question, subsection, user));
      uiMetadata.add(metadata);
    });
    return uiMetadata;
  }

  private PageContentCommand.AddQuestion asAdd(
      Long pageId,
      WaQuestion question,
      Long subsection,
      Long user) {
    return new AddQuestion(
        pageId,
        question,
        subsection,
        user,
        Instant.now());
  }
}
