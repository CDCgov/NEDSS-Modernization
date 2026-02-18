package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TabDeleterTest {

  @Mock private EntityManager entityManager;

  @InjectMocks private TabDeleter deleter;

  @Test
  void should_delete_tab() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);

    // When a request to delete a tab is processed
    deleter.delete(1l, 2l, 999l);

    // Then the tab is deleted
    ArgumentCaptor<PageContentCommand.DeleteTab> captor =
        ArgumentCaptor.forClass(PageContentCommand.DeleteTab.class);
    verify(page).deleteTab(captor.capture());
  }

  @Test
  void should_not_delete_tab_no_page_found() {
    // Given a page that doesn't exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When a request to delete a tab is processed
    // Then an exception is thrown
    assertThrows(DeleteTabException.class, () -> deleter.delete(1l, 2l, 999l));
  }
}
