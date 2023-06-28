package gov.cdc.nbs.questionbank.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.questionbank.page.model.PageSummary;
import gov.cdc.nbs.questionbank.page.request.PageSummaryRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/pages")
public class PageSummaryController {

    private final PageSummaryFinder finder;

    public PageSummaryController(PageSummaryFinder finder) {
        this.finder = finder;
    }


    @GetMapping
    public Page<PageSummary> getAllPageSummary(
            @PageableDefault(size = 25, sort = "id", page = 0) Pageable pageable) {
        log.debug("Received find all page summary request");
        var results = finder.find(pageable);
        log.debug("Returning page summaries");
        return results;
    }

    @PostMapping
    public Page<PageSummary> pageSummarySearch(
            @RequestBody PageSummaryRequest request,
            @PageableDefault(size = 25, sort = "id", page = 0) Pageable pageable) {
        log.debug("Received get page summary request");
        var results = finder.find(request, pageable);
        log.debug("Returning page summaries");
        return results;
    }

}
