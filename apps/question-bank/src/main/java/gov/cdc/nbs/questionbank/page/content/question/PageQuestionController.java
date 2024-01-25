package gov.cdc.nbs.questionbank.page.content.question;

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


  public PageQuestionController(final PageQuestionAdder contentManager, final PageQuestionDeleter deleter) {
    this.adder = contentManager;
    this.deleter = deleter;
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
}
