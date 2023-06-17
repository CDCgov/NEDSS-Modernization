package gov.cdc.nbs.questionbank.question;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.response.CreateQuestionResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/questions/")
public class QuestionController {

    private final QuestionCreator creator;
    private final UserDetailsProvider userDetailsProvider;

    public QuestionController(
            QuestionCreator creator,
            UserDetailsProvider userDetailsProvider) {
        this.creator = creator;
        this.userDetailsProvider = userDetailsProvider;
    }

    @PostMapping("text")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateQuestionResponse createTextQuestion(@RequestBody CreateQuestionRequest.Text request) {
        log.debug("Received create text question request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        long questionId = creator.create(userId, request);
        log.debug("Successfully created text question with Id: {}", questionId);
        return new CreateQuestionResponse(questionId);
    }

    @PostMapping("date")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateQuestionResponse createDateQuestion(@RequestBody CreateQuestionRequest.Date request) {
        log.debug("Received create date question request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        long questionId = creator.create(userId, request);
        log.debug("Successfully created date question with Id: {}", questionId);
        return new CreateQuestionResponse(questionId);
    }

    @PostMapping("numeric")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateQuestionResponse createNumericQuestion(@RequestBody CreateQuestionRequest.Numeric request) {
        log.debug("Received create date question request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        long questionId = creator.create(userId, request);
        log.debug("Successfully created date question with Id: {}", questionId);
        return new CreateQuestionResponse(questionId);
    }

    @PostMapping("coded")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateQuestionResponse createCodedQuestion(@RequestBody CreateQuestionRequest.Coded request) {
        log.debug("Received create coded question request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        long questionId = creator.create(userId, request);
        log.debug("Successfully created coded question with Id: {}", questionId);
        return new CreateQuestionResponse(questionId);
    }

}
