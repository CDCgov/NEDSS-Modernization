package gov.cdc.nbs.questionbank.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.Update;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;

@ExtendWith(MockitoExtension.class)
class WaUiMetadataTest {

    @Test
    void should_set_valid_values_for_text_question() {
        Instant now = Instant.now();
        // Given a AddQuestion command
        WaTemplate page = page();
        TextQuestionEntity question = QuestionEntityMother.textQuestion();
        PageContentCommand.AddQuestion command = new PageContentCommand.AddQuestion(
                page.getId(),
                question,
                12,
                1L,
                now);
        // When a new WaUiMetadata entry is created
        WaUiMetadata metadata = new WaUiMetadata(page, command, 5);

        // Then the expected values are set
        assertDefaultValues(metadata);
        assertGeneralValues(metadata, command, 5);
        assertEquals(question.getDefaultValue(), metadata.getDefaultValue());
        assertEquals(question.getMask(), metadata.getMask());
        assertEquals(question.getFieldSize(), metadata.getFieldSize());
    }

    @Test
    void should_set_valid_values_for_date_question() {
        Instant now = Instant.now();
        // Given a AddQuestion command
        WaTemplate page = page();
        DateQuestionEntity question = QuestionEntityMother.dateQuestion();
        PageContentCommand.AddQuestion command = new PageContentCommand.AddQuestion(
                page.getId(),
                question,
                12,
                1L,
                now);
        // When a new WaUiMetadata entry is created
        WaUiMetadata metadata = new WaUiMetadata(page, command, 5);

        // Then the expected values are set
        assertDefaultValues(metadata);
        assertGeneralValues(metadata, command, 5);
        assertEquals(question.getMask(), metadata.getMask());
        assertEquals(question.getFutureDateIndCd(), metadata.getFutureDateIndCd());
    }

    @Test
    void should_set_valid_values_for_numeric_question() {
        Instant now = Instant.now();
        // Given a AddQuestion command
        WaTemplate page = page();
        NumericQuestionEntity question = QuestionEntityMother.numericQuestion();
        PageContentCommand.AddQuestion command = new PageContentCommand.AddQuestion(
                page.getId(),
                question,
                12,
                1L,
                now);
        // When a new WaUiMetadata entry is created
        WaUiMetadata metadata = new WaUiMetadata(page, command, 5);

        // Then the expected values are set
        assertDefaultValues(metadata);
        assertGeneralValues(metadata, command, 5);
        assertEquals(question.getMask(), metadata.getMask());
        assertEquals(question.getFieldSize(), metadata.getFieldSize());
        assertEquals(question.getDefaultValue(), metadata.getDefaultValue());
        assertEquals(question.getMinValue(), metadata.getMinValue());
        assertEquals(question.getMaxValue(), metadata.getMaxValue());
        assertEquals(question.getUnitTypeCd(), metadata.getUnitTypeCd());
        assertEquals(question.getUnitValue(), metadata.getUnitValue());
    }

    @Test
    void should_set_valid_values_for_coded_question() {
        Instant now = Instant.now();
        // Given a AddQuestion command
        WaTemplate page = page();
        CodedQuestionEntity question = QuestionEntityMother.codedQuestion();
        PageContentCommand.AddQuestion command = new PageContentCommand.AddQuestion(
                page.getId(),
                question,
                12,
                1L,
                now);
        // When a new WaUiMetadata entry is created
        WaUiMetadata metadata = new WaUiMetadata(page, command, 5);

        // Then the expected values are set
        assertDefaultValues(metadata);
        assertGeneralValues(metadata, command, 5);
        assertEquals(question.getCodeSetGroupId(), metadata.getCodeSetGroupId());
        assertEquals(question.getDefaultValue(), metadata.getDefaultValue());
    }

    @Test
    void should_throw_addQuestion_exception() {
        Instant now = Instant.now();
        // Given an AddQuestion command with invalid question type
        WaTemplate page = page();
        WaQuestion question = new WaQuestion() {
            @Override
            public String getDataType() {
                return "test";
            }

            @Override
            public void update(Update command) {

            }
        };

        PageContentCommand.AddQuestion command = new PageContentCommand.AddQuestion(
                page.getId(),
                question,
                12,
                1L,
                now);
        // When a new WaUiMetadata entry is created 
        // Then an exception is thrown
        assertThrows(AddQuestionException.class, () -> new WaUiMetadata(page, command, 5));

    }

    private void assertDefaultValues(WaUiMetadata metadata) {
        assertEquals('F', metadata.getStandardNndIndCd().charValue());
        assertEquals('F', metadata.getStandardQuestionIndCd().charValue());
        assertEquals("T", metadata.getEnableInd());
        assertEquals("T", metadata.getDisplayInd());
        assertEquals("F", metadata.getRequiredInd());
        assertEquals("USER", metadata.getEntryMethod());
        assertEquals("Active", metadata.getRecordStatusCd());
        assertEquals(1, metadata.getVersionCtrlNbr().intValue());
    }

