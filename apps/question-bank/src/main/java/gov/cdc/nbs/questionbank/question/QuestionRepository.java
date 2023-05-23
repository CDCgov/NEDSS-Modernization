package gov.cdc.nbs.questionbank.question;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntityId;

public interface QuestionRepository extends JpaRepository<DisplayElementEntity, DisplayElementEntityId> {

	@Query("UPDATE DisplayElementEntity SET active=:questionActive where id=:questionId")
	public Optional<DisplayElementEntity> deleteQuestion(@Param("questionId") Long questionId,
			@Param("questionActive") boolean questionActive);

}
