package gov.cdc.nbs.questionbank.page.download;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.authentication.UserDetailsProvider;
import gov.cdc.nbs.questionbank.page.PageController;
import gov.cdc.nbs.questionbank.page.PageCreator;
import gov.cdc.nbs.questionbank.page.PageDeletor;
import gov.cdc.nbs.questionbank.page.PageMetaDataDownloader;
import gov.cdc.nbs.questionbank.page.PageStateChanger;
import gov.cdc.nbs.questionbank.page.model.PageHistory;
import gov.cdc.nbs.questionbank.page.service.PageHistoryFinder;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class PageControllerTest {

  @Mock private PageCreator creator;
  @Mock private PageStateChanger stateChange;
  @Mock private UserDetailsProvider userDetailsProvider;
  @Mock private PageDeletor pageDeletor;
  @Mock private PageMetaDataDownloader pageMetaDataDownloader;

  @Mock private PageHistoryFinder pageHistoryFinder;

  @InjectMocks private PageController pageController;

  @Test
  void getPageHistoryTest() {
    Pageable pageable = mock(Pageable.class);
    List<PageHistory> resultList =
        Arrays.asList(
            new PageHistory("1", "09/25/2019", "User1", "Note1"),
            new PageHistory("2", "09/25/2019", "User2", "Note2"));
    Page<PageHistory> expectedPageHistory = new PageImpl<>(resultList, pageable, 10);
    when(pageHistoryFinder.getPageHistory(100l, pageable)).thenReturn(expectedPageHistory);
    Page<PageHistory> actualPageHistory = pageController.getPageHistory(100l, pageable);
    assertEquals(expectedPageHistory, actualPageHistory);
  }

  @Test
  void getPageHistoryException() {
    Pageable pageable = mock(Pageable.class);
    when(pageHistoryFinder.getPageHistory(100l, pageable))
        .thenThrow(
            new RuntimeException("Error Fetching Page-History by Template_nm From the Database"));
    var exception =
        assertThrows(RuntimeException.class, () -> pageController.getPageHistory(100l, pageable));
    assertTrue(
        exception
            .getMessage()
            .contains("Error Fetching Page-History by Template_nm From the Database"));
  }
}
