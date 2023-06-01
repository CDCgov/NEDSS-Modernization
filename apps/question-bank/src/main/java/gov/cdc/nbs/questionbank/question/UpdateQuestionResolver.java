package gov.cdc.nbs.questionbank.question;

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
    public String updateQuestion(@Argument Long questionId) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return questionHandler.updateQuestion();
    }
}
