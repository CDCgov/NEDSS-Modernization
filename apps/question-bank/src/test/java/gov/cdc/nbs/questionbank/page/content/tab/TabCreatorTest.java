package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.CreateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TabCreatorTest {
  @Mock private EntityManager entityManager;

  @Mock private PageContentIdGenerator idGenerator;

  @InjectMocks private TabCreator creator;

  @Test
  void should_create_tab() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata newTabMock = Mockito.mock(WaUiMetadata.class);
    when(newTabMock.getId()).thenReturn(98l);
    when(page.addTab(Mockito.any(PageContentCommand.AddTab.class))).thenReturn(newTabMock);

    // And a working id generator
    when(idGenerator.next()).thenReturn("someId");

    // When a request is processed to add a tab
    creator.create(1l, new CreateTabRequest("tab", false), 44l);

    // Then the tab is created
    ArgumentCaptor<PageContentCommand.AddTab> captor =
        ArgumentCaptor.forClass(PageContentCommand.AddTab.class);
    verify(page).addTab(captor.capture());
    assertEquals("someId", captor.getValue().identifier());
    assertEquals("tab", captor.getValue().label());
    assertFalse(captor.getValue().visible());
  }

  @Test
  void should_not_create_no_page() {
    // Given a page does not exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When a request is made
    // Then an exception is thrown
    CreateTabRequest request = new CreateTabRequest("tab", true);
    assertThrows(CreateTabException.class, () -> creator.create(1l, request, 5l));
  }

  @Test
  void should_not_create_empty_name() {
    // When a request is made without a name
    // Then an exception is thrown
    CreateTabRequest request = new CreateTabRequest("", true);
    assertThrows(CreateTabException.class, () -> creator.create(1l, request, 5l));
  }

  @Test
  void should_not_create_null_name() {
    // When a request is made without a name
    // Then an exception is thrown
    assertThrows(CreateTabException.class, () -> creator.create(1l, null, 5l));
  }
}
