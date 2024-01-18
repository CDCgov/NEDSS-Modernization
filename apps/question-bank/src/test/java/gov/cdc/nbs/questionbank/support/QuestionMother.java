package gov.cdc.nbs.questionbank.support;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

import gov.cdc.nbs.questionbank.entity.question.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@Component
@Transactional
public class QuestionMother {

  @Autowired
  private WaQuestionRepository questionRepository;

  @Autowired
  EntityManager entityManager;

  @Autowired
  private TestQuestionCleaner cleaner;

  private List<WaQuestion> allQuestions = new ArrayList<>();

  public void clean() {
    this.allQuestions.forEach(cleaner::clean);
    this.allQuestions.clear();
  }

  public void addManaged(Long questionId) {
    this.allQuestions.add(entityManager.find(WaQuestion.class, questionId));
  }

  public WaQuestion textQuestion() {
    return allQuestions.stream()
        .filter(q -> q instanceof TextQuestionEntity).findFirst()
        .orElseGet(this::createTextQuestion);
  }

  public WaQuestion dateQuestion() {
    return allQuestions.stream()
        .filter(q -> q instanceof DateQuestionEntity).findFirst()
        .orElseGet(this::createDateQuestion);
  }

  public WaQuestion codeQuestion() {
    return allQuestions.stream()
        .filter(q -> q instanceof CodedQuestionEntity).findFirst()
        .orElseGet(this::createCodeQuestion);
  }

  public WaQuestion numericQuestion() {
    return allQuestions.stream()
        .filter(q -> q instanceof NumericQuestionEntity).findFirst()
        .orElseGet(this::createNumericQuestion);
  }

  public WaQuestion one() {
    return allQuestions.stream().findFirst()
        .orElseThrow(() -> new IllegalStateException("No questions are available"));
  }

  private WaQuestion createTextQuestion() {
    WaQuestion q = QuestionEntityMother.textQuestion();
    q = questionRepository.save(q);
    allQuestions.add(q);
    return q;
  }

  private WaQuestion createDateQuestion() {
    WaQuestion q = QuestionEntityMother.dateQuestion();
    q = questionRepository.save(q);
    allQuestions.add(q);
    return q;
  }

  private WaQuestion createCodeQuestion() {
    WaQuestion q = QuestionEntityMother.codedQuestion();
    q = questionRepository.save(q);
    allQuestions.add(q);
    return q;
  }

  private WaQuestion createNumericQuestion() {
    WaQuestion q = QuestionEntityMother.numericQuestion();
    q = questionRepository.save(q);
    allQuestions.add(q);
    return q;
  }

  public List<WaQuestion> list(int count) {
    return allQuestions.stream().limit(count).toList();
  }

}

