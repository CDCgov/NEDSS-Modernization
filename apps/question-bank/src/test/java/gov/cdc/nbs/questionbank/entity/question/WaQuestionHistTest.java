package gov.cdc.nbs.questionbank.entity.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import gov.cdc.nbs.questionbank.question.command.QuestionCommand.Update;
import gov.cdc.nbs.questionbank.question.exception.UpdateQuestionException;
import gov.cdc.nbs.questionbank.support.QuestionEntityMother;
import org.junit.jupiter.api.Test;

class WaQuestionHistTest {

  @Test
  void should_set_numeric_fields() {
    NumericQuestionEntity actual = QuestionEntityMother.numericQuestion();
    WaQuestionHist hist = new WaQuestionHist(actual);

    assertEquals(actual.getMask(), hist.getMask());
    assertEquals(actual.getFieldSize(), hist.getFieldSize());
    assertEquals(actual.getDefaultValue(), hist.getDefaultValue());
    assertEquals(actual.getMinValue(), hist.getMinValue());
    assertEquals(actual.getMaxValue(), hist.getMaxValue());
    assertEquals(actual.getUnitTypeCd(), hist.getUnitTypeCd());
    assertEquals(actual.getUnitValue(), hist.getUnitValue());
  }

  @Test
  void should_set_date_fields() {
    DateQuestionEntity actual = QuestionEntityMother.dateQuestion();
    WaQuestionHist hist = new WaQuestionHist(actual);

    assertEquals(actual.getMask(), hist.getMask());
    assertEquals(actual.getFutureDateIndCd(), hist.getFutureDateIndCd());
  }

  @Test
  // Allow more than 25 assertions
  @SuppressWarnings("squid:S5961")
  void should_set_coded_fields() {
    CodedQuestionEntity question = QuestionEntityMother.codedQuestion();
    WaQuestionHist hist = new WaQuestionHist(question);

    assertEquals(question.getCodeSetGroupId(), hist.getCodeSetGroupId());
    assertEquals(question.getDefaultValue(), hist.getDefaultValue());
    assertEquals(question.getId(), hist.getWaQuestionUid().getId());
    assertEquals(question.getDataType(), hist.getDataType());
    assertEquals(question.getDataCd(), hist.getDataCd());
    assertEquals(question.getDataLocation(), hist.getDataLocation());
    assertEquals(question.getQuestionIdentifier(), hist.getQuestionIdentifier());
    assertEquals(question.getQuestionOid(), hist.getQuestionOid());
    assertEquals(question.getQuestionOidSystemTxt(), hist.getQuestionOidSystemTxt());
    assertEquals(question.getQuestionUnitIdentifier(), hist.getQuestionUnitIdentifier());
    assertEquals(question.getDataUseCd(), hist.getDataUseCd());
    assertEquals(question.getQuestionLabel(), hist.getQuestionLabel());
    assertEquals(question.getQuestionToolTip(), hist.getQuestionToolTip());
    assertEquals(question.getRdbColumnNm(), hist.getRdbColumnNm());
    assertEquals(question.getPartTypeCd(), hist.getPartTypeCd());
    assertEquals(question.getVersionCtrlNbr(), hist.getVersionCtrlNbr());
    assertEquals(question.getUnitParentIdentifier(), hist.getUnitParentIdentifier());
    assertEquals(question.getQuestionGroupSeqNbr(), hist.getQuestionGroupSeqNbr());
    assertEquals(question.getFutureDateIndCd(), hist.getFutureDateIndCd());
    assertEquals(question.getLegacyDataLocation(), hist.getLegacyDataLocation());
    assertEquals(question.getRepeatsIndCd(), hist.getRepeatsIndCd());
    assertEquals(question.getLocalId(), hist.getLocalId());
    assertEquals(question.getQuestionNm(), hist.getQuestionNm());
    assertEquals(question.getGroupNm(), hist.getGroupNm());
    assertEquals(question.getSubGroupNm(), hist.getSubGroupNm());
    assertEquals(question.getDescTxt(), hist.getDescTxt());
    assertEquals(question.getRptAdminColumnNm(), hist.getRptAdminColumnNm());
    assertEquals(question.getNndMsgInd(), hist.getNndMsgInd());
    assertEquals(question.getQuestionIdentifierNnd(), hist.getQuestionIdentifierNnd());
    assertEquals(question.getQuestionLabelNnd(), hist.getQuestionLabelNnd());
    assertEquals(question.getQuestionRequiredNnd(), hist.getQuestionRequiredNnd());
    assertEquals(question.getQuestionDataTypeNnd(), hist.getQuestionDataTypeNnd());
    assertEquals(question.getHl7SegmentField(), hist.getHl7SegmentField());
    assertEquals(question.getOrderGroupId(), hist.getOrderGroupId());
    assertEquals(question.getRecordStatusCd(), hist.getRecordStatusCd());
    assertEquals(question.getRecordStatusTime(), hist.getRecordStatusTime());
    assertEquals(question.getNbsUiComponentUid(), hist.getNbsUiComponentUid());
    assertEquals(question.getStandardQuestionIndCd(), hist.getStandardQuestionIndCd());
    assertEquals(question.getEntryMethod(), hist.getEntryMethod());
    assertEquals(question.getQuestionType(), hist.getQuestionType());
    assertEquals(question.getAdminComment(), hist.getAdminComment());
    assertEquals(question.getRdbTableNm(), hist.getRdbTableNm());
    assertEquals(question.getUserDefinedColumnNm(), hist.getUserDefinedColumnNm());
    assertEquals(question.getStandardNndIndCd(), hist.getStandardNndIndCd());
    assertEquals(question.getLegacyQuestionIdentifier(), hist.getLegacyQuestionIdentifier());
    assertEquals(question.getOtherValueIndCd(), hist.getOtherValueIndCd());
    assertEquals(question.getSourceNm(), hist.getSourceNm());
    assertEquals(question.getCoinfectionIndCd(), hist.getCoinfectionIndCd());
    assertEquals(question.getAddUserId(), hist.getAddUserId());
    assertEquals(question.getAddTime(), hist.getAddTime());
    assertEquals(question.getLastChgTime(), hist.getLastChgTime());
    assertEquals(question.getLastChgUserId(), hist.getLastChgUserId());
  }

  @Test
  void should_throw_exception_unrecognized_type() {
    WaQuestion question =
        new WaQuestion() {
          @Override
          public String getDataType() {
            return "Test";
          }

          @Override
          public void update(Update command) {
            // NOOP
          }
        };

    assertThrows(UpdateQuestionException.class, () -> new WaQuestionHist(question));
  }
}
