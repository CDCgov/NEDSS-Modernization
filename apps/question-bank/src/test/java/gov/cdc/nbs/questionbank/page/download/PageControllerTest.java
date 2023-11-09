package gov.cdc.nbs.questionbank.page.download;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.PageCreator;
import gov.cdc.nbs.questionbank.page.PageDownloader;
import gov.cdc.nbs.questionbank.page.PageStateChanger;
import gov.cdc.nbs.questionbank.page.model.PageInfo;
import gov.cdc.nbs.questionbank.page.services.PageInfoFinder;
import gov.cdc.nbs.questionbank.page.summary.search.PageSummary;
import gov.cdc.nbs.questionbank.question.model.ConditionSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

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
    private UserDetailsProvider userDetailsProvider;
    @Mock
    private PageInfoFinder pageInfoFinder;

    private PageController pageController;
    @BeforeEach
    void setUp() {
        pageController = new PageController(creator, stateChange, pageDownloader, userDetailsProvider, pageInfoFinder);
    }

    @Test
    void downloadPageLibraryPDFTest() throws Exception {
        byte[] resp = "pagedownloader".getBytes();
        Mockito.when(pageDownloader.downloadLibraryPDF())
                .thenReturn(resp);
        ResponseEntity<byte[]> actual = pageController.downloadPageLibraryPDF();
        assertNotNull(actual);
    }

    @Test
    void getPageInfoDetails() throws Exception {
        Long pageId = 1L;
        PageInfo.EventType eventType = new PageInfo.EventType("value","name");
        PageInfo.MessageMappingGuide messageMappingGuide = new PageInfo.MessageMappingGuide("100" ,"name");
        List<ConditionSummary> conditions = Collections.EMPTY_LIST;

        PageInfo expectedPageInfo = new PageInfo(eventType,"page name","description",
                messageMappingGuide,conditions,"data mart");
        Mockito.when(pageInfoFinder.find(pageId)).thenReturn(expectedPageInfo);
        PageInfo result = pageController.getPageInfoDetails(pageId);
        assertEquals(expectedPageInfo, result);
    }

    @Test
    void getPageInfoDetailsException() throws Exception {
        Long pageId = 1L;
        when(pageInfoFinder.find(pageId)).thenThrow(new NullPointerException("No Page Info For That Id"));
        var exception = assertThrows(NullPointerException.class, () -> pageController.getPageInfoDetails(pageId));
        assertTrue(exception.getMessage().contains("No Page Info For That Id"));
    }

}
