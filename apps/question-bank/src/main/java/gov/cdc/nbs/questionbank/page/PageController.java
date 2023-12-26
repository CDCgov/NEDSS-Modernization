package gov.cdc.nbs.questionbank.page;

import com.itextpdf.text.DocumentException;
import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.model.PageHistory;
import gov.cdc.nbs.questionbank.page.request.PageCreateRequest;
import gov.cdc.nbs.questionbank.page.response.PageCreateResponse;
import gov.cdc.nbs.questionbank.page.response.PageDeleteResponse;
import gov.cdc.nbs.questionbank.page.response.PageStateResponse;
import gov.cdc.nbs.questionbank.page.service.PageHistoryFinder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pages")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageController {

  private final PageCreator creator;
  private final PageStateChanger stateChange;
  private final PageDownloader pageDownloader;
  private final UserDetailsProvider userDetailsProvider;

  private final PageDeletor pageDeletor;
  private final PageMetaDataDownloader pageMetaDataDownloader;

  private final PageHistoryFinder pageHistoryFinder;

  public PageController(
      final PageCreator creator,
      final PageStateChanger stateChange,
      final PageDownloader pageDownloader,
      final UserDetailsProvider userDetailsProvider,
      final PageDeletor pageDeletor,
      final PageMetaDataDownloader pageMetaDataDownloader,
      final PageHistoryFinder pageHistoryFinder) {
    this.creator = creator;
    this.stateChange = stateChange;
    this.pageDownloader = pageDownloader;
    this.userDetailsProvider = userDetailsProvider;
    this.pageDeletor = pageDeletor;
    this.pageMetaDataDownloader = pageMetaDataDownloader;
    this.pageHistoryFinder = pageHistoryFinder;
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
  public PageDeleteResponse deletePageDraft(@PathVariable("id") Long pageId) {
    return pageDeletor.deletePageDraft(pageId);
  }

  @GetMapping("{waTemplateUid}/download-metadata")
  public ResponseEntity<Resource> downloadPageMetadata(@PathVariable("waTemplateUid") Long waTemplateUid)
      throws IOException {
    String fileName = "PageMetadata.xlsx";
    InputStreamResource file =
        new InputStreamResource(pageMetaDataDownloader.downloadPageMetadataByWaTemplateUid(waTemplateUid));
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
        .contentType(
            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(file);

  }

  @GetMapping("{id}/page-history")
  public List<PageHistory> getPageHistory(@PathVariable("id") Long pageId) {
    return pageHistoryFinder.getPageHistory(pageId);
  }


}
