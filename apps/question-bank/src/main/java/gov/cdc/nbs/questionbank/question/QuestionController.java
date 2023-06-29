package gov.cdc.nbs.questionbank.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionStatusRequest;
import gov.cdc.nbs.questionbank.question.request.UpdateQuestionRequest;
import gov.cdc.nbs.questionbank.question.response.CreateQuestionResponse;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    private final QuestionCreator creator;
    private final UserDetailsProvider userDetailsProvider;
    private final QuestionUpdater updater;
    private final QuestionFinder finder;

    public QuestionController(
            QuestionCreator creator,
            UserDetailsProvider userDetailsProvider,
            QuestionUpdater updater,
            QuestionFinder finder) {
        this.creator = creator;
        this.userDetailsProvider = userDetailsProvider;
        this.updater = updater;
        this.finder = finder;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Page<Question> findAllQuestions(@PageableDefault(size = 25) Pageable pageable) {
        log.debug("Received find all question request");
        Page<Question> results = finder.find(pageable);
        log.debug("Completed find all question request");
        return results;
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Page<Question> findQuestions(
            @RequestBody FindQuestionRequest request,
            @PageableDefault(size = 25) Pageable pageable) {
        log.debug("Received find question request");
        Page<Question> results = finder.find(request, pageable);
        log.debug("Found {} questions", results.getTotalElements());
        return results;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public CreateQuestionResponse createQuestion(@RequestBody CreateQuestionRequest request) {
        log.debug("Received create question request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        long questionId = creator.create(userId, request);
        log.debug("Successfully created question with Id: {}", questionId);
        return new CreateQuestionResponse(questionId);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public Question updateQuestion(@PathVariable("id") Long id, @RequestBody UpdateQuestionRequest request) {
        log.debug("Received update question request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        Question question = updater.update(userId, id, request);
        log.debug("Completed update question request");
        return question;
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
    public GetQuestionResponse getQuestion(@PathVariable("id") Long id) {
        log.debug("Receive get question request");
        GetQuestionResponse question = finder.find(id);
        log.debug("Found question");
        return question;
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
