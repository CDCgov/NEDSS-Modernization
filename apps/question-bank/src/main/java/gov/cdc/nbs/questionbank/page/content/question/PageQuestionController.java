package gov.cdc.nbs.questionbank.page.content.question;

import gov.cdc.nbs.questionbank.page.content.question.request.UpdatePageQuestionRequest;
import gov.cdc.nbs.questionbank.page.detail.PagesResponse.PagesQuestion;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/pages/{page}/")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageQuestionController {

  private final PageQuestionAdder adder;
  private final PageQuestionDeleter deleter;
  private final PageQuestionUpdater updater;


  public PageQuestionController(final PageQuestionAdder contentManager,
      final PageQuestionDeleter deleter,
      final PageQuestionUpdater updater) {
    this.adder = contentManager;
    this.deleter = deleter;
    this.updater = updater;
  }

  @PostMapping("subsection/{subsection}/questions")
  public AddQuestionResponse addQuestionToPage(
      @PathVariable("page") Long pageId,
      @PathVariable("subsection") Long subsection,
      @RequestBody AddQuestionRequest request,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return adder.addQuestions(pageId, subsection, request, details.getId());
  }

  @DeleteMapping("questions/{questionId}")
  public void deleteQuestion(
      @PathVariable("page") Long page,
      @PathVariable("questionId") Long questionId,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    deleter.deleteQuestion(page, questionId, details.getId());
  }

  @PutMapping("questions/{questionId}")
  public PagesQuestion updatePageQuestion(
      @PathVariable("page") Long pageId,
      @PathVariable("questionId") Long questionId,
      @RequestBody UpdatePageQuestionRequest request,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return updater.updatePageQuestion(pageId, questionId, request, details.getId());
  }
}
