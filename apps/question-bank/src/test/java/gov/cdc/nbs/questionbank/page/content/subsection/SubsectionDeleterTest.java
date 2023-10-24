package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.DeleteSubsectionException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;

@ExtendWith(MockitoExtension.class)
class SubsectionDeleterTest {

    @Mock
    private WaUiMetaDataRepository repository;
    @Mock
    private WaTemplateRepository templateRepository;

    @InjectMocks
    private SubsectionDeleter deleter;

    @ParameterizedTest
    @MethodSource("componentList")
    void should_delete_subsection(Long nextComponent) {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And an empty subsection with order number
        when(repository.getOrderNumber(1l)).thenReturn(Optional.of(3));
        when(repository.findNextNbsUiComponentUid(4, 2l)).thenReturn(Optional.ofNullable(nextComponent));

        // When a delete subsection request is processed
        deleter.delete(2l, 1l);

        // Then the subsection is deleted
        verify(repository).deleteById(1l);
        verify(repository).decrementOrderNumbers(3, 1l);
    }

    private static List<Long> componentList() {
        return Arrays.asList(
                1010l, // TAB
                1015l, // SECTION
                1016l, // SUBSECTION
                null);
    }

    @Test
    void should_not_delete_because_published() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(0L)).thenReturn(false);

        // When a delete subsection request is processed
        // Then an exception is thrown
        assertThrows(DeleteSubsectionException.class, () -> deleter.delete(0l, 1l));
    }

    @Test
    void should_not_delete_no_subsection() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And an subsection that doesn't exist
        when(repository.getOrderNumber(1l)).thenReturn(Optional.empty());

        // When a delete section request is processed
        // Then an exception is thrown
        assertThrows(DeleteSubsectionException.class, () -> deleter.delete(2l, 1l));
    }

    @Test
    void should_not_delete_has_content() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And a section with content
        when(repository.getOrderNumber(1l)).thenReturn(Optional.of(3));
        when(repository.findNextNbsUiComponentUid(4, 2l)).thenReturn(Optional.of(1009l));

        // When a delete section request is processed
        // Then an exception is thrown
        assertThrows(DeleteSubsectionException.class, () -> deleter.delete(2l, 1l));
    }

}
