package gov.cdc.nbs.questionbank.question;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.DropdownQuestionEntity;
import gov.cdc.nbs.questionbank.entities.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;
import gov.cdc.nbs.questionbank.questionnaire.EntityMapper;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public Questionnaire.TextQuestion createTextQuestion(@Argument QuestionRequest.CreateTextQuestion request) {
        log.debug("Received create text question request: {}", request);
        long userId = getUserId();
        TextQuestionEntity entity = questionCreator.create(request, userId);
        log.debug("Successfully created text question entity: {}", entity.getId().toString());
        return entityMapper.toTextQuestion(entity);
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Questionnaire.DateQuestion createDateQuestion(@Argument QuestionRequest.CreateDateQuestion request) {
        log.debug("Received create date question request: {}", request);
        long userId = getUserId();
        DateQuestionEntity entity = questionCreator.create(request, userId);
        log.debug("Successfully created date question entity: {}", entity.getId().toString());
        return entityMapper.toDateQuestion(entity);
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Questionnaire.DropdownQuestion createDropdownQuestion(
            @Argument QuestionRequest.CreateDropdownQuestion request) {
        log.debug("Received create dropdown question request: {}", request);
        long userId = getUserId();
        DropdownQuestionEntity entity = questionCreator.create(request, userId);
        log.debug("Successfully created dropdown question entity: {}", entity.getId().toString());
        return entityMapper.toDropdownQuestion(entity);
    }

    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Questionnaire.NumericQuestion createNumericQuestion(
            @Argument QuestionRequest.CreateNumericQuestion request) {
        log.debug("Received create numeric question request: {}", request);
        long userId = getUserId();
        NumericQuestionEntity entity = questionCreator.create(request, userId);
        log.debug("Successfully created numeric question entity: {}", entity.getId().toString());
        return entityMapper.toNumericQuestion(entity);
    }

}
