package gov.cdc.nbs.questionbank.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entities.VersionId;
import gov.cdc.nbs.questionbank.entities.TextQuestionEntity;

public interface TextQuestionRepository extends JpaRepository<TextQuestionEntity, VersionId> {

}
