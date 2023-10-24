package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;

@ExtendWith(MockitoExtension.class)
class SectionDeleterTest {
    @Mock
    private WaUiMetaDataRepository repository;
    @Mock
    private WaTemplateRepository templateRepository;

    @InjectMocks
    private SectionDeleter deleter;

    @Test
    void should_delete_section_next_element_is_tab() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And an empty section with order number
        when(repository.getOrderNumber(1l)).thenReturn(3);
        when(repository.findNextNbsUiComponentUid(4, 2l)).thenReturn(Optional.of(1010l));

        // When a delete section request is processed
        deleter.deleteSection(2l, 1l);

        // Then the section is deleted
        verify(repository).deleteById(1l);
        verify(repository).decrementOrderNumbers(3, 1l);
    }

    @Test
    void should_delete_section_end_of_page() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And an empty section with order number
        when(repository.getOrderNumber(1l)).thenReturn(3);
        when(repository.findNextNbsUiComponentUid(4, 2l)).thenReturn(Optional.empty());

        // When a delete section request is processed
        deleter.deleteSection(2l, 1l);

        // Then the section is deleted
        verify(repository).deleteById(1l);
        verify(repository).decrementOrderNumbers(3, 1l);
    }

    @Test
    void should_delete_section_next_element_is_section() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And an empty section with order number
        when(repository.getOrderNumber(1l)).thenReturn(3);
        when(repository.findNextNbsUiComponentUid(4, 2l)).thenReturn(Optional.of(1015l));

        // When a delete section request is processed
        deleter.deleteSection(2l, 1l);

        // Then the section is deleted
        verify(repository).deleteById(1l);
        verify(repository).decrementOrderNumbers(3, 1l);
    }

    @Test
    void should_not_delete_published() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(false);

        // When a delete section request is processed
        // Then an exception is thrown
        assertThrows(DeleteSectionException.class, () -> deleter.deleteSection(2l, 1l));
    }

    @Test
    void should_not_delete_has_content() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And a section with content
        when(repository.getOrderNumber(1l)).thenReturn(3);
        when(repository.findNextNbsUiComponentUid(4, 2l)).thenReturn(Optional.of(1009l));

        // When a delete section request is processed
        // Then an exception is thrown
        assertThrows(DeleteSectionException.class, () -> deleter.deleteSection(2l, 1l));
    }
}
