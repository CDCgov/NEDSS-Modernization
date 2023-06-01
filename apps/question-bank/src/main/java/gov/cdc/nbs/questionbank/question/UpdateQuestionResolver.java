package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.kafka.message.question.QuestionRequest;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import gov.cdc.nbs.authentication.UserDetailsProvider;

@AllArgsConstructor
@Controller
public class UpdateQuestionResolver {

    private final QuestionHandler questionHandler;
    private final UserDetailsProvider userDetailsProvider;


    @MutationMapping()
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public QuestionRequest updateQuestion(@Argument Long questionId) {
    //public QuestionResponse updateQuestion(@Argument QuestionRequest.UpdateTextQuestionRequest data) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return questionHandler.updateQuestion();
    }
}
