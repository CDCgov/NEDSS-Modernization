package gov.cdc.nbs.questionbank.questionnaire;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

@Controller
public class QuestionnaireResolver {
    private final QuestionnaireFinder finder;

    public QuestionnaireResolver(QuestionnaireFinder finder) {
        this.finder = finder;
    }

    @QueryMapping()
    public Questionnaire findQuestionnaire(@Argument QuestionnaireContext input) {
        return finder.find(input);
    }
}
