package gov.cdc.nbs.questionbank.question;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.questionnaire.EntityMapper;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

@Controller
public class CreateQuestionResolver {

    private final UserDetailsProvider userDetailsProvider;
    private final EntityMapper entityMapper;
    private final QuestionCreator questionCreator;

    CreateQuestionResolver(
            UserDetailsProvider userDetailsProvider,
            EntityMapper entityMapper,
            QuestionCreator questionCreator) {
        this.userDetailsProvider = userDetailsProvider;
        this.entityMapper = entityMapper;
        this.questionCreator = questionCreator;
    }

    private long getUserId() {
        return userDetailsProvider.getCurrentUserDetails().getId();
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Questionnaire.TextQuestion createTextQuestion(@Argument QuestionRequest.CreateTextQuestion data) {
        long userId = getUserId();
        return entityMapper.toTextQuestion(questionCreator.create(data, userId));
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Questionnaire.DateQuestion createDateQuestion(@Argument QuestionRequest.CreateDateQuestion data) {
        long userId = getUserId();
        return entityMapper.toDateQuestion(questionCreator.create(data, userId));
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Questionnaire.DropdownQuestion createDropdownQuestion(
            @Argument QuestionRequest.CreateDropdownQuestion data) {
        long userId = getUserId();
        return entityMapper.toDropdownQuestion(questionCreator.create(data, userId));
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Questionnaire.NumericQuestion createNumericQuestion(@Argument QuestionRequest.CreateNumericQuestion data) {
        long userId = getUserId();
        return entityMapper.toNumericQuestion(questionCreator.create(data, userId));
    }

}
