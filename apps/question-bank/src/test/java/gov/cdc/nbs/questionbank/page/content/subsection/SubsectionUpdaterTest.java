package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubSectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubSectionRequest;

@ExtendWith(MockitoExtension.class)
class SubsectionUpdaterTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private SubSectionUpdater updater;


    @Test
    void should_update_subsection() {
        // Given a page
        WaTemplate page = Mockito.mock(WaTemplate.class);
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(page);
        WaUiMetadata subSectionMock = Mockito.mock(WaUiMetadata.class);
        when(subSectionMock.getId()).thenReturn(98l);
        ArgumentCaptor<PageContentCommand.UpdateSubsection> captor =
                ArgumentCaptor.forClass(PageContentCommand.UpdateSubsection.class);
        when(page.updateSubSection(captor.capture())).thenReturn(subSectionMock);


        // When a valid request is made to update the section
        UpdateSubSectionRequest request = new UpdateSubSectionRequest("New name", false);
        updater.update(1l, 2l, 3l, request);

        // Then the section is updated
        assertEquals("New name", captor.getValue().label());
        assertEquals(false, captor.getValue().visible());
    }

    @Test
    void should_not_update_null_name() {
        // When an invalid request is made
        UpdateSubSectionRequest request = new UpdateSubSectionRequest(null, false);

        // Then an exception is thrown
        assertThrows(UpdateSubSectionException.class, () -> updater.update(1l, 2l, 3l, request));
    }

    @Test
    void should_not_update_empty_name() {
        // When an invalid request is made
        UpdateSubSectionRequest request = new UpdateSubSectionRequest("", false);

        // Then an exception is thrown
        assertThrows(UpdateSubSectionException.class, () -> updater.update(1l, 2l, 3l, request));
    }

    @Test
    void should_not_update_null_request() {
        // When an invalid request is made
        UpdateSubSectionRequest request = null;

        // Then an exception is thrown
        assertThrows(UpdateSubSectionException.class, () -> updater.update(1l, 2l, 3l, request));
    }

    @Test
    void should_not_update_null_page() {
        // Given a page does not exist
        when(entityManager.find(WaTemplate.class, 1l)).thenReturn(null);

        // When an request is made
        UpdateSubSectionRequest request = new UpdateSubSectionRequest("new name", false);

        // Then an exception is thrown
        assertThrows(UpdateSubSectionException.class, () -> updater.update(1l, 2l, 3l, request));
    }
}
