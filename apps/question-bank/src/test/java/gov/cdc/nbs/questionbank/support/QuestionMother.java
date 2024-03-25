package gov.cdc.nbs.questionbank.support;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entity.question.CodedQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.NumericQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;
import gov.cdc.nbs.testing.support.Active;
import jakarta.persistence.EntityManager;

@Component
@Transactional
public class QuestionMother {
  private final WaQuestionRepository questionRepository;
  private final EntityManager entityManager;
  private final Active<QuestionIdentifier> active;
  private final TestQuestionCleaner cleaner;

  public QuestionMother(
      final WaQuestionRepository questionRepository,
      final EntityManager entityManager,
      final Active<QuestionIdentifier> active,
      final TestQuestionCleaner cleaner) {
    this.questionRepository = questionRepository;
    this.entityManager = entityManager;
    this.active = active;
    this.cleaner = cleaner;
  }

  private List<WaQuestion> allQuestions = new ArrayList<>();

  public void clean() {
    this.allQuestions.forEach(cleaner::clean);
    this.allQuestions.clear();
    this.active.reset();
  }

  public void addManaged(Long questionId) {
    WaQuestion question = entityManager.find(WaQuestion.class, questionId);
    this.active.active(new QuestionIdentifier(question.getId(), question.getQuestionLabel()));
    this.allQuestions.add(question);
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

  public WaQuestion findByUniqueId(String id) {
    return allQuestions.stream()
        .filter(q -> q.getQuestionIdentifier().equals(id))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("No questions are available"));
  }

  private WaQuestion createTextQuestion() {
    TextQuestionEntity q = new TextQuestionEntity(QuestionCommandMother.addTextQuestion());
    q = questionRepository.save(q);
    addManaged(q.getId());
    return q;
  }

  private WaQuestion createDateQuestion() {
    DateQuestionEntity q = new DateQuestionEntity(QuestionCommandMother.addDateQuestion());
    q = questionRepository.save(q);
    addManaged(q.getId());
    return q;
  }

  private WaQuestion createCodeQuestion() {
    CodedQuestionEntity q = new CodedQuestionEntity(QuestionCommandMother.addCodedQuestion());
    q = questionRepository.save(q);
    addManaged(q.getId());
    return q;
  }

  private WaQuestion createNumericQuestion() {
    NumericQuestionEntity q = new NumericQuestionEntity(QuestionCommandMother.addNumericQuestion());
    q = questionRepository.save(q);
    addManaged(q.getId());
    return q;
  }

  public List<WaQuestion> list(int count) {
    return allQuestions.stream().limit(count).toList();
  }

}

