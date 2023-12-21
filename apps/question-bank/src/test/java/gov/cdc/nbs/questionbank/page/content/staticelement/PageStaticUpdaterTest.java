package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.page.content.staticelement.exceptions.UpdateStaticElementException;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.UpdateStaticRequests;

@ExtendWith(MockitoExtension.class)
class PageStaticUpdaterTest {
    @InjectMocks
    private PageStaticUpdater pageStaticUpdater;

    @Mock
    private EntityManager entityManager;

    @Test
    void testUpdateDefaultStaticElement() {

        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old comments");
        tempElement.setNbsUiComponentUid(1036L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(tempElement);

        var request = new UpdateStaticRequests.UpdateDefault("new admin comments");

        pageStaticUpdater.updateDefaultStaticElement(componentId, request, 123L);

        assertEquals("new admin comments", tempElement.getAdminComment());
    }

    @Test
    void testUpdateDefaultStaticElementDoesNotExist() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old comments");
        tempElement.setNbsUiComponentUid(1012L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(null);

        var request = new UpdateStaticRequests.UpdateDefault("new admin comments");

        assertThrows(UpdateStaticElementException.class,
                () -> pageStaticUpdater.updateDefaultStaticElement(2L, request, 2L));
    }

    @Test
    void testUpdateDefaultStaticElementInvalidComponent() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old comments");
        tempElement.setNbsUiComponentUid(2L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(null);

        var request = new UpdateStaticRequests.UpdateDefault("new admin comments");

        assertThrows(UpdateStaticElementException.class,
                () -> pageStaticUpdater.updateDefaultStaticElement(2L, request, 2L));
    }

    @Test
    void testUpdateHyperlink() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old comments");
        tempElement.setDefaultValue("www.initial.com");
        tempElement.setQuestionLabel("inital label");
        tempElement.setNbsUiComponentUid(1003L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(tempElement);

        var request = new UpdateStaticRequests.UpdateHyperlink("new label", "www.new.com", "new admin comments");

        pageStaticUpdater.updateHyperlink(componentId, request, 123L);

        assertEquals("new admin comments", tempElement.getAdminComment());
        assertEquals("new label", tempElement.getQuestionLabel());
        assertEquals("www.new.com", tempElement.getDefaultValue());
    }

    @Test
    void testUpdateHyperlinkDoesNotExist() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old comments");
        tempElement.setDefaultValue("www.initial.com");
        tempElement.setQuestionLabel("inital label");
        tempElement.setNbsUiComponentUid(1012L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(null);

        var request = new UpdateStaticRequests.UpdateHyperlink("new label", "www.new.com", "new admin comments");

        assertThrows(UpdateStaticElementException.class,
                () -> pageStaticUpdater.updateHyperlink(2L, request, 2L));
    }

    @Test
    void testUpdateHyperlinkInvalidComponent() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old comments");
        tempElement.setDefaultValue("www.initial.com");
        tempElement.setQuestionLabel("inital label");
        tempElement.setNbsUiComponentUid(2L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(null);

        var request = new UpdateStaticRequests.UpdateHyperlink("new label", "www.new.com", "new admin comments");

        assertThrows(UpdateStaticElementException.class,
                () -> pageStaticUpdater.updateHyperlink(2L, request, 2L));
    }

    @Test
    void testUpdateReadOnlyComments() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old admin comments");
        tempElement.setQuestionLabel("old comments");
        tempElement.setNbsUiComponentUid(1014L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(tempElement);

        var request = new UpdateStaticRequests.UpdateReadOnlyComments("new comments", "admin comments");
        pageStaticUpdater.updateReadOnlyComments(componentId, request, 123L);

        assertEquals("admin comments", tempElement.getAdminComment());
        assertEquals("new comments", tempElement.getQuestionLabel());
    }

    @Test
    void testUpdateReadOnlyCommentsDoesNotExist() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old admin comments");
        tempElement.setQuestionLabel("old comments");
        tempElement.setNbsUiComponentUid(1012L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(null);

        var request = new UpdateStaticRequests.UpdateReadOnlyComments("new comments", "admin comments");
        assertThrows(UpdateStaticElementException.class,
                () -> pageStaticUpdater.updateReadOnlyComments(2L, request, 2L));
    }

    @Test
    void testUpdateReadOnlyCommentsInvalidComponent() {
        WaUiMetadata tempElement = new WaUiMetadata();
        tempElement.setAdminComment("old admin comments");
        tempElement.setQuestionLabel("old comments");
        tempElement.setNbsUiComponentUid(2L);
        tempElement.setId(2L);

        long componentId = 2L;
        when(entityManager.find(WaUiMetadata.class, componentId)).thenReturn(null);

        var request = new UpdateStaticRequests.UpdateReadOnlyComments("new comments", "admin comments");
        assertThrows(UpdateStaticElementException.class,
                () -> pageStaticUpdater.updateReadOnlyComments(2L, request, 2L));
    }
}
