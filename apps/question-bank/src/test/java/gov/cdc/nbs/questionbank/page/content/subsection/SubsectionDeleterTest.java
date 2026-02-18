package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubSectionException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubsectionDeleterTest {

  @Mock private EntityManager entityManager;

  @InjectMocks private SubSectionDeleter deleter;

  @Test
  void should_delete_subsection() {
    // Given a page
    WaTemplate page = Mockito.mock(WaTemplate.class);
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);

    // When a request to delete a subsection is processed
    deleter.delete(1l, 2l, 999l);

    // Then the subsection is deleted
    ArgumentCaptor<PageContentCommand.DeleteSubsection> captor =
        ArgumentCaptor.forClass(PageContentCommand.DeleteSubsection.class);
    verify(page).deleteSubsection(captor.capture());
  }

  @Test
  void should_not_delete_section_no_page_found() {
    // Given a page that doesn't exist
    when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

    // When a request to delete a subsection is processed
    // Then an exception is thrown
    assertThrows(DeleteSubSectionException.class, () -> deleter.delete(1l, 2l, 999l));
  }
}
