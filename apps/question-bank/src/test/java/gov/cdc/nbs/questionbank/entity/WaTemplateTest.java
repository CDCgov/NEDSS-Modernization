package gov.cdc.nbs.questionbank.entity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.section.exception.DeleteSectionException;

class WaTemplateTest {

    @Test
    void should_delete_section() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        WaUiMetadata section = section(page, 3l, 3);
        page.addSection(section);

        // When a delete section request is processed
        page.deleteSection(3l);

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(section));
    }

    @Test
    void should_delete_section_before_tab() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        WaUiMetadata section = section(page, 3l, 3);
        page.addSection(section);

        // And there is another tab
        page.addTab(tab(page, 4l, 4));

        // When a delete section request is processed
        page.deleteSection(3l);

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(section));
    }

    @Test
    void should_delete_section_before_another_section() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        WaUiMetadata section = section(page, 3l, 3);
        page.addSection(section);

        // And there is another section
        page.addSection(section(page, 4l, 4));

        // When a delete section request is processed
        page.deleteSection(3l);

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(section));
    }

    @Test
    void should_note_delete_no_section() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // When a delete section request is processed
        // Then an exception is thrown
        assertThrows(DeleteSectionException.class, () -> page.deleteSection(3l));
    }

    @Test
    void should_note_delete_section_has_content() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        WaUiMetadata section = section(page, 3l, 3);
        page.addSection(section);

        // And that section has content
        page.addContent(
                "question",
                1008,
                4,
                999l,
                Instant.now());

        // When a delete section request is processed
        // Then an exception is thrown
        assertThrows(DeleteSectionException.class, () -> page.deleteSection(3l));
    }



    private WaUiMetadata tab(WaTemplate page, Long id, int orderNbr) {
        WaUiMetadata tab = new WaUiMetadata(page, new PageContentCommand.AddTab(
                page.getId(),
                "tab",
                true,
                "tab",
                orderNbr,
                999l,
                Instant.now()));
        tab = Mockito.spy(tab);
        when(tab.getId()).thenReturn(id);
        return tab;
    }

    private WaUiMetadata section(WaTemplate page, Long id, int orderNbr) {
        WaUiMetadata section = new WaUiMetadata(
                page,
                new PageContentCommand.AddSection(
                        page.getId(),
                        "section",
                        true,
                        "section",
                        orderNbr,
                        999l,
                        Instant.now()));
        section = Mockito.spy(section);
        when(section.getId()).thenReturn(id);
        return section;
    }

}
