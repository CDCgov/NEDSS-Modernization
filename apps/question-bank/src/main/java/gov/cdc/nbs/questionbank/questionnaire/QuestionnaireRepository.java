package gov.cdc.nbs.questionbank.questionnaire;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;

public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity, Long> {

    @Query("SELECT q FROM QuestionnaireEntity q WHERE :conditionCd IN q.conditionCodes AND q.questionnaireType =:questionnaireType")
    public Optional<QuestionnaireEntity> findByConditionAndType(
            @Param("conditionCd") String conditioncd,
            @Param("questionnaireType") String questionnaireType);
}
