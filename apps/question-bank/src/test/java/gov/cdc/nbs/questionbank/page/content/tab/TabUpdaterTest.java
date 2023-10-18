package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.repository.WaUiMetaDataRepository;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;

@ExtendWith(MockitoExtension.class)
class TabUpdaterTest {
    @Mock
    private WaUiMetaDataRepository repository;
    @Mock
    private WaTemplateRepository templateRepository;

    @InjectMocks
    private TabUpdater updater;

    @Test
    void should_update_tab() {
        // Given a draft page
        when(templateRepository.isPageDraft(1l)).thenReturn(true);

        // And an existing tab
        when(repository.findById(2l)).thenReturn(Optional.of(new WaUiMetadata()));
        
        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(repository.save(captor.capture())).then(a -> a.getArgument(0));

        // When a tab is updated
        updater.update(1l, 2L, new UpdateTabRequest("test name", false));
        
        // Then the tab is changed
        WaUiMetadata actual = captor.getValue();
        assertEquals("test name", actual.getQuestionLabel());
        assertEquals("F", actual.getDisplayInd());
    }

    @Test
    void should_not_update_published() {
        // Given a non draft page
        when(templateRepository.isPageDraft(1l)).thenReturn(false);

        // When a request is made to update a tab
        // Then an exception is thrown
        assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l , null));
    }

    @Test
    void should_require_label() {
        // Given a draft page
        when(templateRepository.isPageDraft(1l)).thenReturn(true);

        // When a request is made to update a tab with a null label
        // Then an exception is thrown
        UpdateTabRequest request = new UpdateTabRequest(null, false);
        assertThrows(UpdateTabException.class, () -> updater.update(1l,2l,request));
    }

    @Test
    void should_require_request() {
        // Given a draft page
        when(templateRepository.isPageDraft(1l)).thenReturn(true);

        // When a request is made to update a tab with a null request
        // Then an exception is thrown
        assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, null));
    }

}
