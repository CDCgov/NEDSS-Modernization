package gov.cdc.nbs.questionbank.page;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.request.UpdatePageDetailsRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
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
    private final PageCreator creator;
    private final PageStateChanger stateChange;
    private final PageDownloader pageDownloader;
    private final UserDetailsProvider userDetailsProvider;
    public PageController(
            final PageUpdater pageUpdater,
            final PageSummaryFinder finder,
            final PageCreator creator,
            final PageStateChanger stateChange,
            final PageDownloader pageDownloader,
            final UserDetailsProvider userDetailsProvider) {
        this.pageUpdater = pageUpdater;
        this.finder = finder;
        this.creator = creator;
        this.stateChange = stateChange;
        this.pageDownloader = pageDownloader;
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
    public PageCreateResponse createPage(@RequestBody PageCreateRequest request) {
        Long userId = userDetailsProvider.getCurrentUserDetails().getId();
        return creator.createPage(request, userId);
    }

    @PutMapping("{id}/draft")
    public PageStateResponse savePageDraft(@PathVariable("id") Long pageId) {
        return stateChange.savePageAsDraft(pageId);
    }
    
	@GetMapping("download")
	public ResponseEntity<Resource> downloadPageLibrary() throws IOException {
		String fileName = "PageLibrary.csv";
		InputStreamResource file = new InputStreamResource(pageDownloader.downloadLibrary());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

    @GetMapping("downloadPDF")
    public ResponseEntity<byte[]> downloadPageLibraryPDF() throws DocumentException, IOException {
        var pdf = pageDownloader.downloadLibraryPDF();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("PageLibrary.pdf").build()
                                .toString())
                .body(pdf);

    }

    @DeleteMapping("{id}/delete-draft")
    public PageStateResponse deletePageDraft(@PathVariable("id") Long pageId) {
        return stateChange.deletePageDraft(pageId);
    }


}
