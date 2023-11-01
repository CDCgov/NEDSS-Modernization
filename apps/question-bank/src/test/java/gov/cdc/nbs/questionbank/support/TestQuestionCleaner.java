package gov.cdc.nbs.questionbank.support;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;

@Component
public class TestQuestionCleaner {
    private final EntityManager entityManager;

    TestQuestionCleaner(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    void clean(final WaQuestion question) {
        WaQuestion found = this.entityManager.find(WaQuestion.class, question.getId());
        if (found != null) {
            this.entityManager.remove(found);
        }
    }
}
