package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.section.exception.CreateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SectionCreatorTest {

  @Mock private EntityManager entityManager;

  @Mock private PageContentIdGenerator idGenerator;

  @InjectMocks private SectionCreator creator;

  @Test
  void should_create_section() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata newSectionMock = Mockito.mock(WaUiMetadata.class);
    when(newSectionMock.getId()).thenReturn(98l);
    when(page.addSection(Mockito.any(PageContentCommand.AddSection.class)))
        .thenReturn(newSectionMock);

    // And a working id generator
    when(idGenerator.next()).thenReturn("someId");

    // When a request is processed to add a section
    creator.create(1l, new CreateSectionRequest(2l, "section", false), 44l);

    // Then the section is created
    ArgumentCaptor<PageContentCommand.AddSection> captor =
        ArgumentCaptor.forClass(PageContentCommand.AddSection.class);
    verify(page).addSection(captor.capture());
    assertEquals("someId", captor.getValue().identifier());
    assertEquals("section", captor.getValue().label());
    assertFalse(captor.getValue().visible());
  }

  @Test
  void should_not_create_no_page() {
    // Given a page does not exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When a request is made
    // Then an exception is thrown
    CreateSectionRequest request = new CreateSectionRequest(2l, "section", true);
    assertThrows(CreateSectionException.class, () -> creator.create(1l, request, 5l));
  }

  @Test
  void should_not_create_empty_name() {
    // When a request is made without a name
    // Then an exception is thrown
    CreateSectionRequest request = new CreateSectionRequest(2l, "", true);
    assertThrows(CreateSectionException.class, () -> creator.create(1l, request, 5l));
  }

  @Test
  void should_not_create_null_name() {
    // When a request is made without a name
    // Then an exception is thrown
    assertThrows(CreateSectionException.class, () -> creator.create(1l, null, 5l));
  }
}
