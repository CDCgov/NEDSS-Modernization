package gov.cdc.nbs.questionbank.page.content.staticelement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticHyperLinkRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticElementDefaultRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.AddStaticReadOnlyCommentsRequest;
import gov.cdc.nbs.questionbank.page.content.staticelement.request.DeleteStaticElementRequest;

@ExtendWith(MockitoExtension.class)
class PageStaticCreatorTest {
    @Mock
    private WaUiMetadataRepository uiMetadatumRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PageStaticCreator contentManager;


    @Test
    void should_add_line_separator_to_page() {
        var request = new AddStaticElementDefaultRequest(null, 10L);

        Long pageId = 123L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        Long userId = 999L;

        WaUiMetadata subsec = new WaUiMetadata();
        subsec.setOrderNbr(4);

        when(uiMetadatumRepository.findById(request.subSectionId())).thenReturn(Optional.of(subsec));

        when(uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subsec.getOrderNbr())).thenReturn(4);

        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });

        Long newId = contentManager.addLineSeparator(pageId, request, userId);

        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertEquals(999L, captor.getValue().getAddUserId().longValue());
        assertNotNull(newId);
    }

    @Test
    void should_add_hyperlink_to_page() {
        var request = new AddStaticHyperLinkRequest("google", "google.com", null, 10L);

        Long pageId = 123L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        Long userId = 999L;

        WaUiMetadata subsec = new WaUiMetadata();
        subsec.setOrderNbr(4);

        when(uiMetadatumRepository.findById(request.subSectionId())).thenReturn(Optional.of(subsec));

        when(uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subsec.getOrderNbr())).thenReturn(4);

        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });

        Long newId = contentManager.addHyperLink(pageId, request, userId);

        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertEquals(999L, captor.getValue().getAddUserId().longValue());
        assertEquals(request.label(), captor.getValue().getQuestionLabel());
        assertEquals(request.linkUrl(), captor.getValue().getDefaultValue());
        assertNotNull(newId);
    }

    @Test
    void should_add_read_only_comments_to_page() {
        var request = new AddStaticReadOnlyCommentsRequest("comments test", null, 123L);

        Long pageId = 321L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        Long userId = 999L;

        WaUiMetadata subsec = new WaUiMetadata();
        subsec.setOrderNbr(4);

        when(uiMetadatumRepository.findById(request.subSectionId())).thenReturn(Optional.of(subsec));

        when(uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subsec.getOrderNbr())).thenReturn(4);

        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });

        Long newId = contentManager.addReadOnlyComments(pageId, request, userId);


        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertEquals(999L, captor.getValue().getAddUserId().longValue());
        assertEquals(request.commentsText(), captor.getValue().getQuestionLabel());
        assertNotNull(newId);
    }

    @Test
    void should_add_read_only_participants_list() {
        var request = new AddStaticElementDefaultRequest(null, 10L);

        Long pageId = 123L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        Long userId = 999L;

        WaUiMetadata subsec = new WaUiMetadata();
        subsec.setOrderNbr(4);

        when(uiMetadatumRepository.findById(request.subSectionId())).thenReturn(Optional.of(subsec));

        when(uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subsec.getOrderNbr())).thenReturn(4);

        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });

        Long newId = contentManager.addReadOnlyParticipantsList(pageId, request, userId);

        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertEquals(999L, captor.getValue().getAddUserId().longValue());
        assertNotNull(newId);
    }

    @Test
    void should_add_original_electronic_doc_list() {
        var request = new AddStaticElementDefaultRequest(null, 10L);

        Long pageId = 123L;
        when(entityManager.getReference(WaTemplate.class, pageId)).thenReturn(new WaTemplate());

        Long userId = 999L;

        WaUiMetadata subsec = new WaUiMetadata();
        subsec.setOrderNbr(4);

        when(uiMetadatumRepository.findById(request.subSectionId())).thenReturn(Optional.of(subsec));

        when(uiMetadatumRepository.findMaxOrderNbrForSubsection(pageId, subsec.getOrderNbr())).thenReturn(4);

        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(uiMetadatumRepository.save(captor.capture())).thenAnswer(m -> {
            WaUiMetadata savedMetadatum = m.getArgument(0);
            savedMetadatum.setId(77L);
            return savedMetadatum;
        });

        Long newId = contentManager.addOriginalElectronicDocList(pageId, request, userId);

        verify(uiMetadatumRepository, times(1)).save(Mockito.any());
        assertEquals(999L, captor.getValue().getAddUserId().longValue());
        assertNotNull(newId);
    }

    @Test
    void should_delete_static_element_from_page() {
        var request = new DeleteStaticElementRequest(123L);

        Long pageId = 321L;

        WaUiMetadata component = new WaUiMetadata();
        component.setOrderNbr(7);

        when(uiMetadatumRepository.findById(request.componentId())).thenReturn(Optional.of(component));

        contentManager.deleteStaticElement(pageId, request);

        
    }

}
