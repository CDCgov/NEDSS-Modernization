package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SectionDeleterTest {
  @Mock private EntityManager entityManager;

  @InjectMocks private SectionDeleter deleter;

  @Test
  void should_delete_section() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);

    // When a request to delete a section is processed
    deleter.deleteSection(1l, 2l, 999l);

    // Then the section is deleted
    ArgumentCaptor<PageContentCommand.DeleteSection> captor =
        ArgumentCaptor.forClass(PageContentCommand.DeleteSection.class);
    verify(page).deleteSection(captor.capture());
  }

  @Test
  void should_not_delete_section_no_page_found() {
    // Given a page that doesn't exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When a request to delete a section is processed
    // Then an exception is thrown
    assertThrows(DeleteSectionException.class, () -> deleter.deleteSection(1l, 2l, 999l));
  }
}
