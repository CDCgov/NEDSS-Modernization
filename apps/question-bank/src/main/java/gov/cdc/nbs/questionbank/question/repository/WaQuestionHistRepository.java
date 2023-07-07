package gov.cdc.nbs.questionbank.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.question.WaQuestionHist;

public interface WaQuestionHistRepository extends JpaRepository<WaQuestionHist, Long> {

}
