package gov.cdc.nbs.questionbank.question.repository;

import gov.cdc.nbs.questionbank.entity.question.WaQuestionHist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaQuestionHistRepository extends JpaRepository<WaQuestionHist, Long> {}
