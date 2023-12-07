package gov.cdc.nbs.questionbank.page.content.question;

import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;

@Component
public class PageQuestionCreator {

  private final EntityManager entityManager;
  private final WaUiMetadataCreator creator;

  public PageQuestionCreator(
      final EntityManager entityManager,
      final WaUiMetadataCreator creator) {
    this.entityManager = entityManager;
    this.creator = creator;
  }

  public AddQuestionResponse addQuestions(Long pageId, Long subsection, AddQuestionRequest request, Long user) {
    if (request == null) {
      throw new AddQuestionException("Invalid request provided");
    }

    WaTemplate page = entityManager.find(WaTemplate.class, pageId);
    if (page == null) {
      throw new AddQuestionException("Failed to find page with id: " + pageId);
    }

    List<Long> addedIds = creator.createUiMetadata(request.questionIds(), page, subsection, user).stream()
        .map(WaUiMetadata::getId).toList();

    return new AddQuestionResponse(addedIds);
  }

}
