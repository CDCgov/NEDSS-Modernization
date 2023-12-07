package gov.cdc.nbs.questionbank.page.content.question;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.NbsUserDetails;
import gov.cdc.nbs.questionbank.page.content.question.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.content.question.response.AddQuestionResponse;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/pages/{page}/subsection/{subsection}/questions")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageQuestionController {

  private final PageQuestionAdder adder;

  public PageQuestionController(final PageQuestionAdder adder) {
    this.adder = adder;
  }

  @PostMapping
  public AddQuestionResponse addQuestionToPage(
      @PathVariable("page") Long pageId,
      @PathVariable("subsection") Long subsection,
      @RequestBody AddQuestionRequest request,
      @ApiIgnore @AuthenticationPrincipal final NbsUserDetails details) {
    return adder.addQuestions(pageId, subsection, request, details.getId());
  }
}
