package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.UpdateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.UpdateTabRequest;

@ExtendWith(MockitoExtension.class)
class TabUpdaterTest {
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private TabUpdater updater;

    @Test
    void should_update_section() {
        // Given a page
        WaTemplate page = Mockito.mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        WaUiMetadata sectionMock = Mockito.mock(WaUiMetadata.class);
        when(sectionMock.getId()).thenReturn(98l);
        ArgumentCaptor<PageContentCommand.UpdateTab> captor =
                ArgumentCaptor.forClass(PageContentCommand.UpdateTab.class);
        when(page.updateTab(captor.capture())).thenReturn(sectionMock);


        // When a valid request is made to update the tab
        UpdateTabRequest request = new UpdateTabRequest("New name", false);
        updater.update(1l, 2l, 3l, request);

        // Then the tab is updated
        assertEquals("New name", captor.getValue().label());
        assertEquals(false, captor.getValue().visible());
    }

    @Test
    void should_not_update_null_name() {
        // When an invalid request is made
        UpdateTabRequest request = new UpdateTabRequest(null, false);

        // Then an exception is thrown
        assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, 3l, request));
    }

    @Test
    void should_not_update_empty_name() {
        // When an invalid request is made
        UpdateTabRequest request = new UpdateTabRequest("", false);

        // Then an exception is thrown
        assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, 3l, request));
    }

    @Test
    void should_not_update_null_request() {
        // When an invalid request is made
        UpdateTabRequest request = null;

        // Then an exception is thrown
        assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, 3l, request));
    }

    @Test
    void should_not_update_null_page() {
        // Given a page does not exist
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

        // When an request is made
        UpdateTabRequest request = new UpdateTabRequest("new name", false);

        // Then an exception is thrown
        assertThrows(UpdateTabException.class, () -> updater.update(1l, 2l, 3l, request));
    }

}
