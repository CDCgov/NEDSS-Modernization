package gov.cdc.nbs.questionbank.question;


import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.VersionId;

public interface QuestionRepository extends JpaRepository<DisplayElementEntity, VersionId> {

	@Transactional
	@Modifying
	@Query("UPDATE DisplayElementEntity SET active=FALSE where id=:questionId")
	public int deleteQuestion(@Param("questionId") UUID questionId);

}
