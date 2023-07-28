package gov.cdc.nbs.questionbank.page;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.model.AddQuestionResponse;
import gov.cdc.nbs.questionbank.page.model.PageSummary;
import gov.cdc.nbs.questionbank.page.request.AddQuestionRequest;
import gov.cdc.nbs.questionbank.page.request.PageSummaryRequest;
import gov.cdc.nbs.questionbank.page.request.UpdatePageDetailsRequest;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.services.PageContentManager;
import gov.cdc.nbs.questionbank.page.services.PageSummaryFinder;
import gov.cdc.nbs.questionbank.page.services.PageUpdater;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/pages")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageController {

    private final PageUpdater pageUpdater;
    private final PageSummaryFinder finder;
    private final PageStateChanger stateChange;
    private final PageContentManager contentManager;
    private final UserDetailsProvider userDetailsProvider;
    
    public PageController(
            final PageUpdater pageUpdater,
            final PageSummaryFinder finder,
            final PageStateChanger stateChange,
            final PageContentManager contentManager,
            final UserDetailsProvider userDetailsProvider) {
        this.pageUpdater = pageUpdater;
        this.finder = finder;
         this.stateChange = stateChange;
        this.contentManager = contentManager;
        this.userDetailsProvider = userDetailsProvider;
    }

    @PutMapping("{id}/details")
    public PageSummary updatePageDetails(
            @PathVariable("id") Long pageId,
            @RequestBody UpdatePageDetailsRequest request) {
        log.debug("Received update page details request");
        pageUpdater.update(pageId, request);
        log.debug("Completed update page details request");
        return finder.find(pageId);
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
    public Page<PageSummary> search(
            @RequestBody PageSummaryRequest request,
            @PageableDefault(size = 25, sort = "id", page = 0) Pageable pageable) {
        log.debug("Received get page summary request");
        var results = finder.find(request, pageable);
        log.debug("Returning page summaries");
        return results;
    }
    
    @PutMapping("{id}/draft")
    public PageStateResponse savePageDraft(@PathVariable("id") Long pageId) {
    	return stateChange.savePageAsDraft(pageId);
    }

    @PostMapping("{id}/questions")
    public AddQuestionResponse addQuestionToPage(
            @PathVariable("id") Long pageId,
            @RequestBody AddQuestionRequest request) {
        log.debug("Received add question to page request");
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        Long componentId = contentManager.addQuestion(pageId, request, userId);
        log.debug("COmpleted add question to page request");
        return new AddQuestionResponse(componentId);
    }

}
