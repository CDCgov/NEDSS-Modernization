package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TabUpdaterTest {
  @Mock private EntityManager entityManager;

  @InjectMocks private TabUpdater updater;

  @Test
  void should_update_section() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata sectionMock = Mockito.mock(WaUiMetadata.class);
    when(sectionMock.getId()).thenReturn(98l);
    ArgumentCaptor<PageContentCommand.UpdateTab> captor =
        ArgumentCaptor.forClass(PageContentCommand.UpdateTab.class);
    when(page.updateTab(captor.capture(), Mockito.any())).thenReturn(sectionMock);

    // When a valid request is made to update the tab
    UpdateTabRequest request = new UpdateTabRequest("New name", false);
    updater.update(1l, 2l, request, 3l);

    // Then the tab is updated
    assertEquals("New name", captor.getValue().label());
    assertEquals(false, captor.getValue().visible());
    assertEquals(2l, captor.getValue().tab());
  }

  @Test
  void should_not_update_null_name() {
    // When an invalid request is made
    UpdateTabRequest request = new UpdateTabRequest(null, false);

    // Then an exception is thrown
    assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_not_update_empty_name() {
    // When an invalid request is made
    UpdateTabRequest request = new UpdateTabRequest("", false);

    // Then an exception is thrown
    assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_not_update_null_request() {
    // When an invalid request is made
    UpdateTabRequest request = null;

    // Then an exception is thrown
    assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_not_update_null_page() {
    // Given a page does not exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When an request is made
    UpdateTabRequest request = new UpdateTabRequest("new name", false);

    // Then an exception is thrown
    assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_find_tab() {
    // given a valid tab
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(page.getId()).thenReturn(2l);

    WaUiMetadata tab = Mockito.mock(WaUiMetadata.class);
    when(tab.getWaTemplateUid()).thenReturn(page);
    when(tab.getNbsUiComponentUid()).thenReturn(1010l);

    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(tab);

    assertNotNull(updater.findTab(1l, 2l));
  }

  @Test
  void should_not_find_tab_null() {
    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(null);
    assertThrows(PageContentModificationException.class, () -> updater.findTab(1l, 2l));
  }

  @Test
  void should_not_find_tab_bad_component() {
    // given a tab with a bad component id
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(page.getId()).thenReturn(2l);

    WaUiMetadata tab = Mockito.mock(WaUiMetadata.class);
    when(tab.getWaTemplateUid()).thenReturn(page);
    when(tab.getNbsUiComponentUid()).thenReturn(1011l);

    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(tab);
    assertThrows(PageContentModificationException.class, () -> updater.findTab(1l, 2l));
  }

  @Test
  void should_not_find_tab_bad_page() {
    // given a tab with the wrong page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(page.getId()).thenReturn(3l);

    WaUiMetadata tab = Mockito.mock(WaUiMetadata.class);
    when(tab.getWaTemplateUid()).thenReturn(page);

    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(tab);
    assertThrows(PageContentModificationException.class, () -> updater.findTab(1l, 2l));
  }
}
