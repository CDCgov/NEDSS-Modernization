package gov.cdc.nbs.questionbank.support;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.question.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.TextQuestionEntity;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionHistRepository;
import gov.cdc.nbs.questionbank.question.repository.WaQuestionRepository;

@Component
public class QuestionMother {

    @Autowired
    private WaQuestionRepository questionRepository;

    @Autowired
    private WaQuestionHistRepository histRepository;

    private List<WaQuestion> allQuestions = new ArrayList<>();

    public void clean() {
        histRepository.deleteAll();
        questionRepository.deleteAll();
        allQuestions.clear();
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

}

