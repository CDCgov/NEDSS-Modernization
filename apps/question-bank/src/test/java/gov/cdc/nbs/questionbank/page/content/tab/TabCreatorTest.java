package gov.cdc.nbs.questionbank.page.content.tab;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.tab.exceptions.CreateTabException;
import gov.cdc.nbs.questionbank.page.content.tab.request.CreateTabRequest;
import gov.cdc.nbs.questionbank.page.content.tab.response.Tab;

@ExtendWith(MockitoExtension.class)
class TabCreatorTest {
    @Mock
    private WaUiMetadataRepository repository;
    @Mock
    private WaTemplateRepository templateRepository;
    @Mock
    private PageContentIdGenerator idGenerator;

    @InjectMocks
    private TabCreator creator;

    @Test
    void should_create_tab() {
        // Given a page that is a draft
        when(templateRepository.isPageDraft(1l)).thenReturn(true);
        when(templateRepository.getReferenceById(1l)).thenReturn(new WaTemplate());
        when(repository.findMaxOrderNbrForPage(1l)).thenReturn(1);
        ArgumentCaptor<WaUiMetadata> captor = ArgumentCaptor.forClass(WaUiMetadata.class);
        when(repository.save(captor.capture())).then(a -> a.getArgument(0));

        // And a valid id generator
        when(idGenerator.next()).thenReturn("GA00001");

        // When a request is processed to create a tab
        Tab actual = creator.create(1l, 2l, new CreateTabRequest("tab name", true));

        // Then a tab is created
        assertNotNull(actual);
        assertEquals("tab name", actual.name());
        assertEquals(true, actual.visible());
        assertEquals("GA00001", captor.getValue().getQuestionIdentifier());
    }

    @ParameterizedTest
    @MethodSource("badRequests")
    void should_not_create(CreateTabRequest request) {
        // When a request is made
        // Then an exception is thrown
        assertThrows(CreateTabException.class, () -> creator.create(2l, 3l, request));
        verifyNoInteractions(repository);
    }

    private static List<CreateTabRequest> badRequests() {
        return Arrays.asList(
                new CreateTabRequest("", false), // Empty name
                new CreateTabRequest(null, false), // null name
                null // Null request
        );
    }

}
