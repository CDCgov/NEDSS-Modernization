package gov.cdc.nbs.questionbank.page.summary.search;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
class PageSummarySearchController {

  private final PageSummarySearcher searcher;

  PageSummarySearchController(final PageSummarySearcher searcher) {
    this.searcher = searcher;
  }

  @Operation(
      operationId = "search",
      summary = "Allows paginated searching of Page Summaries with filters.",
      tags = "Page Summary")
  @PostMapping("/search")
  Page<PageSummary> search(
      @RequestBody final PageSummaryRequest request,
      @ParameterObject @PageableDefault(size = 25, sort = "id") final Pageable pageable) {
    PageSummaryCriteria criteria = PageSummaryCriteriaMapper.asCriteria(request);

    return searcher.find(criteria, pageable);
  }
}
