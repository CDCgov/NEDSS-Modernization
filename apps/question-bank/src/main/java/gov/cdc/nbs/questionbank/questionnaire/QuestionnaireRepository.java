package gov.cdc.nbs.questionbank.questionnaire;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.questionbank.entities.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Query("SELECT q FROM Questionnaire q INNER JOIN q.conditions c WHERE c.id =:conditionId AND q.questionnaireType =:questionnaireType")
    public Optional<Questionnaire> findByConditionAndType(
            @Param("conditionId") long conditionId,
            @Param("questionnaireType") String questionnaireType);
}
