package gov.cdc.nbs.questionbank.question;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.request.CreateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionStatusRequest;
import gov.cdc.nbs.questionbank.question.response.CreateQuestionResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionCreator creator;
    private final UserDetailsProvider userDetailsProvider;
    private final QuestionUpdater updater;

    public QuestionController(
            QuestionCreator creator,
            UserDetailsProvider userDetailsProvider,
            QuestionUpdater updater) {
        this.creator = creator;
        this.userDetailsProvider = userDetailsProvider;
        this.updater = updater;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateQuestionResponse createQuestion(@RequestBody CreateQuestionRequest request) {
        log.debug("Received create question request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        long questionId = creator.create(userId, request);
        log.debug("Successfully created question with Id: {}", questionId);
        return new CreateQuestionResponse(questionId);
    }

    @PutMapping("{id}/status")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Question setQuestionStatus(@PathVariable("id") Long id, @RequestBody QuestionStatusRequest request) {
        log.debug("Received update question status request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        Question question = updater.setStatus(userId, id, request.active());
        log.debug("Successfully updated question status");
        return question;
    }

}
