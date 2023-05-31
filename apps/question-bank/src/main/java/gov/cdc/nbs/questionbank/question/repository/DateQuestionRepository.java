package gov.cdc.nbs.questionbank.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entities.DateQuestionEntity;
import gov.cdc.nbs.questionbank.entities.VersionId;

public interface DateQuestionRepository extends JpaRepository<DateQuestionEntity, VersionId> {

}
