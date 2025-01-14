package gov.cdc.nbs.questionbank.entity;

import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.QuestionUpdate;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateCodedQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateCodedQuestionValueset;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateDateQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateNumericQuestion;
import gov.cdc.nbs.questionbank.page.command.PageContentCommand.UpdateTextQuestion;
import gov.cdc.nbs.questionbank.page.content.PageContentModificationException;
import gov.cdc.nbs.questionbank.page.content.subsection.request.GroupSubSectionRequest;
import gov.cdc.nbs.questionbank.page.exception.AddQuestionException;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.Update;
import gov.cdc.nbs.questionbank.question.request.QuestionRequest.ReportingInfo;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        // NOOP
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
      int orderNumber) {
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
    assertEquals(question.getCoinfectionIndCd(), metadata.getCoinfectionIndCd());
  }

  private WaTemplate page() {
    WaTemplate page = new WaTemplate();
    page.setId(2L);
    return page;

  }


  @Test
  void should_create_tab() {
    WaTemplate page = new WaTemplate();
    page.setId(123L);
    PageContentCommand.AddTab command = addTab();
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
    assertNull(tabMetadata.getPublishIndCd());
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
    page.setId(123L);
    PageContentCommand.AddTab command = addTab();
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
    page.setId(123L);
    PageContentCommand.AddSubsection command = addSubsection();
    WaUiMetadata subsectionMetadata = new WaUiMetadata(page, command, 3);

    PageContentCommand.UpdateSubsection updateCommand = updateSubsection();
    subsectionMetadata.update(updateCommand);

    assertEquals(updateCommand.label(), subsectionMetadata.getQuestionLabel());
    assertEquals(updateCommand.visible() ? "T" : "F", subsectionMetadata.getDisplayInd());
    assertEquals(updateCommand.userId(), subsectionMetadata.getLastChgUserId().longValue());
    assertEquals(updateCommand.requestedOn(), subsectionMetadata.getLastChgTime());
  }

  @Test
  void should_update_text_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    TextQuestionEntity question = QuestionEntityMother.textQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata textQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateTextQuestion command = updateTextQuestion(now);

    // When the question is updated
    textQuestion.update(command);

    // Then the appropriate fields are updated
    assertThat(textQuestion.getDefaultValue()).isEqualTo(command.defaultValue());
    assertThat(textQuestion.getFieldSize()).isEqualTo(command.fieldLength().toString());

    assertThat(textQuestion.getQuestionLabel()).isEqualTo(command.label());
    assertThat(textQuestion.getQuestionToolTip()).isEqualTo(command.tooltip());
    assertThat(textQuestion.getDisplayInd()).isEqualTo(command.visible() ? "T" : "F");
    assertThat(textQuestion.getEnableInd()).isEqualTo(command.enabled() ? "T" : "F");
    assertThat(textQuestion.getRequiredInd()).isEqualTo(command.required() ? "T" : "F");
    assertThat(textQuestion.getNbsUiComponentUid()).isEqualTo(command.displayControl());
    validateReporting(textQuestion, command);
    validateMessaging(textQuestion, command);
  }

  @Test
  void should_update_published_text_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    TextQuestionEntity question = QuestionEntityMother.textQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata textQuestion = new WaUiMetadata(page, create, 5);
    textQuestion.setPublishIndCd('T');

    // And a valid update command
    UpdateTextQuestion command = updateTextQuestion(now);

    // When the question is updated
    textQuestion.update(command);

    // Then the appropriate fields are updated
    assertThat(textQuestion.getDefaultValue()).isEqualTo(command.defaultValue());
    // and field size was not affected
    assertThat(textQuestion.getFieldSize()).isEqualTo(question.getFieldSize());
  }

  @Test
  void should_not_update_non_text_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    NumericQuestionEntity question = QuestionEntityMother.numericQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata textQuestion = new WaUiMetadata(page, create, 5);
    textQuestion.setPublishIndCd('T');

    // And a valid update command
    UpdateTextQuestion command = updateTextQuestion(now);

    // When the question is updated and exception is thrown
    assertThrows(PageContentModificationException.class, () -> textQuestion.update(command));
  }

  @Test
  void should_update_numeric_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    NumericQuestionEntity question = QuestionEntityMother.numericQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata numericQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateNumericQuestion command = updateNumericQuestion(now);

    // When the question is updated
    numericQuestion.update(command);

    // Then the appropriate fields are updated
    assertThat(numericQuestion.getMask()).isEqualTo(command.mask());
    assertThat(numericQuestion.getFieldSize()).isEqualTo(command.fieldLength().toString());
    assertThat(numericQuestion.getDefaultValue()).isEqualTo(command.defaultValue().toString());
    assertThat(numericQuestion.getMinValue()).isEqualTo(command.minValue());
    assertThat(numericQuestion.getMaxValue()).isEqualTo(command.maxValue());
    assertThat(numericQuestion.getUnitTypeCd()).isEqualTo("LITERAL");
    assertThat(numericQuestion.getUnitValue()).isEqualTo(command.relatedUnitsLiteral());
    assertThat(numericQuestion.getWaNndMetadatum()).isNull();
  }

  @Test
  void should_update_numeric_question_related() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    NumericQuestionEntity question = QuestionEntityMother.numericQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata numericQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateNumericQuestion command = new PageContentCommand.UpdateNumericQuestion(
        36L,
        "new label",
        "new tooltip",
        false,
        false,
        false,
        1007,
        "NUM",
        3,
        0L,
        0L,
        999L,
        null,
        123L,
        new ReportingInfo("report Label", "DFT_RDB_TABLE", "RDB_COL", "DMART_COLUMN"),
        true,
        "messageVariable",
        "messageLabel",
        "codeSystemOid",
        "codeSystemName",
        true,
        "hl7DataType",
        "admin comments",
        1L,
        now);

    // When the question is updated
    numericQuestion.update(command);

    // Then the appropriate fields are updated
    assertThat(numericQuestion.getUnitTypeCd()).isEqualTo("CODED");
    assertThat(numericQuestion.getUnitValue()).isEqualTo(command.relatedUnitsValueSet().toString());
  }

  @Test
  void should_not_update_non_numeric_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    TextQuestionEntity question = QuestionEntityMother.textQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata numericQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateNumericQuestion command = updateNumericQuestion(now);

    // When the question is updated an exception is thrown
    assertThrows(PageContentModificationException.class, () -> numericQuestion.update(command));
  }

  @Test
  void should_update_date_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    DateQuestionEntity question = QuestionEntityMother.dateQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata dateQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateDateQuestion command = updateDateQuestion(now);

    // When the question is updated
    dateQuestion.update(command);

    // Then the appropriate fields are updated
    assertThat(dateQuestion.getMask()).isEqualTo(command.mask());
    assertThat(dateQuestion.getFutureDateIndCd()).isEqualTo(command.allowFutureDates() ? 'T' : 'F');
  }

  @Test
  void should_not_update_non_date_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    TextQuestionEntity question = QuestionEntityMother.textQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata dateQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateDateQuestion command = updateDateQuestion(now);


    // When the question is updated an exception is thrown
    assertThrows(PageContentModificationException.class, () -> dateQuestion.update(command));
  }

  @Test
  void should_update_coded_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    CodedQuestionEntity question = QuestionEntityMother.codedQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata dateQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateCodedQuestion command = updateCodedQuestion(now);

    // When the question is updated
    dateQuestion.update(command);

    // Then the appropriate fields are updated
    assertThat(dateQuestion.getDefaultValue()).isEqualTo(command.defaultValue());
    assertThat(dateQuestion.getCodeSetGroupId()).isEqualTo(command.valueset());
    assertThat(dateQuestion.getWaRdbMetadatum()).isNull();
    assertThat(dateQuestion.getWaNndMetadatum()).isNull();
  }

  @Test
  void should_update_coded_question_published() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    CodedQuestionEntity question = QuestionEntityMother.codedQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata dateQuestion = new WaUiMetadata(page, create, 5);
    dateQuestion.setPublishIndCd('T');

    // And a valid update command
    UpdateCodedQuestion command = updateCodedQuestion(now);

    // When the question is updated
    dateQuestion.update(command);

    // Then the appropriate fields are updated
    assertThat(dateQuestion.getDefaultValue()).isEqualTo(command.defaultValue());
    // value set is not updated and matches original
    assertThat(dateQuestion.getCodeSetGroupId()).isEqualTo(question.getCodeSetGroupId());
  }

  @Test
  void should_not_update_non_coded_question() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    TextQuestionEntity question = QuestionEntityMother.textQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata dateQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateCodedQuestion command = updateCodedQuestion(now);

    // When the question is updated an exception is thrown
    assertThrows(PageContentModificationException.class, () -> dateQuestion.update(command));
  }

  @Test
  void should_fail_update_coded_valueset_not_coded() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    DateQuestionEntity question = QuestionEntityMother.dateQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata dateQuestion = new WaUiMetadata(page, create, 5);

    // And a valid update command
    UpdateCodedQuestionValueset command = new UpdateCodedQuestionValueset(1L, 2L, 3L, Instant.now());

    // When the question is updated, an exception is thrown
    assertThrows(PageContentModificationException.class, () -> dateQuestion.update(command));
  }

  @Test
  void should_fail_update_coded_valueset_published() {
    Instant now = Instant.now();
    // Given a text question
    WaTemplate page = page();
    CodedQuestionEntity question = QuestionEntityMother.codedQuestion();
    PageContentCommand.AddQuestion create = new PageContentCommand.AddQuestion(
        page.getId(),
        question,
        12,
        1L,
        now);
    WaUiMetadata dateQuestion = new WaUiMetadata(page, create, 5);
    dateQuestion.setPublishIndCd('T');
    // And a valid update command
    UpdateCodedQuestionValueset command = new UpdateCodedQuestionValueset(1L, 2L, 3L, Instant.now());

    // When the question is updated, an exception is thrown
    assertThrows(PageContentModificationException.class, () -> dateQuestion.update(command));
  }

  @Test
  void should_group() {
    WaUiMetadata metadata = new WaUiMetadata();
    metadata.setId(99L);
    PageContentCommand.GroupSubsection groupCommand = new PageContentCommand.GroupSubsection(
        0,
        "BLOCK_NAME",
        List.of(new GroupSubSectionRequest.Batch(99L, true, "question label", 100)),
        2,
        3L,
        Instant.now());
    metadata.updateQuestionBatch(groupCommand, 1);

    assertThat(metadata.getBlockNm()).isEqualTo("BLOCK_NAME");
    assertThat(metadata.getBatchTableAppearIndCd()).isEqualTo('Y');
    assertThat(metadata.getQuestionGroupSeqNbr()).isEqualTo(1);
    assertThat(metadata.getBatchTableHeader()).isEqualTo("question label");
    assertThat(metadata.getBatchTableColumnWidth()).isEqualTo(100);
  }

  @Test
  void should_clear_group() {
    WaUiMetadata metadata = new WaUiMetadata();
    metadata.setId(99L);
    PageContentCommand.GroupSubsection groupCommand = new PageContentCommand.GroupSubsection(
        0,
        "BLOCK_NAME",
        List.of(new GroupSubSectionRequest.Batch(99L, false, "question label", 100)),
        2,
        3L,
        Instant.now());
    metadata.updateQuestionBatch(groupCommand, 1);

    assertThat(metadata.getBlockNm()).isEqualTo("BLOCK_NAME");
    assertThat(metadata.getBatchTableAppearIndCd()).isEqualTo('N');
    assertThat(metadata.getQuestionGroupSeqNbr()).isEqualTo(1);
    assertThat(metadata.getBatchTableHeader()).isNull();
    assertThat(metadata.getBatchTableColumnWidth()).isNull();
  }

  @Test
  void should_clone_nnd() {
    WaTemplate newPage = new WaTemplate();
    WaUiMetadata metadata = new WaUiMetadata();
    WaNndMetadatum nnd = new WaNndMetadatum();
    metadata.setWaNndMetadatum(nnd);

    WaUiMetadata cloned = WaUiMetadata.clone(metadata, newPage);

    assertThat(cloned.getWaTemplateUid()).isEqualTo(newPage);
    assertThat(cloned.getWaNndMetadatum().getWaUiMetadataUid()).isEqualTo(cloned);
  }

  @Test
  void should_clone_rdb() {
    WaTemplate newPage = new WaTemplate();
    WaUiMetadata metadata = new WaUiMetadata();
    WaRdbMetadata nnd = new WaRdbMetadata();
    metadata.setWaRdbMetadatum(nnd);

    WaUiMetadata cloned = WaUiMetadata.clone(metadata, newPage);

    assertThat(cloned.getWaTemplateUid()).isEqualTo(newPage);
  }

  private void validateMessaging(WaUiMetadata question, QuestionUpdate command) {
    assertThat(question.getWaNndMetadatum().getQuestionIdentifierNnd()).isEqualTo(command.messageVariableId());
    assertThat(question.getWaNndMetadatum().getQuestionRequiredNnd())
        .isEqualTo(command.requiredInMessage() ? 'R' : 'O');
    assertThat(question.getWaNndMetadatum().getQuestionLabelNnd()).isEqualTo(command.labelInMessage());
    assertThat(question.getWaNndMetadatum().getQuestionDataTypeNnd()).isEqualTo(command.hl7DataType());
    assertThat(question.getWaNndMetadatum().getLastChgUserId()).isEqualTo(command.userId());
    assertThat(question.getWaNndMetadatum().getLastChgTime()).isEqualTo(command.requestedOn());
    assertThat(question.getQuestionOidSystemTxt()).isEqualTo(command.codeSystemName());
    assertThat(question.getQuestionOid()).isEqualTo(command.codeSystemOid());
  }

  private void validateReporting(
      WaUiMetadata question,
      QuestionUpdate command) {
    assertThat(question.getWaRdbMetadatum().getRptAdminColumnNm()).isEqualTo(command.datamartInfo().reportLabel());
    assertThat(question.getWaRdbMetadatum().getUserDefinedColumnNm())
        .isEqualTo(command.datamartInfo().dataMartColumnName());
    assertThat(question.getWaRdbMetadatum().getLastChgUserId()).isEqualTo(command.userId());
    assertThat(question.getWaRdbMetadatum().getLastChgTime()).isEqualTo(command.requestedOn());
  }


  private PageContentCommand.UpdateTextQuestion updateTextQuestion(Instant requestedOn) {
    return new PageContentCommand.UpdateTextQuestion(
        36L,
        "new label",
        "new tooltip",
        false,
        false,
        false,
        1007,
        "default",
        30,
        new ReportingInfo("report Label", "DFT_RDB_TABLE", "RDB_COL", "DMART_COLUMN"),
        true,
        "messageVariable",
        "messageLabel",
        "codeSystemOid",
        "codeSystemName",
        true,
        "hl7DataType",
        "admin comments",
        1L,
        requestedOn);
  }

  private PageContentCommand.UpdateNumericQuestion updateNumericQuestion(Instant requestedOn) {
    return new PageContentCommand.UpdateNumericQuestion(
        36L,
        "new label",
        "new tooltip",
        true,
        true,
        true,
        1007,
        "NUM",
        3,
        0L,
        0L,
        999L,
        "literal related",
        null,
        new ReportingInfo("report Label", "DFT_RDB_TABLE", "RDB_COL", "DMART_COLUMN"),
        false,
        "messageVariable",
        "messageLabel",
        "codeSystemOid",
        "codeSystemName",
        true,
        "hl7DataType",
        "admin comments",
        1L,
        requestedOn);
  }

  private PageContentCommand.UpdateDateQuestion updateDateQuestion(Instant requestedOn) {
    return new PageContentCommand.UpdateDateQuestion(
        36L,
        "new label",
        "new tooltip",
        false,
        false,
        false,
        1007,
        "DATE",
        false,
        new ReportingInfo("report Label", "DFT_RDB_TABLE", "RDB_COL", "DMART_COLUMN"),
        true,
        "messageVariable",
        "messageLabel",
        "codeSystemOid",
        "codeSystemName",
        true,
        "hl7DataType",
        "admin comments",
        1L,
        requestedOn);
  }

  private PageContentCommand.UpdateCodedQuestion updateCodedQuestion(Instant requestedOn) {
    return new PageContentCommand.UpdateCodedQuestion(
        36L,
        "new label",
        "new tooltip",
        false,
        false,
        false,
        1026L, // readonly user entered
        "DATE",
        999L,
        new ReportingInfo("report Label", "DFT_RDB_TABLE", "RDB_COL", "DMART_COLUMN"),
        true,
        "messageVariable",
        "messageLabel",
        "codeSystemOid",
        "codeSystemName",
        true,
        "hl7DataType",
        "admin comments",
        1L,
        requestedOn);
  }

  private PageContentCommand.UpdateTab updateTab() {
    return new PageContentCommand.UpdateTab(
        "updated label",
        true,
        3L,
        444,
        Instant.now());
  }

  private PageContentCommand.UpdateSubsection updateSubsection() {
    return new PageContentCommand.UpdateSubsection(
        "updated label",
        false,
        3L,
        444,
        Instant.now());
  }

  private PageContentCommand.AddTab addTab() {
    return new PageContentCommand.AddTab(
        "test label",
        false,
        "some identifier",
        22,
        Instant.now());
  }

  private PageContentCommand.AddSubsection addSubsection() {
    return new PageContentCommand.AddSubsection(
        "test label",
        false,
        "some identifier",
        55,
        22,
        Instant.now());
  }
}
