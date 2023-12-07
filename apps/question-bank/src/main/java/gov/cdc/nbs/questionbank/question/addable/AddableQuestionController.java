package gov.cdc.nbs.questionbank.question.addable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/questions/page/{pageId}")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class AddableQuestionController {
  private final AddableQuestionFinder addableQuestionFinder;

  public AddableQuestionController(final AddableQuestionFinder addableQuestionFinder) {
    this.addableQuestionFinder = addableQuestionFinder;
  }

  @ApiOperation(
      value = "Search addable questions",
      notes = "Searches for questions that can be added to the given page",
      tags = "Page")
  @PostMapping(path = "search")
  public Page<AddableQuestion> findAddableQuestions(
      @RequestBody AddableQuestionCriteria request,
      @PathVariable("pageId") Long pageId,
      @PageableDefault(size = 25) Pageable pageable) {
    return addableQuestionFinder.find(request, pageId, pageable);
  }
}
