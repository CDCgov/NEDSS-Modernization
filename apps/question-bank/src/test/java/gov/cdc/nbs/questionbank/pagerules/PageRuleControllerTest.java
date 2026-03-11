package gov.cdc.nbs.questionbank.pagerules;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.page.content.rule.PageRuleDeleter;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PageRuleControllerTest {

  @Mock private PageRuleDeleter pageRuleDeleter;
  @Mock private PageRuleCreator pageRuleCreator;
  @Mock private PageRuleUpdater pageRuleUpdater;
  @Mock private PageRuleFinder pageRuleFinder;
  @Mock private PdfCreator pdfCreator;
  @Mock private CsvCreator csvCreator;

  @InjectMocks private PageRuleController controller;

  @Test
  void createsPdf() {
    SearchPageRuleRequest request = new SearchPageRuleRequest("");
    when(pageRuleFinder.searchPageRule(1l, request, null))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    ResponseEntity<byte[]> response = controller.downloadRulePdf(1l, request, null);
    assertThat(response).isNotNull();

    assertThat(response.getHeaders().get("Content-Type").get(0)).isEqualTo("application/pdf");
    assertThat(response.getHeaders().get("Content-Disposition").get(0))
        .isEqualTo("attachment; filename=\"ManageRulesLibrary.pdf\"");
  }

  @Test
  void createsCsv() {
    SearchPageRuleRequest request = new SearchPageRuleRequest("");
    when(pageRuleFinder.searchPageRule(1l, request, null))
        .thenReturn(new PageImpl<>(new ArrayList<>()));
    ResponseEntity<byte[]> response = controller.downloadRuleCsv(1l, request, null);
    assertThat(response).isNotNull();

    assertThat(response.getHeaders().get("Content-Type").get(0)).isEqualTo("text/plain");
    assertThat(response.getHeaders().get("Content-Disposition").get(0))
        .isEqualTo("attachment; filename=\"ManageRulesLibrary.csv\"");
  }
}
