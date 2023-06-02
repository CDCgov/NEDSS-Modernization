package gov.cdc.nbs.questionbank.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FindQuestionResolver {

    private final QuestionFinder finder;

    public FindQuestionResolver(final QuestionFinder finder) {
        this.finder = finder;
    }

    @QueryMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Page<Questionnaire.Question> findQuestions(
            @Argument QuestionFilter filter,
            @Argument QuestionPage page) {
        log.debug("Received findQuestions request with filter: {}", filter);
        Page<Questionnaire.Question> questions = finder.find(filter, toPageable(page));
        log.debug("Found {} questions", questions.getTotalElements());
        return questions;
    }

    private Pageable toPageable(QuestionPage page) {
        if (page == null) {
            return null;
        }
        return PageRequest.of(page.pageNumber(), page.pageSize());
    }

}
