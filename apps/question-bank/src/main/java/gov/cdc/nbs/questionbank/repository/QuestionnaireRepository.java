package gov.cdc.nbs.questionbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entities.Questionnaire;

public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

}
