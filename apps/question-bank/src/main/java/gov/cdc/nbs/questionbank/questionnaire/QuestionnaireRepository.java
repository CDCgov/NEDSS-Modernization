package gov.cdc.nbs.questionbank.questionnaire;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.questionbank.entities.QuestionnaireEntity;

public interface QuestionnaireRepository extends JpaRepository<QuestionnaireEntity, Long> {

    public Optional<QuestionnaireEntity> findOneByConditionCodes(@Param("conditionCd") String conditionCd);
}
