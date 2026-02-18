package gov.cdc.nbs.questionbank.page.summary.download;

import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryCriteria;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryCriteriaMapper;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryRequest;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummarySearcher;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PageSummaryDownloader {

  private final PageSummarySearcher searcher;
  private final PageSummaryCsvCreator csvCreator;
  private final PageSummaryPdfCreator pdfCreator;

  public PageSummaryDownloader(
      final PageSummarySearcher searcher,
      final PageSummaryCsvCreator csvCreator,
      final PageSummaryPdfCreator pdfCreator) {
    this.searcher = searcher;
    this.csvCreator = csvCreator;
    this.pdfCreator = pdfCreator;
  }

  public InputStreamResource createCsv(final PageSummaryRequest request, final Pageable pageable) {
    // Retrieve page summaries
    PageSummaryCriteria criteria = PageSummaryCriteriaMapper.asCriteria(request);
    Sort sort = pageable != null ? pageable.getSort() : Sort.by("id");
    Page<PageSummary> summaries =
        searcher.find(criteria, PageRequest.ofSize(Integer.MAX_VALUE).withSort(sort));

    // Create CSV
    return new InputStreamResource(csvCreator.toCsv(summaries));
  }

  public byte[] createPdf(final PageSummaryRequest request, final Pageable pageable) {
    // Retrieve page summaries
    PageSummaryCriteria criteria = PageSummaryCriteriaMapper.asCriteria(request);
    Sort sort = pageable != null ? pageable.getSort() : Sort.by("id");
    Page<PageSummary> summaries =
        searcher.find(criteria, PageRequest.ofSize(Integer.MAX_VALUE).withSort(sort));

    // Create PDF
    return pdfCreator.create(summaries);
  }
}
