package gov.cdc.nbs.questionbank.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.PageCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.GroupSubsection;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import java.time.Instant;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class WaTemplateTest {

  @Test
  void section_add() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // When an add section request is processed
    WaUiMetadata section =
        page.addSection(
            new PageContentCommand.AddSection(
                "section label", true, "sectionIdentifier", 2, 999L, Instant.now()));

    // Then the section is added
    assertNotNull(section);
    assertTrue(page.getUiMetadata().contains(section));
    assertEquals(2L, page.getUiMetadata().get(0).getId().longValue());
    assertEquals("sectionIdentifier", page.getUiMetadata().get(1).getQuestionIdentifier());
    assertEquals("section label", page.getUiMetadata().get(1).getQuestionLabel());
  }

  @Test
  void section_add_error_page_not_draft() {
    // Given a template that is not a draft
    WaTemplate page =
        new WaTemplate()
            .publish(new PageCommand.Publish(227L, Instant.parse("2022-01-18T01:15:50Z")));

    // When an add section request is processed
    PageContentCommand.AddSection command =
        new PageContentCommand.AddSection(
            "section label", true, "sectionIdentifier", 2, 999L, Instant.now());

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addSection(command));
  }

  @Test
  void section_add_error_tab_not_found() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And there is no tab

    // When an add section request is processed
    PageContentCommand.AddSection command =
        new PageContentCommand.AddSection(
            "section label", true, "sectionIdentifier", 2, 999L, Instant.now());

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addSection(command));
  }

  @Test
  void section_delete() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    WaUiMetadata section = section(page, 3L, 3);
    page.addSection(section);

    // When a delete section request is processed
    page.deleteSection(new PageContentCommand.DeleteSection(3L, 999L, Instant.now()));

    // Then the section is removed
    assertFalse(page.getUiMetadata().contains(section));
  }

  @Test
  void section_delete_before_tab() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    WaUiMetadata section = section(page, 3L, 3);
    page.addSection(section);

    // And there is another tab
    page.addTab(tab(page, 4L, 4));

    // When a delete section request is processed
    page.deleteSection(new PageContentCommand.DeleteSection(3L, 999L, Instant.now()));

    // Then the section is removed
    assertFalse(page.getUiMetadata().contains(section));
  }

  @Test
  void section_delete_before_another_section() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    WaUiMetadata section = section(page, 3L, 3);
    page.addSection(section);

    // And there is another section
    page.addSection(section(page, 4L, 4));

    // When a delete section request is processed
    page.deleteSection(new PageContentCommand.DeleteSection(3L, 999L, Instant.now()));

    // Then the section is removed
    assertFalse(page.getUiMetadata().contains(section));
  }

  @Test
  void section_delete_error_no_section() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // When a delete section request is processed
    PageContentCommand.DeleteSection command =
        new PageContentCommand.DeleteSection(3L, 999L, Instant.now());
    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.deleteSection(command));
  }

  @Test
  void section_delete_error_section_has_content() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    page.addSection(section(page, 3L, 3));

    // And that section has a subsection
    page.addSubSection(subsection(page, 4L, 4));

    // When a delete section request is processed
    PageContentCommand.DeleteSection command =
        new PageContentCommand.DeleteSection(3L, 999L, Instant.now());
    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.deleteSection(command));
  }

  @Test
  void subsection_delete() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    page.addSection(section(page, 3L, 3));

    // And that section has a subsection
    WaUiMetadata subsection = subsection(page, 4L, 4);
    page.addSubSection(subsection);

    // When a delete subsection request is processed
    page.deleteSubsection(new PageContentCommand.DeleteSubsection(4L, 999L, Instant.now()));

    // Then the section is removed
    assertFalse(page.getUiMetadata().contains(subsection));
  }

  @Test
  void subsection_delete_before_tab() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    page.addSection(section(page, 3L, 3));

    // And that section has a subsection
    WaUiMetadata subsection = subsection(page, 4L, 4);
    page.addSubSection(subsection);

    // And there is another tab
    page.addTab(tab(page, 5L, 5));

    // When a delete subsection request is processed
    page.deleteSubsection(new PageContentCommand.DeleteSubsection(4L, 999L, Instant.now()));

    // Then the section is removed
    assertFalse(page.getUiMetadata().contains(subsection));
  }

  @Test
  void subsection_delete_before_another_subsection() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    page.addSection(section(page, 3L, 3));

    // And that section has a subsection
    WaUiMetadata subsection = subsection(page, 4L, 4);
    page.addSubSection(subsection);

    // And there is another subsection
    page.addSubSection(subsection(page, 5L, 5));

    // When a delete subsection request is processed
    page.deleteSubsection(new PageContentCommand.DeleteSubsection(4L, 999L, Instant.now()));

    // Then the section is removed
    assertFalse(page.getUiMetadata().contains(subsection));
  }

  @Test
  void subsection_delete_before_a_section() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    page.addSection(section(page, 3L, 3));

    // And that section has a subsection
    WaUiMetadata subsection = subsection(page, 4L, 4);
    page.addSubSection(subsection);

    // And there is another section
    page.addSection(section(page, 5L, 5));

    // When a delete subsection request is processed
    page.deleteSubsection(new PageContentCommand.DeleteSubsection(4L, 999L, Instant.now()));

    // Then the section is removed
    assertFalse(page.getUiMetadata().contains(subsection));
  }

  @Test
  void subsection_delete_error_no_subsection() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // When a delete section request is processed
    PageContentCommand.DeleteSubsection command =
        new PageContentCommand.DeleteSubsection(3L, 999L, Instant.now());
    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.deleteSubsection(command));
  }

  @Test
  void subsection_delete_error_subsection_has_content() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    WaUiMetadata section = section(page, 3L, 3);
    page.addSection(section);

    // And that section has a subsection
    page.addSubSection(subsection(page, 4L, 4));

    // And that subsection has content
    page.addContent("question", 1008, 5, 999L, Instant.now(), null, null);

    // When a delete subsection request is processed
    PageContentCommand.DeleteSubsection command =
        new PageContentCommand.DeleteSubsection(4L, 999L, Instant.now());
    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.deleteSubsection(command));
  }

  @Test
  void subsection_add() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // When an add subsection request is processed
    WaUiMetadata subsection =
        page.addSubSection(
            new PageContentCommand.AddSubsection(
                "subsection label", true, "subsectionIdentifier", 3L, 999L, Instant.now()));

    // Then the section is added
    assertNotNull(subsection);
    assertTrue(page.getUiMetadata().contains(subsection));
    assertEquals(2L, page.getUiMetadata().get(0).getId().longValue());
    assertEquals(3L, page.getUiMetadata().get(1).getId().longValue());
    assertEquals("subsectionIdentifier", page.getUiMetadata().get(2).getQuestionIdentifier());
    assertEquals("subsection label", page.getUiMetadata().get(2).getQuestionLabel());
  }

  @Test
  void subsection_add_error_page_not_draft() {
    // Given a template that is not a draft
    WaTemplate page =
        new WaTemplate()
            .publish(new PageCommand.Publish(227L, Instant.parse("2022-01-18T01:15:50Z")));
    // When an add subsection request is processed
    PageContentCommand.AddSubsection command =
        new PageContentCommand.AddSubsection(
            "subsection label", true, "subsectionIdentifier", 3, 999L, Instant.now());

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addSubSection(command));
  }

  @Test
  void subsection_add_error_section_not_found() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And there is no section

    // When an add subsection request is processed
    PageContentCommand.AddSubsection command =
        new PageContentCommand.AddSubsection(
            "subsection label", true, "subsectionIdentifier", 3, 999L, Instant.now());

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addSubSection(command));
  }

  @Test
  void subsection_update() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    WaUiMetadata section = section(page, 3L, 3);
    page.addSection(section);

    // And that section has a subsection
    WaUiMetadata subsection = subsection(page, 4L, 4);
    page.addSubSection(subsection);

    // When an update subsection command is processed
    Instant now = Instant.now();
    PageContentCommand.UpdateSubsection command =
        new PageContentCommand.UpdateSubsection("updated subsection label", false, 4, 998L, now);
    WaUiMetadata updated = page.updateSubSection(command, ss -> subsection);

    // Then the subsection is updated
    assertNotNull(updated);
    assertEquals("updated subsection label", updated.getQuestionLabel());
    assertEquals(now, page.getLastChgTime());
    assertEquals(998L, page.getLastChgUserId().longValue());
  }

  @Test
  void subsection_update_error_non_draft() {
    // Given a template that is not a Draft
    WaTemplate page =
        new WaTemplate()
            .publish(new PageCommand.Publish(227L, Instant.parse("2022-01-18T01:15:50Z")));

    // When an update subsection command is processed
    Instant now = Instant.now();
    PageContentCommand.UpdateSubsection command =
        new PageContentCommand.UpdateSubsection("updated subsection label", false, 4, 998L, now);

    // Then an exception is thrown
    assertThrows(
        PageContentModificationException.class, () -> page.updateSubSection(command, null));
  }

  @Test
  void section_update() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    WaUiMetadata section = section(page, 3L, 3);
    page.addSection(section);

    // When an update section command is processed
    Instant now = Instant.now();
    PageContentCommand.UpdateSection command =
        new PageContentCommand.UpdateSection("updated section label", false, 3, 998L, now);
    WaUiMetadata updated = page.updateSection(command, s -> section);

    // Then the subsection is updated
    assertNotNull(updated);
    assertEquals("updated section label", updated.getQuestionLabel());
    assertEquals(now, page.getLastChgTime());
    assertEquals(998L, page.getLastChgUserId().longValue());
  }

  @Test
  void section_update_error_non_draft() {
    // Given a template that is not a Draft
    WaTemplate page =
        new WaTemplate()
            .publish(new PageCommand.Publish(227L, Instant.parse("2022-01-18T01:15:50Z")));

    // When an update section command is processed
    Instant now = Instant.now();
    PageContentCommand.UpdateSection command =
        new PageContentCommand.UpdateSection("updated section label", false, 4, 998L, now);

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.updateSection(command, null));
  }

  @Test
  void tab_update() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    WaUiMetadata tab = tab(page, 2L, 2);
    page.addTab(tab);

    // When an update tab command is processed
    Instant now = Instant.now();
    PageContentCommand.UpdateTab command =
        new PageContentCommand.UpdateTab("updated tab label", false, 2, 998L, now);
    WaUiMetadata updated = page.updateTab(command, t -> tab);

    // Then the subsection is updated
    assertNotNull(updated);
    assertEquals("updated tab label", updated.getQuestionLabel());
    assertEquals(now, page.getLastChgTime());
    assertEquals(998L, page.getLastChgUserId().longValue());
  }

  @Test
  void tab_update_error_non_draft() {
    // Given a template that is not a Draft
    WaTemplate page =
        new WaTemplate()
            .publish(new PageCommand.Publish(227L, Instant.parse("2022-01-18T01:15:50Z")));

    // When an update tab command is processed
    Instant now = Instant.now();
    PageContentCommand.UpdateSection command =
        new PageContentCommand.UpdateSection("updated tab label", false, 4, 998L, now);

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.updateSection(command, null));
  }

  @Test
  void tab_delete() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    WaUiMetadata tab = tab(page, 2L, 2);
    page.addTab(tab);

    // When a delete tab request is processed
    page.deleteTab(new PageContentCommand.DeleteTab(2L, 999L, Instant.now()));

    // Then the tab is removed
    assertFalse(page.getUiMetadata().contains(tab));
  }

  @Test
  void tab_delete_before_tab() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    WaUiMetadata tab = tab(page, 2L, 2);
    page.addTab(tab);

    // And there is another tab
    page.addTab(tab(page, 4L, 4));

    // When a delete tab request is processed
    page.deleteTab(new PageContentCommand.DeleteTab(2L, 999L, Instant.now()));

    // Then the tab is removed
    assertFalse(page.getUiMetadata().contains(tab));
  }

  @Test
  void tab_delete_error_no_tab() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // When a delete tab request is processed
    PageContentCommand.DeleteTab command =
        new PageContentCommand.DeleteTab(2L, 999L, Instant.now());
    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.deleteTab(command));
  }

  @Test
  void tab_delete_error_tab_has_content() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And that tab has a section
    page.addSection(section(page, 3L, 3));

    // When a delete section request is processed
    PageContentCommand.DeleteTab command =
        new PageContentCommand.DeleteTab(2L, 999L, Instant.now());
    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.deleteTab(command));
  }

  @Test
  void tab_add() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // When an add tab request is processed
    WaUiMetadata tab =
        page.addTab(
            new PageContentCommand.AddTab("tab label", true, "tabIdentifier", 999L, Instant.now()));

    // Then the tab is added
    assertNotNull(tab);
    assertTrue(page.getUiMetadata().contains(tab));
    assertEquals("tabIdentifier", page.getUiMetadata().get(0).getQuestionIdentifier());
    assertEquals("tab label", page.getUiMetadata().get(0).getQuestionLabel());
  }

  @Test
  void tab_add_error_page_not_draft() {
    // Given a template that is not a draft
    WaTemplate page =
        new WaTemplate()
            .publish(new PageCommand.Publish(227L, Instant.parse("2022-01-18T01:15:50Z")));

    // When an add tab request is processed
    PageContentCommand.AddTab command =
        new PageContentCommand.AddTab("tab label", true, "tabIdentifier", 999L, Instant.now());

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addTab(command));
  }

  @Test
  void question_add() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection
    page.addSubSection(subsection(page, 4L, 4));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // When an add question request is processed
    WaUiMetadata questionMeta =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now()));

    // Then the question is added
    assertNotNull(questionMeta);
    assertTrue(page.getUiMetadata().contains(questionMeta));
    assertEquals(
        question.getQuestionIdentifier(), page.getUiMetadata().get(3).getQuestionIdentifier());
    assertEquals(5, questionMeta.getOrderNbr().intValue());
  }

  @Test
  void question_add_grouped_subsection() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection that is grouped
    page.addSubSection(subsection(page, 4L, 4));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // And an add question request is processed
    WaUiMetadata questionMeta =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now()));
    questionMeta.setWaRdbMetadatum(new WaRdbMetadata());
    questionMeta.setId(765l);

    // And the subsection is grouped
    page.groupSubSection(
        new GroupSubsection(
            4l,
            "BLOCK_NAME",
            Arrays.asList(new GroupSubSectionRequest.Batch(765l, true, "question label", 100)),
            5,
            99l,
            Instant.now()));

    // Then the question is grouped
    assertNotNull(questionMeta);
    assertThat(questionMeta.getBlockNm()).isEqualTo("BLOCK_NAME");
    assertThat(questionMeta.getBatchTableHeader()).isEqualTo("question label");
    assertThat(questionMeta.getBatchTableColumnWidth()).isEqualTo(100);
    assertThat(questionMeta.getBatchTableAppearIndCd()).isEqualTo('Y');
    assertThat(questionMeta.getQuestionGroupSeqNbr()).isEqualTo(1);
    assertThat(questionMeta.getWaRdbMetadatum().getBlockPivotNbr()).isEqualTo(5);
  }

  @Test
  void question_add_question_to_grouped_subsection() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection that is grouped
    page.addSubSection(subsection(page, 4L, 4));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // And an add question request is processed
    WaUiMetadata questionMeta =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now()));
    questionMeta.setWaRdbMetadatum(new WaRdbMetadata());
    questionMeta.setId(765l);

    // And the subsection is grouped
    page.groupSubSection(
        new GroupSubsection(
            4l,
            "BLOCK_NAME",
            Arrays.asList(new GroupSubSectionRequest.Batch(765l, true, "question label", 100)),
            5,
            99l,
            Instant.now()));

    // When a question is added to a grouped subsection
    WaQuestion question2 = QuestionEntityMother.numericQuestion();

    // And an add question request is processed
    WaUiMetadata questionMeta2 =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question2, 4L, 98L, Instant.now()));

    // Then it has proper values set
    assertThat(questionMeta2).isNotNull();
    assertThat(questionMeta2.getBlockNm()).isEqualTo("BLOCK_NAME");
    assertThat(questionMeta2.getBatchTableHeader()).isNull();
    assertThat(questionMeta2.getBatchTableColumnWidth()).isNull();
    assertThat(questionMeta2.getBatchTableAppearIndCd()).isEqualTo('N');
    assertThat(questionMeta2.getQuestionGroupSeqNbr()).isEqualTo(1);
    assertThat(questionMeta2.getWaRdbMetadatum().getBlockPivotNbr()).isEqualTo(5);
  }

  @Test
  void question_add_question_to_grouped_subsection_with_more_than_20() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection that is grouped
    page.addSubSection(subsection(page, 4L, 4));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // And an add question request is processed
    WaUiMetadata questionMeta =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now()));
    questionMeta.setWaRdbMetadatum(new WaRdbMetadata());
    questionMeta.setId(765l);

    // And the subsection is grouped
    page.groupSubSection(
        new GroupSubsection(
            4l,
            "BLOCK_NAME",
            Arrays.asList(new GroupSubSectionRequest.Batch(765l, true, "question label", 100)),
            5,
            99l,
            Instant.now()));

    // subsection is at order nbr 4
    // so setting the first question to 24 makes it look like subsection already contains 20
    questionMeta.setOrderNbr(24);

    // And a question exists
    WaQuestion question2 = QuestionEntityMother.numericQuestion();

    // When an add question request is processed
    PageContentCommand.AddQuestion command =
        new PageContentCommand.AddQuestion(page.getId(), question2, 4L, 98L, Instant.now());
    // then an exception is thrown due to the grouped subsection containing too many questions
    assertThrows(
        PageContentModificationException.class,
        () -> page.addQuestion(command),
        "Unable to add more than 20 questions to a grouped subsection");
  }

  @Test
  void question_add_subsection_follows() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection
    page.addSubSection(subsection(page, 4L, 4));

    // And it has another subsection
    page.addSubSection(subsection(page, 5L, 5));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // When an add question request is processed
    WaUiMetadata questionMeta =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now()));

    // Then the question is added
    assertNotNull(questionMeta);
    assertTrue(page.getUiMetadata().contains(questionMeta));
    assertEquals(
        question.getQuestionIdentifier(), page.getUiMetadata().get(3).getQuestionIdentifier());
    assertEquals(5, questionMeta.getOrderNbr().intValue());
  }

  @Test
  void question_add_tab_follows() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection
    page.addSubSection(subsection(page, 4L, 4));

    // And it has another tab
    page.addTab(tab(page, 5L, 5));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // When an add question request is processed
    WaUiMetadata questionMeta =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now()));

    // Then the question is added
    assertNotNull(questionMeta);
    assertTrue(page.getUiMetadata().contains(questionMeta));
    assertEquals(
        question.getQuestionIdentifier(), page.getUiMetadata().get(3).getQuestionIdentifier());
    assertEquals(5, questionMeta.getOrderNbr().intValue());
  }

  @Test
  void question_add_section_follows() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection
    page.addSubSection(subsection(page, 4L, 4));

    // And it has another section
    page.addSection(section(page, 5L, 5));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // When an add question request is processed
    WaUiMetadata questionMeta =
        page.addQuestion(
            new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now()));

    // Then the question is added
    assertNotNull(questionMeta);
    assertTrue(page.getUiMetadata().contains(questionMeta));
    assertEquals(
        question.getQuestionIdentifier(), page.getUiMetadata().get(3).getQuestionIdentifier());
    assertEquals(5, questionMeta.getOrderNbr().intValue());
  }

  @Test
  void question_add_error_page_not_draft() {
    // Given a template that is not a draft
    WaTemplate page =
        new WaTemplate()
            .publish(new PageCommand.Publish(227L, Instant.parse("2022-01-18T01:15:50Z")));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // When an add question request is processed
    PageContentCommand.AddQuestion command =
        new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now());

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addQuestion(command));
  }

  @Test
  void question_add_error_question_already_present() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has a subsection
    page.addSubSection(subsection(page, 4L, 4));

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // And an add question request is processed
    PageContentCommand.AddQuestion command =
        new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now());
    WaUiMetadata questionMeta = page.addQuestion(command);

    // And the question is added
    assertNotNull(questionMeta);
    assertTrue(page.getUiMetadata().contains(questionMeta));

    // When another request is processed to add the same question
    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addQuestion(command));
  }

  @Test
  void question_add_error_subsection_not_found() {
    // Given a template
    WaTemplate page = new WaTemplate();

    // And it has a tab
    page.addTab(tab(page, 2L, 2));

    // And it has a section
    page.addSection(section(page, 3L, 3));

    // And it has no subsection

    // And a question exists
    WaQuestion question = QuestionEntityMother.textQuestion();

    // When an add question request is processed
    PageContentCommand.AddQuestion command =
        new PageContentCommand.AddQuestion(page.getId(), question, 4L, 98L, Instant.now());

    // Then an exception is thrown
    assertThrows(PageContentModificationException.class, () -> page.addQuestion(command));
  }

  private WaUiMetadata tab(WaTemplate page, Long id, int orderNbr) {
    WaUiMetadata tab =
        new WaUiMetadata(
            page, new PageContentCommand.AddTab("tab", true, "tab", 999L, Instant.now()), orderNbr);
    tab = Mockito.spy(tab);
    when(tab.getId()).thenReturn(id);
    return tab;
  }

  private WaUiMetadata section(WaTemplate page, Long id, int orderNbr) {
    WaUiMetadata section =
        new WaUiMetadata(
            page,
            new PageContentCommand.AddSection("section", true, "section", 0, 999L, Instant.now()),
            orderNbr);
    section = Mockito.spy(section);
    when(section.getId()).thenReturn(id);
    return section;
  }

  private WaUiMetadata subsection(WaTemplate page, Long id, int orderNbr) {
    WaUiMetadata subsection =
        new WaUiMetadata(
            page,
            new PageContentCommand.AddSubsection(
                "subsection", true, "subsection", 0, 999L, Instant.now()),
            orderNbr);
    subsection = Mockito.spy(subsection);
    when(subsection.getId()).thenReturn(id);
    return subsection;
  }
}