    private void assertGeneralValues(WaUiMetadata metadata, PageContentCommand.AddQuestion command,
            Integer orderNumber) {
        var question = command.question();
        assertEquals(command.page(), metadata.getWaTemplateUid().getId());
        assertEquals(question.getNbsUiComponentUid(), metadata.getNbsUiComponentUid());
        assertEquals(question.getQuestionLabel(), metadata.getQuestionLabel());
        assertEquals(question.getQuestionToolTip(), metadata.getQuestionToolTip());
        assertEquals(orderNumber, metadata.getOrderNbr());
        assertEquals(question.getAdminComment(), metadata.getAdminComment());
        assertEquals(question.getDataLocation(), metadata.getDataLocation());
        assertEquals(question.getDescTxt(), metadata.getDescTxt());
        assertEquals(question.getQuestionType(), metadata.getQuestionType());
        assertEquals(question.getQuestionNm(), metadata.getQuestionNm());
        assertEquals(question.getQuestionIdentifier(), metadata.getQuestionIdentifier());
        assertEquals(question.getQuestionOid(), metadata.getQuestionOid());
        assertEquals(question.getQuestionOidSystemTxt(), metadata.getQuestionOidSystemTxt());
        assertEquals(question.getGroupNm(), metadata.getGroupNm());
        assertEquals(question.getSubGroupNm(), metadata.getSubGroupNm());
        assertEquals(question.getDataType(), metadata.getDataType());
        assertEquals(question.getOtherValueIndCd(), metadata.getOtherValueIndCd());
    }

    private WaTemplate page() {
        WaTemplate page = new WaTemplate();
        page.setId(2L);
        return page;

    }


    @Test
    void should_create_tab() {
        WaTemplate page = new WaTemplate();
        page.setId(123l);
        PageContentCommand.AddTab command = addTab(page);
        WaUiMetadata tabMetadata = new WaUiMetadata(page, command, 2);

        assertEquals(1010L, tabMetadata.getNbsUiComponentUid().longValue());
        assertEquals(page.getId(), tabMetadata.getWaTemplateUid().getId());
        assertEquals(command.label(), tabMetadata.getQuestionLabel());
        assertEquals(command.visible() ? "T" : "F", tabMetadata.getDisplayInd());
        assertEquals(command.identifier(), tabMetadata.getQuestionIdentifier());
        assertEquals(2, tabMetadata.getOrderNbr().intValue());
        assertEquals(command.requestedOn(), tabMetadata.getRecordStatusTime());
        assertEquals("F", tabMetadata.getRequiredInd());
        assertEquals(1, tabMetadata.getVersionCtrlNbr().intValue());
        assertEquals('F', tabMetadata.getStandardNndIndCd().charValue());
        assertEquals('T', tabMetadata.getPublishIndCd().charValue());
        assertEquals("T", tabMetadata.getEnableInd());

        assertEquals(command.requestedOn(), tabMetadata.getAddTime());
        assertEquals(command.userId(), tabMetadata.getAddUserId().longValue());
        assertEquals(command.requestedOn(), tabMetadata.getLastChgTime());
        assertEquals(command.userId(), tabMetadata.getLastChgUserId().longValue());
        assertEquals("Active", tabMetadata.getRecordStatusCd());
        assertEquals(command.requestedOn(), tabMetadata.getRecordStatusTime());
    }

    @Test
    void should_update_tab() {
        WaTemplate page = new WaTemplate();
        page.setId(123l);
        PageContentCommand.AddTab command = addTab(page);
        WaUiMetadata tabMetadata = new WaUiMetadata(page, command, 2);

        PageContentCommand.UpdateTab updateCommand = updateTab();
        tabMetadata.update(updateCommand);

        assertEquals(updateCommand.label(), tabMetadata.getQuestionLabel());
        assertEquals(updateCommand.visible() ? "T" : "F", tabMetadata.getDisplayInd());
        assertEquals(updateCommand.userId(), tabMetadata.getLastChgUserId().longValue());
        assertEquals(updateCommand.requestedOn(), tabMetadata.getLastChgTime());
    }

    @Test
    void should_update_subsection() {
        WaTemplate page = new WaTemplate();
        page.setId(123l);
        PageContentCommand.AddSubsection command = addSubsection(page);
        WaUiMetadata subsectionMetadata = new WaUiMetadata(page, command, 3);

        PageContentCommand.UpdateSubsection updateCommand = updateSubsection();
        subsectionMetadata.update(updateCommand);

        assertEquals(updateCommand.label(), subsectionMetadata.getQuestionLabel());
        assertEquals(updateCommand.visible() ? "T" : "F", subsectionMetadata.getDisplayInd());
        assertEquals(updateCommand.userId(), subsectionMetadata.getLastChgUserId().longValue());
        assertEquals(updateCommand.requestedOn(), subsectionMetadata.getLastChgTime());
    }

    private PageContentCommand.UpdateTab updateTab() {
        return new PageContentCommand.UpdateTab(
                "updated label",
                true,
                3l,
                444,
                Instant.now());
    }

    private PageContentCommand.UpdateSubsection updateSubsection() {
        return new PageContentCommand.UpdateSubsection(
                "updated label",
                false,
                3l,
                444,
                Instant.now());
    }

    private PageContentCommand.AddTab addTab(WaTemplate page) {
        return new PageContentCommand.AddTab(
                "test label",
                false,
                "some identifier",
                22,
                Instant.now());
    }

    private PageContentCommand.AddSubsection addSubsection(WaTemplate page) {
        return new PageContentCommand.AddSubsection(
                "test label",
                false,
                "some identifier",
                55,
                22,
                Instant.now());
    }
}
