package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SectionUpdaterTest {

  @Mock private EntityManager entityManager;

  @InjectMocks private SectionUpdater updater;

  @Test
  void should_update_section() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata sectionMock = Mockito.mock(WaUiMetadata.class);
    when(sectionMock.getId()).thenReturn(98l);
    ArgumentCaptor<PageContentCommand.UpdateSection> captor =
        ArgumentCaptor.forClass(PageContentCommand.UpdateSection.class);
    when(page.updateSection(captor.capture(), Mockito.any())).thenReturn(sectionMock);

    // When a valid request is made to update the section
    UpdateSectionRequest request = new UpdateSectionRequest("New name", false);
    updater.update(1l, 2l, request, 3l);

    // Then the section is updated
    assertEquals("New name", captor.getValue().label());
    assertEquals(false, captor.getValue().visible());
    assertEquals(2l, captor.getValue().sectionId());
  }

  @Test
  void should_not_update_null_name() {
    // When an invalid request is made
    UpdateSectionRequest request = new UpdateSectionRequest(null, false);

    // Then an exception is thrown
    assertThrows(UpdateSectionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_not_update_empty_name() {
    // When an invalid request is made
    UpdateSectionRequest request = new UpdateSectionRequest("", false);

    // Then an exception is thrown
    assertThrows(UpdateSectionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_not_update_null_request() {
    // When an invalid request is made
    UpdateSectionRequest request = null;

    // Then an exception is thrown
    assertThrows(UpdateSectionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_not_update_null_page() {
    // Given a page does not exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When an request is made
    UpdateSectionRequest request = new UpdateSectionRequest("new name", false);

    // Then an exception is thrown
    assertThrows(UpdateSectionException.class, () -> updater.update(1l, 2l, request, 3l));
  }

  @Test
  void should_find_section() {
    // given a valid section
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(page.getId()).thenReturn(2l);

    WaUiMetadata section = Mockito.mock(WaUiMetadata.class);
    when(section.getWaTemplateUid()).thenReturn(page);
    when(section.getNbsUiComponentUid()).thenReturn(1015l);

    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(section);

    assertNotNull(updater.findSection(1l, 2l));
  }

  @Test
  void should_not_find_section_null() {
    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(null);
    assertThrows(PageContentModificationException.class, () -> updater.findSection(1l, 2l));
  }

  @Test
  void should_not_find_section_bad_component() {
    // given a section with a bad component id
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(page.getId()).thenReturn(2l);

    WaUiMetadata section = Mockito.mock(WaUiMetadata.class);
    when(section.getWaTemplateUid()).thenReturn(page);
    when(section.getNbsUiComponentUid()).thenReturn(1009l);

    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(section);
    assertThrows(PageContentModificationException.class, () -> updater.findSection(1l, 2l));
  }

  @Test
  void should_not_find_section_bad_page() {
    // given a section with the wrong page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(page.getId()).thenReturn(3l);

    WaUiMetadata section = Mockito.mock(WaUiMetadata.class);
    when(section.getWaTemplateUid()).thenReturn(page);

    when(entityManager.find(WaUiMetadata.class, 1l)).thenReturn(section);
    assertThrows(PageContentModificationException.class, () -> updater.findSection(1l, 2l));
  }
}
