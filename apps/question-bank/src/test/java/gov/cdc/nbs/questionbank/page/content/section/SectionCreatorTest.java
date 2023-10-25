package gov.cdc.nbs.questionbank.page.content.section;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import gov.cdc.nbs.questionbank.page.content.section.exception.CreateSectionException;
import gov.cdc.nbs.questionbank.page.content.section.model.Section;
import gov.cdc.nbs.questionbank.page.content.section.request.CreateSectionRequest;

@ExtendWith(MockitoExtension.class)
class SectionCreatorTest {

    @Mock
    private WaUiMetadataRepository repository;

    @Mock
    private WaTemplateRepository templateRepository;

    @Mock
    private PageContentIdGenerator idGenerator;

    @InjectMocks
    private SectionCreator creator;


    @Test
    void should_create_section() {
        // Given a page exists that is a Draft
        when(templateRepository.findById(1l)).thenReturn(Optional.of(draftPage()));

        // And a Tab exists on the page
        when(repository.findById(2l)).thenReturn(Optional.of(tab()));

        // And a working repository
        when(repository.save(Mockito.any())).thenAnswer(i -> {
            WaUiMetadata saved = i.getArgument(0);
            saved.setId(94l);
            return saved;
        });

        // When a request is processed to add a section to a tab
        Section section = creator.create(1l, 44l, new CreateSectionRequest(2l, "section", false));

        // Then the section is created
        assertEquals(94l, section.id());
        assertEquals("section", section.name());
        assertEquals(false, section.visible());
        verify(repository).incrementOrderNbrGreaterThanOrEqualTo(1l, 45);
    }

    @Test
    void should_not_create_section_page_is_not_draft() {
        // Given a page exists that is a Draft
        when(templateRepository.findById(1l)).thenReturn(Optional.empty());

        // When a request is processed to add a section to a tab
        // Then an exception is thrown
        CreateSectionRequest request = new CreateSectionRequest(2l, "section", false);
        assertThrows(CreateSectionException.class, () -> creator.create(1l, 5l, request));
    }

    @Test
    void should_not_create_section_section_not_found() {
        // Given a page exists that is a Draft
        when(templateRepository.findById(1l)).thenReturn(Optional.of(draftPage()));

        // And a Tab does not exist
        when(repository.findById(2l)).thenReturn(Optional.empty());

        // When a request is processed to add a section to a section
        // Then an exception is thrown
        CreateSectionRequest request = new CreateSectionRequest(2l, "section", true);
        assertThrows(CreateSectionException.class, () -> creator.create(1l,5l, request));
    }

    @Test
    void should_not_create_missing_name() {
        // When a request is made without a name
        // Then an exception is thrown
        CreateSectionRequest request = new CreateSectionRequest(2l, "", true);
        assertThrows(CreateSectionException.class, () -> creator.create(1l, 5l, request));
    }

    @Test
    void should_not_create_missing_null_name() {
        // When a request is made without a name
        // Then an exception is thrown
        assertThrows(CreateSectionException.class, () -> creator.create(1l, 5l, null));
    }

    private WaTemplate draftPage() {
        WaTemplate page = new WaTemplate();
        page.setId(1l);
        page.setTemplateType("Draft");
        return page;
    }

    private WaUiMetadata tab() {
        WaUiMetadata tab = new WaUiMetadata();
        tab.setId(2l);
        tab.setNbsUiComponentUid(1010l);
        tab.setOrderNbr(44);
        return tab;
    }
}
