package gov.cdc.nbs.questionbank.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;

class WaTemplateTest {

    @Test
    void section_add() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // When an add section request is processed
        WaUiMetadata section = page.addSection(new PageContentCommand.AddSection(
                "section label",
                true,
                "sectionIdentifier",
                2,
                999l,
                Instant.now()));

        // Then the section is added
        assertNotNull(section);
        assertTrue(page.getUiMetadata().contains(section));
        assertEquals(2l, page.getUiMetadata().get(0).getId().longValue());
        assertEquals("sectionIdentifier", page.getUiMetadata().get(1).getQuestionIdentifier());
        assertEquals("section label", page.getUiMetadata().get(1).getQuestionLabel());
    }

    @Test
    void section_add_error_page_not_draft() {
        // Given a template that is not a draft
        WaTemplate page = Mockito.spy(new WaTemplate());
        when(page.getTemplateType()).thenReturn("Published");

        // When an add section request is processed
        PageContentCommand.AddSection command = new PageContentCommand.AddSection(
                "section label",
                true,
                "sectionIdentifier",
                2,
                999l,
                Instant.now());

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.addSection(command));
    }

    @Test
    void section_add_error_tab_not_found() {
        // Given a template 
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And there is no tab

        // When an add section request is processed
        PageContentCommand.AddSection command = new PageContentCommand.AddSection(
                "section label",
                true,
                "sectionIdentifier",
                2,
                999l,
                Instant.now());

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.addSection(command));
    }

    @Test
    void section_delete() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        WaUiMetadata section = section(page, 3l, 3);
        page.addSection(section);

        // When a delete section request is processed
        page.deleteSection(new PageContentCommand.DeleteSection(3l, 999l, Instant.now()));

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(section));
    }

    @Test
    void section_delete_before_tab() {
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
        page.deleteSection(new PageContentCommand.DeleteSection(3l, 999l, Instant.now()));

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(section));
    }

    @Test
    void section_delete_before_another_section() {
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
        page.deleteSection(new PageContentCommand.DeleteSection(3l, 999l, Instant.now()));

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(section));
    }

    @Test
    void section_delete_error_no_section() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // When a delete section request is processed
        PageContentCommand.DeleteSection command = new PageContentCommand.DeleteSection(3l, 999l, Instant.now());
        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.deleteSection(command));
    }

    @Test
    void section_delete_error_section_has_content() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        page.addSection(section(page, 3l, 3));

        // And that section has a subsection
        page.addSubSection(subsection(page, 4l, 4));

        // When a delete section request is processed
        PageContentCommand.DeleteSection command = new PageContentCommand.DeleteSection(3l, 999l, Instant.now());
        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.deleteSection(command));
    }

    @Test
    void subsection_delete() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        page.addSection(section(page, 3l, 3));

        // And that section has a subsection
        WaUiMetadata subsection = subsection(page, 4l, 4);
        page.addSubSection(subsection);

        // When a delete subsection request is processed
        page.deleteSubsection(new PageContentCommand.DeleteSubsection(4l, 999l, Instant.now()));

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(subsection));
    }

    @Test
    void subsection_delete_before_tab() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        page.addSection(section(page, 3l, 3));

        // And that section has a subsection
        WaUiMetadata subsection = subsection(page, 4l, 4);
        page.addSubSection(subsection);

        // And there is another tab
        page.addTab(tab(page, 5l, 5));

        // When a delete subsection request is processed
        page.deleteSubsection(new PageContentCommand.DeleteSubsection(4l, 999l, Instant.now()));

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(subsection));
    }

    @Test
    void subsection_delete_before_another_subsection() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        page.addSection(section(page, 3l, 3));

        // And that section has a subsection
        WaUiMetadata subsection = subsection(page, 4l, 4);
        page.addSubSection(subsection);

        // And there is another subsection
        page.addSubSection(subsection(page, 5l, 5));

        // When a delete subsection request is processed
        page.deleteSubsection(new PageContentCommand.DeleteSubsection(4l, 999l, Instant.now()));

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(subsection));
    }

    @Test
    void subsection_delete_before_a_section() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        page.addSection(section(page, 3l, 3));

        // And that section has a subsection
        WaUiMetadata subsection = subsection(page, 4l, 4);
        page.addSubSection(subsection);

        // And there is another section
        page.addSection(section(page, 5l, 5));

        // When a delete subsection request is processed
        page.deleteSubsection(new PageContentCommand.DeleteSubsection(4l, 999l, Instant.now()));

        // Then the section is removed
        assertFalse(page.getUiMetadata().contains(subsection));
    }

    @Test
    void subsection_delete_error_no_subsection() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // When a delete section request is processed
        PageContentCommand.DeleteSubsection command = new PageContentCommand.DeleteSubsection(3l, 999l, Instant.now());
        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.deleteSubsection(command));
    }

    @Test
    void subsection_delete_error_subsection_has_content() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        WaUiMetadata section = section(page, 3l, 3);
        page.addSection(section);

        // And that section has a subsection
        page.addSubSection(subsection(page, 4l, 4));

        // And that subsection has content
        page.addContent(
                "question",
                1008,
                5,
                999l,
                Instant.now());

        // When a delete subsection request is processed
        PageContentCommand.DeleteSubsection command = new PageContentCommand.DeleteSubsection(4l, 999l, Instant.now());
        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.deleteSubsection(command));
    }

    @Test
    void subsection_add() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And it has a section
        page.addSection(section(page, 3l, 3));

        // When an add subsection request is processed
        WaUiMetadata subsection = page.addSubSection(new PageContentCommand.AddSubsection(
                "subsection label",
                true,
                "subsectionIdentifier",
                3l,
                999l,
                Instant.now()));

        // Then the section is added
        assertNotNull(subsection);
        assertTrue(page.getUiMetadata().contains(subsection));
        assertEquals(2l, page.getUiMetadata().get(0).getId().longValue());
        assertEquals(3l, page.getUiMetadata().get(1).getId().longValue());
        assertEquals("subsectionIdentifier", page.getUiMetadata().get(2).getQuestionIdentifier());
        assertEquals("subsection label", page.getUiMetadata().get(2).getQuestionLabel());
    }

    @Test
    void subsection_add_error_page_not_draft() {
        // Given a template that is not a draft
        WaTemplate page = Mockito.spy(new WaTemplate());
        when(page.getTemplateType()).thenReturn("Published");

        // When an add subsection request is processed
        PageContentCommand.AddSubsection command = new PageContentCommand.AddSubsection(
                "subsection label",
                true,
                "subsectionIdentifier",
                3,
                999l,
                Instant.now());

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.addSubSection(command));
    }

    @Test
    void subsection_add_error_section_not_found() {
        // Given a template 
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And there is no section

        // When an add subsection request is processed
        PageContentCommand.AddSubsection command = new PageContentCommand.AddSubsection(
                "subsection label",
                true,
                "subsectionIdentifier",
                3,
                999l,
                Instant.now());

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.addSubSection(command));
    }

    @Test
    void subsection_update() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        WaUiMetadata section = section(page, 3l, 3);
        page.addSection(section);

        // And that section has a subsection
        page.addSubSection(subsection(page, 4l, 4));

        // When an update subsection command is processed
        Instant now = Instant.now();
        PageContentCommand.UpdateSubsection command = new PageContentCommand.UpdateSubsection(
                "updated subsection label",
                false,
                4,
                998l,
                now);
        WaUiMetadata updated = page.updateSubSection(command);

        // Then the subsection is updated
        assertNotNull(updated);
        assertEquals("updated subsection label", updated.getQuestionLabel());
        assertEquals(now, page.getLastChgTime());
        assertEquals(998l, page.getLastChgUserId().longValue());
    }

    @Test
    void subsection_update_error_non_draft() {
        // Given a template that is not a Draft
        WaTemplate page = Mockito.spy(new WaTemplate());
        when(page.getTemplateType()).thenReturn("Published");

        // When an update subsection command is processed
        Instant now = Instant.now();
        PageContentCommand.UpdateSubsection command = new PageContentCommand.UpdateSubsection(
                "updated subsection label",
                false,
                4,
                998l,
                now);

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.updateSubSection(command));
    }

    @Test
    void subsection_update_error_no_subsection() {
        // Given a template 
        WaTemplate page = Mockito.spy(new WaTemplate());

        // When an update subsection command is processed
        Instant now = Instant.now();
        PageContentCommand.UpdateSubsection command = new PageContentCommand.UpdateSubsection(
                "updated subsection label",
                false,
                4,
                998l,
                now);

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.updateSubSection(command));
    }

    @Test
    void section_update() {
        // Given a template
        WaTemplate page = Mockito.spy(new WaTemplate());

        // And it has a tab
        page.addTab(tab(page, 2l, 2));

        // And that tab has a section
        page.addSection(section(page, 3l, 3));

        // When an update section command is processed
        Instant now = Instant.now();
        PageContentCommand.UpdateSection command = new PageContentCommand.UpdateSection(
                "updated section label",
                false,
                3,
                998l,
                now);
        WaUiMetadata updated = page.updateSection(command);

        // Then the subsection is updated
        assertNotNull(updated);
        assertEquals("updated section label", updated.getQuestionLabel());
        assertEquals(now, page.getLastChgTime());
        assertEquals(998l, page.getLastChgUserId().longValue());
    }

    @Test
    void section_update_error_non_draft() {
        // Given a template that is not a Draft
        WaTemplate page = Mockito.spy(new WaTemplate());
        when(page.getTemplateType()).thenReturn("Published");

        // When an update section command is processed
        Instant now = Instant.now();
        PageContentCommand.UpdateSection command = new PageContentCommand.UpdateSection(
                "updated section label",
                false,
                4,
                998l,
                now);

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.updateSection(command));
    }

    @Test
    void section_update_error_no_section() {
        // Given a template 
        WaTemplate page = Mockito.spy(new WaTemplate());

        // When an update section command is processed
        Instant now = Instant.now();
        PageContentCommand.UpdateSection command = new PageContentCommand.UpdateSection(
                "updated subsection label",
                false,
                4,
                998l,
                now);

        // Then an exception is thrown
        assertThrows(PageContentModificationException.class, () -> page.updateSection(command));
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
                        "section",
                        true,
                        "section",
                        0l,
                        999l,
                        Instant.now()),
                orderNbr);
        section = Mockito.spy(section);
        when(section.getId()).thenReturn(id);
        return section;
    }

    private WaUiMetadata subsection(WaTemplate page, Long id, int orderNbr) {
        WaUiMetadata subsection = new WaUiMetadata(
                page,
                new PageContentCommand.AddSubsection(
                        "subsection",
                        true,
                        "subsection",
                        0l,
                        999l,
                        Instant.now()),
                orderNbr);
        subsection = Mockito.spy(subsection);
        when(subsection.getId()).thenReturn(id);
        return subsection;
    }

}
