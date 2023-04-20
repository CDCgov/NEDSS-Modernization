package gov.cdc.nbs.questionbank.questionnaire;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entities.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    public Optional<Questionnaire> findByConditionIdAndQuestionnaireType(Long conditionId, String questionnaireType);
}
