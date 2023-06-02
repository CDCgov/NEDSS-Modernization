package gov.cdc.nbs.questionbank.question;

import java.util.Collection;
import javax.persistence.EntityManager;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.questionnaire.model.Questionnaire;

@Component
public class QuestionFinder {

    private final EntityManager entityManager;

    public QuestionFinder(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Collection<Questionnaire.Question> find(QuestionFilter filter, Pageable page) {
        return null;
    }

}
