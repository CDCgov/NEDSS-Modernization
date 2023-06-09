package gov.cdc.nbs.questionbank.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;

public interface WaQuestionRepository extends JpaRepository<WaQuestion, Long> {

}
