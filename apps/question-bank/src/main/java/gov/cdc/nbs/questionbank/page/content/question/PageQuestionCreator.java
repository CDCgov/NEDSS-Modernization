package gov.cdc.nbs.questionbank.page.content.question;

import java.time.Instant;
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

    public AddQuestionResponse addQuestion(Long pageId, AddQuestionRequest request, Long user) {
        if (request == null) {
            throw new AddQuestionException("Invalid request provided");
        }

        // Find the page
        WaTemplate page = entityManager.find(WaTemplate.class, pageId);
        if (page == null) {
            throw new AddQuestionException("Failed to find page with id: " + pageId);
        }

        // find the question
        WaQuestion question = entityManager.find(WaQuestion.class, request.questionId());
        if (question == null) {
            throw new QuestionNotFoundException(request.questionId());
        }

        // Create the new question metadata entry
        WaUiMetadata metadata = page.addQuestion(asAdd(pageId, question, request.subsectionId(), user));

        // Persist the entities
        entityManager.flush();

        return new AddQuestionResponse(metadata.getId());
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
