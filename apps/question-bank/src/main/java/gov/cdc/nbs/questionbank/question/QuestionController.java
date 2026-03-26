package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.model.Question.CodedQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.DateQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.NumericQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.TextQuestion;
import gov.cdc.nbs.questionbank.question.request.FindQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.QuestionStatusRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.create.CreateTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.update.UpdateCodedQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.update.UpdateDateQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.update.UpdateNumericQuestionRequest;
import gov.cdc.nbs.questionbank.question.request.update.UpdateTextQuestionRequest;
import gov.cdc.nbs.questionbank.question.response.GetQuestionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/questions")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
@RequiredArgsConstructor
public class QuestionController {

  private final QuestionCreator creator;
  private final UserDetailsProvider userDetailsProvider;
  private final QuestionUpdater updater;
  private final QuestionFinder finder;

  @GetMapping
  public Page<Question> findAllQuestions(
      @ParameterObject @PageableDefault(size = 25) Pageable pageable) {
    log.debug("Received find all question request");
    Page<Question> results = finder.find(pageable);
    log.debug("Completed find all question request");
    return results;
  }

  @PostMapping("search")
  public Page<Question> findQuestions(
      @RequestBody FindQuestionRequest request,
      @ParameterObject @PageableDefault(size = 25) Pageable pageable) {
    log.debug("Received find question request");
    Page<Question> results = finder.find(request, pageable);
    log.debug("Found {} questions", results.getTotalElements());
    return results;
  }

  @PostMapping("text")
  @ResponseStatus(HttpStatus.CREATED)
  public TextQuestion createTextQuestion(@RequestBody CreateTextQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return creator.create(userId, request);
  }

  @PostMapping("numeric")
  @ResponseStatus(HttpStatus.CREATED)
  public NumericQuestion createNumericQuestion(@RequestBody CreateNumericQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return creator.create(userId, request);
  }

  @PostMapping("coded")
  @ResponseStatus(HttpStatus.CREATED)
  public CodedQuestion createCodedQuestion(@RequestBody CreateCodedQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return creator.create(userId, request);
  }

  @PostMapping("date")
  @ResponseStatus(HttpStatus.CREATED)
  public DateQuestion createDateQuestion(@RequestBody CreateDateQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return creator.create(userId, request);
  }

  @PutMapping("text/{id}")
  public Question updateTextQuestion(
      @PathVariable Long id, @RequestBody UpdateTextQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return updater.update(userId, id, request);
  }

  @PutMapping("numeric/{id}")
  public Question updateNumericQuestion(
      @PathVariable Long id, @RequestBody UpdateNumericQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return updater.update(userId, id, request);
  }

  @PutMapping("coded/{id}")
  public Question updateCodedQuestion(
      @PathVariable Long id, @RequestBody UpdateCodedQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return updater.update(userId, id, request);
  }

  @PutMapping("date/{id}")
  public Question updateDateQuestion(
      @PathVariable Long id, @RequestBody UpdateDateQuestionRequest request) {
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    return updater.update(userId, id, request);
  }

  @GetMapping("{id}")
  public GetQuestionResponse getQuestion(@PathVariable Long id) {
    log.debug("Receive get question request");
    GetQuestionResponse question = finder.find(id);
    log.debug("Found question");
    return question;
  }

  @PutMapping("{id}/status")
  public Question setQuestionStatus(
      @PathVariable Long id, @RequestBody QuestionStatusRequest request) {
    log.debug("Received update question status request");
    Long userId = userDetailsProvider.getCurrentUserDetails().getId();
    Question question = updater.setStatus(userId, id, request.active());
    log.debug("Successfully updated question status");
    return question;
  }
}
