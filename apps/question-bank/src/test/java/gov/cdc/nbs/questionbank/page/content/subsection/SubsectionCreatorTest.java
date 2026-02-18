package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.CreateSubsectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubSectionRequest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubsectionCreatorTest {
  @Mock private EntityManager entityManager;

  @Mock private PageContentIdGenerator idGenerator;

  @InjectMocks private SubSectionCreator creator;

  @Test
  void should_create_subsection() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
    WaUiMetadata newSubSectionMock = Mockito.mock(WaUiMetadata.class);
    when(newSubSectionMock.getId()).thenReturn(98l);
    when(page.addSubSection(Mockito.any(PageContentCommand.AddSubsection.class)))
        .thenReturn(newSubSectionMock);

    // And a working id generator
    when(idGenerator.next()).thenReturn("someId");

    // When a request is processed to add a section
    creator.create(1l, new CreateSubSectionRequest(3l, "subsection", false), 44l);

    // Then the section is created
    ArgumentCaptor<PageContentCommand.AddSubsection> captor =
        ArgumentCaptor.forClass(PageContentCommand.AddSubsection.class);
    verify(page).addSubSection(captor.capture());
    assertEquals("someId", captor.getValue().identifier());
    assertEquals("subsection", captor.getValue().label());
    assertFalse(captor.getValue().visible());
  }

  @Test
  void should_not_create_no_page() {
    // Given a page does not exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When a request is made
    // Then an exception is thrown
    CreateSubSectionRequest request = new CreateSubSectionRequest(2l, "section", true);
    assertThrows(CreateSubsectionException.class, () -> creator.create(1l, request, 5l));
  }

  @Test
  void should_not_create_empty_name() {
    // When a request is made without a name
    // Then an exception is thrown
    CreateSubSectionRequest request = new CreateSubSectionRequest(2l, "", true);
    assertThrows(CreateSubsectionException.class, () -> creator.create(1l, request, 5l));
  }

  @Test
  void should_not_create_null_name() {
    // When a request is made without a name
    // Then an exception is thrown
    assertThrows(CreateSubsectionException.class, () -> creator.create(1l, null, 5l));
  }
}
