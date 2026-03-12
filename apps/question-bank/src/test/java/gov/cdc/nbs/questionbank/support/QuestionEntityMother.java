package gov.cdc.nbs.questionbank.support;

import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import org.mockito.Mockito;

public class QuestionEntityMother {

  private QuestionEntityMother() {}

  public static TextQuestionEntity textQuestion() {
    var command = QuestionCommandMother.addTextQuestion();
    TextQuestionEntity spy = Mockito.spy(new TextQuestionEntity(command));
    Mockito.lenient().when(spy.getId()).thenReturn(1000l);
    return spy;
  }

  public static DateQuestionEntity dateQuestion() {
    var command = QuestionCommandMother.addDateQuestion();
    DateQuestionEntity spy = Mockito.spy(new DateQuestionEntity(command));
    Mockito.lenient().when(spy.getId()).thenReturn(1001l);
    return spy;
  }

  public static NumericQuestionEntity numericQuestion() {
    var command = QuestionCommandMother.addNumericQuestion();
    NumericQuestionEntity spy = Mockito.spy(new NumericQuestionEntity(command));
    Mockito.lenient().when(spy.getId()).thenReturn(1002l);
    return spy;
  }

  public static CodedQuestionEntity codedQuestion() {
    var command = QuestionCommandMother.addCodedQuestion();
    CodedQuestionEntity spy = Mockito.spy(new CodedQuestionEntity(command));
    Mockito.lenient().when(spy.getId()).thenReturn(1003l);
    return spy;
  }
}
