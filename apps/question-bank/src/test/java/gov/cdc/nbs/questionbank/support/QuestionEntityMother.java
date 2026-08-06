package gov.cdc.nbs.questionbank.support;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.spy;

import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;

public class QuestionEntityMother {

  private QuestionEntityMother() {}

  public static TextQuestionEntity textQuestion() {
    var command = QuestionCommandMother.addTextQuestion();
    TextQuestionEntity spy = spy(new TextQuestionEntity(command));
    lenient().when(spy.getId()).thenReturn(1000l);
    return spy;
  }

  public static DateQuestionEntity dateQuestion() {
    var command = QuestionCommandMother.addDateQuestion();
    DateQuestionEntity spy = spy(new DateQuestionEntity(command));
    lenient().when(spy.getId()).thenReturn(1001l);
    return spy;
  }

  public static NumericQuestionEntity numericQuestion() {
    var command = QuestionCommandMother.addNumericQuestion();
    NumericQuestionEntity spy = spy(new NumericQuestionEntity(command));
    lenient().when(spy.getId()).thenReturn(1002l);
    return spy;
  }

  public static CodedQuestionEntity codedQuestion() {
    var command = QuestionCommandMother.addCodedQuestion();
    CodedQuestionEntity spy = spy(new CodedQuestionEntity(command));
    lenient().when(spy.getId()).thenReturn(1003l);
    return spy;
  }
}
