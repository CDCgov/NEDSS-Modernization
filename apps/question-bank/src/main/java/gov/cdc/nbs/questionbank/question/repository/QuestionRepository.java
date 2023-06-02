package gov.cdc.nbs.questionbank.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entities.QuestionEntity;
import gov.cdc.nbs.questionbank.entities.VersionId;

public interface QuestionRepository extends JpaRepository<QuestionEntity, VersionId> {
    public Page<QuestionEntity> findAllByLabelContaining(String label, Pageable pageable);

}
