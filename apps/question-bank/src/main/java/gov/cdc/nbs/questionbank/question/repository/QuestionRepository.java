package gov.cdc.nbs.questionbank.question.repository;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import gov.cdc.nbs.questionbank.entities.QuestionEntity;
import gov.cdc.nbs.questionbank.entities.VersionId;

public interface QuestionRepository extends JpaRepository<QuestionEntity, VersionId> {
    public Page<QuestionEntity> findAllByLabelContaining(String label, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE DisplayElementEntity SET active=FALSE where id=:questionId")
    public int deleteQuestion(@Param("questionId") UUID questionId);

}
