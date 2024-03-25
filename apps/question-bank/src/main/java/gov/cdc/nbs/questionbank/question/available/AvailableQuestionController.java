package gov.cdc.nbs.questionbank.question.available;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/questions/page/{pageId}")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class AvailableQuestionController {
  private final AvailableQuestionFinder finder;

  public AvailableQuestionController(final AvailableQuestionFinder finder) {
    this.finder = finder;
  }

  // Returns a list of questions that are available to be added to the given page
  @PostMapping("search")
  public Page<AvailableQuestion> findAvailableQuestions(
      @RequestBody AvailableQuestionCriteria request,
      @PathVariable Long pageId,
      @PageableDefault(size = 25) Pageable pageable) {
    return finder.find(request, pageId, pageable);
  }
}
