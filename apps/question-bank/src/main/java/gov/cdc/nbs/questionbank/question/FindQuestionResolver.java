package gov.cdc.nbs.questionbank.question;

import java.util.Collection;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
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
    public Collection<Questionnaire.Question> findQuestions(@Argument QuestionFilter filter, @Argument Pageable page) {
        log.debug("Received findQuestions request with filter: {}", filter);
        Collection<Questionnaire.Question> questions = finder.find(filter, page);
        log.debug("Found {} questions", questions.size());
        return questions;
    }

}
