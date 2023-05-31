package gov.cdc.nbs.questionbank.question;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementId;

public interface QuestionRepository extends JpaRepository<DisplayElementEntity, DisplayElementId> {

	@Modifying
	@Query("UPDATE DisplayElementEntity SET active=:questionActive where id=:questionId")
	public int deleteQuestion(@Param("questionId") Long questionId,
			@Param("questionActive") boolean questionActive);

}
