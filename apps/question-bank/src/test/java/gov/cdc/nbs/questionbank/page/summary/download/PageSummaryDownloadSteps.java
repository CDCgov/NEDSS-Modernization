package gov.cdc.nbs.questionbank.page.summary.download;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryRequest;
import gov.cdc.nbs.questionbank.support.PageIdentifier;
import gov.cdc.nbs.testing.support.Active;
import gov.cdc.nbs.testing.support.Available;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PageSummaryDownloadSteps {

  private final Active<PageSummaryRequest> criteria;
  private final PageSummaryDownloadRequester requester;
  private final Active<ResultActions> response;
  private final Active<PageRequest> pageable;
  private final Available<PageIdentifier> availablePages;

  public PageSummaryDownloadSteps(
      @Qualifier("pageSummaryRequest") final Active<PageSummaryRequest> criteria,
      final PageSummaryDownloadRequester requester,
      final Active<ResultActions> response,
      @Qualifier("pageable") final Active<PageRequest> pageable,
      final Available<PageIdentifier> availablePages) {
    this.criteria = criteria;
    this.requester = requester;
    this.response = response;
    this.pageable = pageable;
    this.availablePages = availablePages;
  }

  @When("^I download page summaries as a (.*)$")
  public void i_download_page_summaries_as_a(final String type) throws Exception {
    switch (type.toLowerCase()) {
      case "csv":
        response.active(requester.csv(
            criteria.active(),
            pageable.maybeActive().orElse(null)));
        break;
      case "pdf":
        response.active(requester.pdf(
            criteria.active(),
            pageable.maybeActive().orElse(null)));
        break;
      default:
        throw new IllegalArgumentException("Unsupported file type specified");
    }
  }

  @Then("^all summaries are present in the (.*)$")
  public void all_summaries_are_present_in_the_file(final String type) throws Exception {
    var result = response.active().andExpect(status().isOk()).andReturn().getResponse();
    switch (type.toLowerCase()) {
      case "csv":
        validateCsvContainsAll(result.getContentAsString());
        break;
      case "pdf":
        validatePdfContainsAll(response);
        break;
      default:
        throw new IllegalArgumentException("Unsupported file type specified");
    }
  }


  @Then("^the (.*) has the following headers:$")
  public void the_file_has_the_headers(final String type, final DataTable dataTable)
      throws IOException {

    switch (type.toLowerCase()) {
      case "csv":
        String content = response.active().andReturn().getResponse().getContentAsString();
        dataTable.asList()
            .forEach(h -> assertThat(content).contains(h));
        break;
      case "pdf":
        String textContent = getPdfTextContent(response);
        dataTable.asList()
            .forEach(h -> assertThat(textContent).contains(h));
        break;
      default:
        throw new IllegalArgumentException("Unknown file type: " + type);
    }
  }

  @Then("^(\\d+) summaries are present in the (.*)$")
  public void count_summaries_are_present_in_the_file(final int count, final String type)
      throws IOException {
    switch (type.toLowerCase()) {
      case "csv":
        String content = response.active().andReturn().getResponse().getContentAsString();
        assertThat(content.lines().count() - 1).isEqualTo(count); // -1 for header row
        break;
      case "pdf":
        String textContent = getPdfTextContent(response);
        assertThat(textContent.split("\n").length - 1).isEqualTo(count); // -1 for header row
        break;
      default:
        throw new IllegalArgumentException("Unknown file type: " + type);
    }

  }

  private String getPdfTextContent(Active<ResultActions> response) throws IOException {
    PdfReader reader = new PdfReader(response.active().andReturn().getResponse().getContentAsByteArray());
    return PdfTextExtractor.getTextFromPage(reader, 1);
  }

  private void validateCsvContainsAll(String contentAsString) {
    assertThat(contentAsString).isNotEmpty();
    availablePages.all().forEach(p -> {
      assertThat(contentAsString).contains(p.name());
    });
  }

  private void validatePdfContainsAll(Active<ResultActions> response) throws IOException {
    String textContent = getPdfTextContent(response);
    availablePages.all().forEach(p -> {
      assertThat(textContent).contains(p.name());
    });
  }


}
