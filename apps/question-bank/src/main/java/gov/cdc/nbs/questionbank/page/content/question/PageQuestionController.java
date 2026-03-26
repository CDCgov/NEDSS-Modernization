package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.question.model.EditableQuestion;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageCodedQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageCodedQuestionValuesetRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageDateQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageNumericQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequiredRequest;
import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageTextQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.content.question.response.ValidationResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages/{page}/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageQuestionController {

  private final EditableQuestionFinder finder;
  private final PageQuestionAdder adder;
  private final PageQuestionDeleter deleter;
  private final PageQuestionUpdater updater;
  private final PageQuestionValidator validator;

  public PageQuestionController(
      final PageQuestionAdder contentManager,
      final PageQuestionDeleter deleter,
      final PageQuestionUpdater updater,
      final EditableQuestionFinder finder,
      final PageQuestionValidator validator) {
    this.adder = contentManager;
    this.deleter = deleter;
    this.updater = updater;
    this.finder = finder;
    this.validator = validator;
  }

  @PostMapping("subsection/{subsection}/questions")
  public AddQuestionResponse addQuestionToPage(
      @PathVariable("page") Long pageId,
      @PathVariable Long subsection,
      @RequestBody AddQuestionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return adder.addQuestions(pageId, subsection, request, details.getId());
  }

  @GetMapping("questions/{questionId}/edit")
  public EditableQuestion getEditableQuestion(
      @PathVariable Long page, @PathVariable Long questionId) {
    return finder.find(page, questionId);
  }

  @PutMapping("questions/{questionId}/required")
  public EditableQuestion updatePageQuestionRequired(
      @PathVariable Long page,
      @PathVariable Long questionId,
      @RequestBody UpdatePageQuestionRequiredRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.setRequired(page, questionId, request, details.getId());
  }

  @GetMapping("questions/{questionId}/datamart/validate")
  public ValidationResponse validateDatamart(
      @PathVariable Long page, @PathVariable Long questionId, @RequestParam String datamart) {
    return validator.validateDataMart(page, questionId, datamart);
  }

  @DeleteMapping("questions/{questionId}")
  public void deleteQuestion(
      @PathVariable Long page,
      @PathVariable Long questionId,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    deleter.deleteQuestion(page, questionId, details.getId());
  }

  @PutMapping("questions/text/{questionId}")
  public EditableQuestion updatePageTextQuestion(
      @PathVariable("page") Long pageId,
      @PathVariable Long questionId,
      @RequestBody UpdatePageTextQuestionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(pageId, questionId, request, details.getId());
  }

  @PutMapping("questions/numeric/{questionId}")
  public EditableQuestion updatePageNumericQuestion(
      @PathVariable("page") Long pageId,
      @PathVariable Long questionId,
      @RequestBody UpdatePageNumericQuestionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(pageId, questionId, request, details.getId());
  }

  @PutMapping("questions/coded/{questionId}")
  public EditableQuestion updatePageCodedQuestion(
      @PathVariable("page") Long pageId,
      @PathVariable Long questionId,
      @RequestBody UpdatePageCodedQuestionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(pageId, questionId, request, details.getId());
  }

  @PutMapping("questions/date/{questionId}")
  public EditableQuestion updatePageDateQuestion(
      @PathVariable("page") Long pageId,
      @PathVariable Long questionId,
      @RequestBody UpdatePageDateQuestionRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(pageId, questionId, request, details.getId());
  }

  @PutMapping("questions/coded/{questionId}/valueset")
  public EditableQuestion updatePageCodedQuestionValueset(
      @PathVariable("page") Long pageId,
      @PathVariable Long questionId,
      @RequestBody UpdatePageCodedQuestionValuesetRequest request,
      @Parameter(hidden = true) @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.update(pageId, questionId, request, details.getId());
  }
}
