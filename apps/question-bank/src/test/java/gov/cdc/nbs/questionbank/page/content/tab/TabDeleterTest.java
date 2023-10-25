package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.DeleteTabException;

@ExtendWith(MockitoExtension.class)
class TabDeleterTest {

    @Mock
    private WaUiMetadataRepository repository;
    @Mock
    private WaTemplateRepository templateRepository;
    @Mock
    private JdbcTemplate template;

    @InjectMocks
    private TabDeleter deleter;

    @Test
    @SuppressWarnings("unchecked")
    void should_delete_tab() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(1l)).thenReturn(true);

        // And an empty tab
        when(template.query(
                Mockito.anyString(),
                Mockito.any(PreparedStatementSetter.class),
                Mockito.any(RowMapper.class)
            ))
        .thenReturn(Collections.singletonList(1010l));

        // And an existing order number
        when(repository.findOrderNumber(2L)).thenReturn(Optional.of(2));

        // When a delete is processed
        deleter.delete(1l, 2l);

        // Then the entry should be deleted
        verify(repository).deleteById(2L);

        // And the elements reordered after it
        verify(repository).decrementOrderNumbers(2, 2l);
    }

    @Test
    void should_not_delete_tab_non_draft() {
        // Given a page that is not a Draft
        when(templateRepository.isPageDraft(1l)).thenReturn(false);

        // When a delete is processed
        // Then an exception is thrown
        assertThrows(DeleteTabException.class, () ->deleter.delete(1l, 2l));
    }

    @Test
    @SuppressWarnings("unchecked")
    void should_not_delete_tab_non_empty_tab() {
        // Given a page that is a Draft
        when(templateRepository.isPageDraft(1l)).thenReturn(true);

        // And a tab with content
        when(template.query(
                Mockito.anyString(),
                Mockito.any(PreparedStatementSetter.class),
                Mockito.any(RowMapper.class)
            ))
        .thenReturn(Collections.singletonList(1015l));

        // When a delete is processed
        // Then an exception is thrown
        assertThrows(DeleteTabException.class, () ->deleter.delete(1l, 2l));
    }
}
