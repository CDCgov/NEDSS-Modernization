package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.UpdateSubsectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.UpdateSubsectionRequest;

@ExtendWith(MockitoExtension.class)
class SubsectionUpdaterTest {

    @Mock
    private WaUiMetadataRepository repository;

    @Mock
    private WaTemplateRepository templateRepository;

    @InjectMocks
    private SubsectionUpdater updater;


    @Test
    void should_update_subsection() {
        // Given a page is a draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And a subsection exists
        when(repository.findById(1l)).thenReturn(Optional.of(emptyMetadata()));

        // And a working repository
        when(repository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        // When a valid request is made to update the section
        UpdateSubsectionRequest request = new UpdateSubsectionRequest("New name", false);
        Subsection section = updater.update(2l, 1l, 3l, request);

        // Then the section is updated
        assertEquals(2l, section.id());
        assertEquals("New name", section.name());
        assertFalse(section.visible());
    }

    @Test
    void should_not_update_subsection_page_is_published() {
        // Given a page is published
        when(templateRepository.isPageDraft(2l)).thenReturn(false);

        // When a request is made
        // Then an exception is thrown
        UpdateSubsectionRequest request = new UpdateSubsectionRequest("New name", false);
        assertThrows(UpdateSubsectionException.class, () ->updater.update(2l, 1l, 3l, request));
    }

    @ParameterizedTest
    @MethodSource("badRequests")
    void should_not_update_subsection(UpdateSubsectionRequest request) {
        // When a request is made
        // Then an exception is thrown
        assertThrows(UpdateSubsectionException.class, () -> updater.update(2l, 1l, 3l, request));
        verifyNoInteractions(repository);
    }

    @Test
    void should_not_update_subsection_because_it_doesnt_exist() {
        // Given a page is a draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And a subsection does not exists
        when(repository.findById(1l)).thenReturn(Optional.empty());

        // When a request
        // Then an exception is thrown
        UpdateSubsectionRequest request = new UpdateSubsectionRequest("New name", false);
        assertThrows(UpdateSubsectionException.class, () ->updater.update(2l, 1l, 3l, request));
    }

    private static List<UpdateSubsectionRequest> badRequests() {
        return Arrays.asList(
                new UpdateSubsectionRequest("", false), // Empty name
                new UpdateSubsectionRequest(null, false), // null name
                null // Null request
        );
    }

    private WaUiMetadata emptyMetadata() {
        WaUiMetadata metadata = new WaUiMetadata();
        metadata.setId(2l);
        metadata.setNbsUiComponentUid(1016l);
        return metadata;
    }
}
