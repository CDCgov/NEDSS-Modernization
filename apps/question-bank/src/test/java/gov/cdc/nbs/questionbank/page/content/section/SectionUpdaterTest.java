package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
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
import gov.cdc.nbs.questionbank.page.content.section.exception.UpdateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.UpdateSectionRequest;

@ExtendWith(MockitoExtension.class)
class SectionUpdaterTest {

    @Mock
    WaUiMetadataRepository repository;

    @Mock
    WaTemplateRepository templateRepository;

    @InjectMocks
    private SectionUpdater updater;

    @Test
    void should_update_section() {
        // Given a page is a draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And a section exists
        when(repository.findById(1l)).thenReturn(Optional.of(emptyMetadata()));

        // And a working repository
        when(repository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        // When a valid request is made to update the section
        UpdateSectionRequest request = new UpdateSectionRequest("New name", false);
        Section section = updater.update(2l, 1l, 3l, request);

        // Then the section is updated
        assertEquals(2l, section.id());
        assertEquals("New name", section.name());
        assertFalse(section.visible());
    }

    @Test
    void should_not_update_section_published() {
        // Given a page is published
        when(templateRepository.isPageDraft(2l)).thenReturn(false);

        // When a valid request is made to update the section
        // Then an exception is thrown
        UpdateSectionRequest request = new UpdateSectionRequest("New name", false);
        assertThrows(UpdateSectionException.class, () ->updater.update(2l, 1l, 3l, request));
    }

    @ParameterizedTest
    @MethodSource("badRequests")
    void should_not_update_section_null_request(UpdateSectionRequest request) {
        // Given a page is a draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // When a request is made to update the section with a null request
        // Then an exception is thrown
        assertThrows(UpdateSectionException.class, () ->updater.update(2l, 1l, 3l, request));
        verifyNoInteractions(repository);
    }

    private static List<UpdateSectionRequest> badRequests() {
        return Arrays.asList(
                new UpdateSectionRequest("", false), // Empty name
                new UpdateSectionRequest(null, false), // null name
                null // Null request
        );
    }

    @Test
    void should_not_update_section_no_section() {
        // Given a page is a draft
        when(templateRepository.isPageDraft(2l)).thenReturn(true);

        // And a section does not exists
        when(repository.findById(1l)).thenReturn(Optional.empty());

        // When a valid request is made to update the section
        // Then an exception is thrown
        UpdateSectionRequest request = new UpdateSectionRequest("New name", false);
        assertThrows(UpdateSectionException.class, () ->updater.update(2l, 1l, 3l, request));
    }

    private WaUiMetadata emptyMetadata() {
        WaUiMetadata metadata = new WaUiMetadata();
        metadata.setId(2l);
        return metadata;
    }
}
