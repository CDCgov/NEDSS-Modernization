package gov.cdc.nbs.questionbank.page.content.subsection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import gov.cdc.nbs.questionbank.entity.repository.WaTemplateRepository;
import gov.cdc.nbs.questionbank.entity.repository.WaUiMetadataRepository;
import gov.cdc.nbs.questionbank.page.content.PageContentIdGenerator;
import gov.cdc.nbs.questionbank.page.content.subsection.exception.CreateSubsectionException;
import gov.cdc.nbs.questionbank.page.content.subsection.model.Subsection;
import gov.cdc.nbs.questionbank.page.content.subsection.request.CreateSubsectionRequest;

@ExtendWith(MockitoExtension.class)
class SubsectionCreatorTest {
    @Mock
    private WaUiMetadataRepository repository;
    @Mock
    private WaTemplateRepository templateRepository;
    @Mock
    private PageContentIdGenerator idGenerator;

    @InjectMocks
    private SubsectionCreator creator;

    @Test
    void should_create_subsection() {
        // Given a page exists that is a Draft
        when(templateRepository.findById(1l)).thenReturn(Optional.of(draftPage()));

        // And a Section exists on the page
        when(repository.findById(2l)).thenReturn(Optional.of(section()));

        // And a working repository
        when(repository.save(Mockito.any())).thenAnswer(i -> {
            WaUiMetadata saved = i.getArgument(0);
            saved.setId(94l);
            return saved;
        });

        // When a request is processed to add a Subsection to a section
        Subsection subsection = creator.create(
            1l,
             5l, 
            new CreateSubsectionRequest(
                2l,
                "subsection name",
                true));

        // Then the subsection is created
        assertEquals(94l, subsection.id());
        assertEquals("subsection name", subsection.name());
        assertEquals(true, subsection.visible());
        verify(repository).incrementOrderNbrGreaterThanOrEqualTo(1l, 45);
    }

    @Test
    void should_not_create_subsection_page_is_not_draft() {
        // Given a page exists that is a Draft
        when(templateRepository.findById(1l)).thenReturn(Optional.empty());

        // When a request is processed to add a Subsection to a section
        // Then an exception is thrown
        CreateSubsectionRequest request = new CreateSubsectionRequest(2l, "subsection name", true);
        assertThrows(CreateSubsectionException.class, () -> creator.create(1l,5l, request));
    }

    @Test
    void should_not_create_subsection_section_not_found() {
        // Given a page exists that is a Draft
        when(templateRepository.findById(1l)).thenReturn(Optional.of(draftPage()));

        // And a Section does not exist
        when(repository.findById(2l)).thenReturn(Optional.empty());

        // When a request is processed to add a Subsection to a section
        // Then an exception is thrown
        CreateSubsectionRequest request = new CreateSubsectionRequest(2l, "subsection name", true);
        assertThrows(CreateSubsectionException.class, () -> creator.create(1l,5l, request));
    }

    @Test
    void should_not_create_missing_name() {
        // When a request is made without a name
        // Then an exception is thrown
        CreateSubsectionRequest request = new CreateSubsectionRequest(2l, "", true);
        assertThrows(CreateSubsectionException.class, () -> creator.create(1l, 5l, request));
    }

    @Test
    void should_not_create_missing_null_name() {
        // When a request is made without a name
        // Then an exception is thrown
        assertThrows(CreateSubsectionException.class, () -> creator.create(1l, 5l, null));
    }

    private WaTemplate draftPage() {
        WaTemplate page = new WaTemplate();
        page.setId(1l);
        page.setTemplateType("Draft");
        return page;
    }

    private WaUiMetadata section() {
        WaUiMetadata section = new WaUiMetadata();
        section.setId(2l);
        section.setOrderNbr(44);
        return section;
    }
}
