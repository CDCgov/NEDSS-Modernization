package gov.cdc.nbs.questionbank.page.content.question;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.AddQuestion;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;

@Component
@Transactional
public class PageQuestionCreator {

  private final EntityManager entityManager;

  public PageQuestionCreator(
      final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public AddQuestionResponse addQuestions(Long pageId, Long subsection, AddQuestionRequest request, Long user) {
    if (request == null) {
      throw new AddQuestionException("Invalid request provided");
    }

    // Find the page
    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new AddQuestionException("Failed to find page with id: " + pageId);
    }

    List<Long> metadataIds = new ArrayList<>();
    // for each question
    request.questionIds().forEach(id -> {

      // find the question
      WaQuestion question = entityManager.find(WaQuestion.class, id);
      if (question == null) {
        throw new QuestionNotFoundException(id);
      }

      // Create the new question metadata entry
      WaUiMetadata metadata = page.addQuestion(asAdd(pageId, question, subsection, user));
      metadataIds.add(metadata.getId());
    });

    return new AddQuestionResponse(metadataIds);
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
