package gov.cdc.nbs.questionbank.page.summary.download;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.page.PageMetaDataDownloader;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummaryRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PageSummaryDownloadControllerTest {

  @Mock private PageSummaryDownloader summaryDownloader;

  @Mock private PageMetaDataDownloader metadataDownloader;

  @InjectMocks private PageSummaryDownloadController controller;

  @Test
  void proper_return_type_csv() {
    // Given a request to download a csv
    PageSummaryRequest request = new PageSummaryRequest("", new ArrayList<>());
    PageRequest pageable = PageRequest.of(0, 100, Sort.by(Direction.ASC, "name"));
    InputStreamResource mockedInputStream = Mockito.mock(InputStreamResource.class);
    when(summaryDownloader.createCsv(request, pageable)).thenReturn(mockedInputStream);

    // When the request is processed
    ResponseEntity<Resource> response = controller.csv(request, pageable);

    // Then the correct media type is returned
    assertThat(response.getHeaders())
        .containsEntry("Content-Type", Collections.singletonList("application/csv"));
    assertThat(response.getHeaders())
        .containsEntry(
            "Content-Disposition",
            Collections.singletonList("attachment; filename=\"PageLibrary.csv\""));
  }

  @Test
  void proper_return_type_pdf() {
    // Given a request to download a pdf
    PageSummaryRequest request = new PageSummaryRequest("", new ArrayList<>());
    PageRequest pageable = PageRequest.of(0, 100, Sort.by(Direction.ASC, "name"));
    byte[] pdfMock = new byte[8];
    when(summaryDownloader.createPdf(request, pageable)).thenReturn(pdfMock);

    // When the request is processed
    ResponseEntity<byte[]> response = controller.pdf(request, pageable);

    // Then the correct media type is returned
    assertThat(response.getHeaders())
        .containsEntry("Content-Type", Collections.singletonList("application/pdf"));
    assertThat(response.getHeaders())
        .containsEntry(
            "Content-Disposition",
            Collections.singletonList("attachment; filename=\"PageLibrary.pdf\""));
  }

  @Test
  void proper_return_type_metadata() throws IOException {
    // Given a request to download metadata
    ByteArrayInputStream metaMock = Mockito.mock(ByteArrayInputStream.class);
    when(metadataDownloader.downloadPageMetadataByWaTemplateUid(1l)).thenReturn(metaMock);

    // When the request is processed
    ResponseEntity<Resource> response = controller.downloadPageMetadata(1l);

    // Then the correct media type is returned
    assertThat(response.getHeaders())
        .containsEntry(
            "Content-Type",
            Collections.singletonList(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    assertThat(response.getHeaders())
        .containsEntry(
            "Content-Disposition",
            Collections.singletonList("attachment; filename=\"PageMetadata.xlsx\""));
  }
}
