package gov.cdc.nbs.questionbank.question;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;

@RestController
@RequestMapping("/api/v1/questions/")
public class CreateQuestionController {

    private final UserDetailsProvider userDetailsProvider;
    private final QuestionCreator questionCreator;

    CreateQuestionController(
            UserDetailsProvider userDetailsProvider,
            QuestionCreator questionCreator) {
        this.userDetailsProvider = userDetailsProvider;
        this.questionCreator = questionCreator;
    }


}
