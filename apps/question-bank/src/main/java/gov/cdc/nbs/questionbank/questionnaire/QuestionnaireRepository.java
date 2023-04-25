package gov.cdc.nbs.questionbank.questionnaire;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

    @Query("SELECT q FROM Questionnaire q JOIN q.conditionCodes c WHERE c =:conditionCd AND q.questionnaireType =:questionnaireType")
    public Optional<Questionnaire> findByConditionAndType(
            @Param("conditionCd") String conditioncd,
            @Param("questionnaireType") String questionnaireType);
}
