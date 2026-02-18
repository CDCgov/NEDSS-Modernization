package gov.cdc.nbs.questionbank.page.summary.download;

import gov.cdc.nbs.questionbank.page.PageMetaDataDownloader;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/pages")
@PreAuthorize("hasAuthority('LDFADMINISTRATION-SYSTEM')")
public class PageSummaryDownloadController {

  private final PageSummaryDownloader pageSummaryDownloader;
  private final PageMetaDataDownloader pageMetaDataDownloader;

  public PageSummaryDownloadController(
      final PageSummaryDownloader pageDownloader,
      final PageMetaDataDownloader pageMetaDataDownloader) {
    this.pageSummaryDownloader = pageDownloader;
    this.pageMetaDataDownloader = pageMetaDataDownloader;
  }

  @PostMapping("csv")
  public ResponseEntity<Resource> csv(
      @RequestBody final PageSummaryRequest request,
      @ParameterObject @PageableDefault(sort = "id") final Pageable pageable) {
    InputStreamResource file = pageSummaryDownloader.createCsv(request, pageable);

    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            ContentDisposition.attachment().filename("PageLibrary.csv").build().toString())
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }

  @PostMapping("pdf")
  public ResponseEntity<byte[]> pdf(
      @RequestBody final PageSummaryRequest request,
      @ParameterObject @PageableDefault(sort = "id") final Pageable pageable) {
    byte[] pdf = pageSummaryDownloader.createPdf(request, pageable);

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_PDF)
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            ContentDisposition.attachment().filename("PageLibrary.pdf").build().toString())
        .body(pdf);
  }

  @GetMapping("{id}/metadata")
  public ResponseEntity<Resource> downloadPageMetadata(@PathVariable("id") Long page)
      throws IOException {
    ByteArrayInputStream is = pageMetaDataDownloader.downloadPageMetadataByWaTemplateUid(page);
    InputStreamResource file = new InputStreamResource(is);
    return ResponseEntity.ok()
        .header(
            HttpHeaders.CONTENT_DISPOSITION,
            ContentDisposition.attachment().filename("PageMetadata.xlsx").build().toString())
        .contentType(
            MediaType.parseMediaType(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
        .body(file);
  }
}
