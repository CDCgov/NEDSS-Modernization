package gov.cdc.nbs.questionbank.question;

import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.command.QuestionCommand.Update;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.question.model.Question;
import gov.cdc.nbs.questionbank.question.model.Question.CodedQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.DateQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.NumericQuestion;
import gov.cdc.nbs.questionbank.question.model.Question.TextQuestion;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class QuestionMapperTest {

  private final QuestionMapper questionMapper = new QuestionMapper();

  @Test
  void should_map_text_question() {
    TextQuestionEntity entity = QuestionEntityMother.textQuestion();
    TextQuestion q = (TextQuestion) questionMapper.toQuestion(entity);

    // Text question specific fields
    assertEquals(entity.getMask(), q.mask());
    assertEquals(entity.getFieldSize(), q.fieldSize());
    assertEquals(entity.getDefaultValue(), q.defaultValue());

    validateQuestionFields(q, entity);
  }

  @Test
  void should_map_date_question() {
    DateQuestionEntity entity = QuestionEntityMother.dateQuestion();
    DateQuestion q = (DateQuestion) questionMapper.toQuestion(entity);

    // Date question specific fields
    assertEquals(entity.getMask(), q.mask());
    assertEquals(entity.getFutureDateIndCd().equals('T'), q.allowFutureDates());

    validateQuestionFields(q, entity);
  }

  @Test
  void should_map_numeric_question() {
    NumericQuestionEntity entity = QuestionEntityMother.numericQuestion();
    NumericQuestion q = (NumericQuestion) questionMapper.toQuestion(entity);

    // Numeric question specific fields
    assertEquals(entity.getMask(), q.mask());
    assertEquals(entity.getFieldSize(), q.fieldLength());
    assertEquals(entity.getDefaultValue(), q.defaultValue());
    assertEquals(entity.getMinValue(), q.minValue());
    assertEquals(entity.getMaxValue(), q.maxValue());
    assertEquals(entity.getUnitTypeCd(), q.unitTypeCd());
    assertEquals(entity.getUnitValue(), q.unitValue());

    validateQuestionFields(q, entity);
  }


  @Test
  void should_map_coded_question() {
    CodedQuestionEntity entity = QuestionEntityMother.codedQuestion();
    CodedQuestion q = (CodedQuestion) questionMapper.toQuestion(entity);

    // Numeric question specific fields
    assertEquals(entity.getCodeSetGroupId().longValue(), q.valueSet());
    assertEquals(entity.getDefaultValue(), q.defaultValue());

    validateQuestionFields(q, entity);
  }

  @Test
  void should_throw_if_unable_to_map() {
    WaQuestion entity = new WaQuestion() {

      @Override
      public String getDataType() {
        return "Test";
      }

      @Override
      public void update(Update command) {// NOOP
        //
      }

    };

    assertThrows(UpdateQuestionException.class, () -> questionMapper.toQuestion(entity));
  }



  private void validateQuestionFields(Question q, WaQuestion entity) {
    assertEquals(entity.getId().longValue(), q.id());
    assertEquals(entity.getQuestionType(), q.codeSet());
    assertEquals(entity.getQuestionIdentifier(), q.uniqueId());
    assertEquals(entity.getQuestionNm(), q.uniqueName());
    assertEquals(entity.getRecordStatusCd(), q.status());
    assertEquals(entity.getSubGroupNm(), q.subgroup());
    assertEquals(entity.getDescTxt(), q.description());
    assertEquals(entity.getDataType(), q.type());
    assertEquals(entity.getQuestionLabel(), q.label());
    assertEquals(entity.getQuestionToolTip(), q.tooltip());
    assertEquals(entity.getNbsUiComponentUid(), q.displayControl());
    assertEquals(entity.getAdminComment(), q.adminComments());

    // data mart
    assertEquals(entity.getRptAdminColumnNm(), q.dataMartInfo().defaultLabelInReport());
    assertEquals(entity.getRdbTableNm(), q.dataMartInfo().defaultRdbTableName());
    assertEquals(entity.getRdbColumnNm(), q.dataMartInfo().rdbColumnName());
    assertEquals(entity.getUserDefinedColumnNm(), q.dataMartInfo().dataMartColumnName());

    // messaging
    assertEquals(entity.getNndMsgInd().equals('T'), q.messagingInfo().includedInMessage());
    assertEquals(entity.getQuestionIdentifierNnd(), q.messagingInfo().messageVariableId());
    assertEquals(entity.getQuestionLabelNnd(), q.messagingInfo().labelInMessage());
    assertEquals(entity.getQuestionOid(), q.messagingInfo().codeSystem());
    assertEquals(entity.getQuestionRequiredNnd().equals('R'), q.messagingInfo().requiredInMessage());
    assertEquals(entity.getQuestionDataTypeNnd(), q.messagingInfo().hl7DataType());
  }


}
