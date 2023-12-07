package gov.cdc.nbs.questionbank.page.download;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PageControllerTest {

    @Mock
    private PageCreator creator;
    @Mock
    private PageStateChanger stateChange;
    @Mock
    private PageDownloader pageDownloader;
    @Mock
    private PageUpdater pageUpdater;
    @Mock
    private UserDetailsProvider userDetailsProvider;
    @Mock
    private PageDeletor pageDeletor;
    @Mock
    private PageMetaDataDownloader pageMetaDataDownloader;


    @Test
    void downloadPageLibraryPDFTest() throws Exception {
        PageController pageController = new PageController(creator, stateChange,
                pageDownloader, userDetailsProvider, pageDeletor, pageMetaDataDownloader, pageUpdater);
        byte[] resp = "pagedownloader".getBytes();
        Mockito.when(pageDownloader.downloadLibraryPDF())
                .thenReturn(resp);
        ResponseEntity<byte[]> actual = pageController.downloadPageLibraryPDF();
        assertNotNull(actual);
    }

    @Test
    void downloadPageMetadataTest() throws IOException {
        Long waTemplateUid = 1L;
        when(pageMetaDataDownloader.downloadPageMetadataByWaTemplateUid(waTemplateUid))
                .thenReturn(new ByteArrayInputStream("test,csv,data".getBytes()));
        PageController pageController = new PageController(creator, stateChange,
                pageDownloader, userDetailsProvider, pageDeletor, pageMetaDataDownloader, pageUpdater);
        ResponseEntity<Resource> response = pageController.downloadPageMetadata(waTemplateUid);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("attachment; filename=PageMetadata.xlsx", response.getHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION));
        assertEquals(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"), response.getHeaders().getContentType());
    }

    @Test
    void downloadPageMetadataExceptionTest() throws IOException {
        Long waTemplateUid = 1L;
        when(pageMetaDataDownloader.downloadPageMetadataByWaTemplateUid(waTemplateUid))
                .thenReturn(new ByteArrayInputStream("test,csv,data".getBytes()));
        PageController pageController = new PageController(creator, stateChange,
                pageDownloader, userDetailsProvider, pageDeletor, pageMetaDataDownloader, pageUpdater);
        when(pageMetaDataDownloader.downloadPageMetadataByWaTemplateUid(waTemplateUid))
                .thenThrow(new IOException("Error Downloading Page History"));
        var exception = assertThrows(IOException.class, () -> pageController.downloadPageMetadata(waTemplateUid));
        assertTrue(exception.getMessage().contains("Error Downloading Page History"));

    }
}
