package gov.cdc.nbs.questionbank.page.download;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.PageCreator;
import gov.cdc.nbs.questionbank.page.PageDownloader;
import gov.cdc.nbs.questionbank.page.PageStateChanger;
import gov.cdc.nbs.questionbank.page.services.PageInfoFinder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PageControllerTest {

    @Mock
    private PageCreator creator;
    @Mock
    private PageStateChanger stateChange;
    @Mock
    private PageDownloader pageDownloader;
    @Mock
    private UserDetailsProvider userDetailsProvider;
    @Mock
    private PageInfoFinder pageInfoFinder;

    @Test
    void downloadPageLibraryPDFTest() throws Exception {

        PageController pageController = new PageController(creator, stateChange,
                pageDownloader, userDetailsProvider,pageInfoFinder);

        byte[] resp = "pagedownloader".getBytes();
        Mockito.when(pageDownloader.downloadLibraryPDF())
                .thenReturn(resp);
        ResponseEntity<byte[]> actual = pageController.downloadPageLibraryPDF();
        assertNotNull(actual);
    }
}
