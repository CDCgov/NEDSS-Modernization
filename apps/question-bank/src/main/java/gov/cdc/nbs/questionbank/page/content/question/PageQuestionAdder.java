package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.page.exception.PageNotFoundException;
import gov.cdc.nbs.questionbank.question.exception.QuestionNotFoundException;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PageQuestionAdder {

  private final EntityManager entityManager;

  public PageQuestionAdder(final EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public AddQuestionResponse addQuestions(
      Long pageId, Long subsection, AddQuestionRequest request, Long user) {
    if (request == null) {
      throw new AddQuestionException("Invalid request provided");
    }

    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new PageNotFoundException(pageId);
    }

    List<WaUiMetadata> uiMetadata = new ArrayList<>();
    Instant now = Instant.now();

    request
        .questionIds()
        .forEach(
            id -> {
              // find the question
              WaQuestion question = entityManager.find(WaQuestion.class, id);
              if (question == null) {
                throw new QuestionNotFoundException(id);
              }

              // Create the new question metadata entry
              WaUiMetadata metadata =
                  page.addQuestion(
                      new PageContentCommand.AddQuestion(
                          page.getId(), question, subsection, user, now));
              uiMetadata.add(metadata);
            });
    entityManager.flush();
    return new AddQuestionResponse(uiMetadata.stream().map(WaUiMetadata::getId).toList());
  }
}
