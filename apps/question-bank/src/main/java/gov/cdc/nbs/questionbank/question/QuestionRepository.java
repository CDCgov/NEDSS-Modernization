package gov.cdc.nbs.questionbank.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntityId;

public interface QuestionRepository extends JpaRepository<DisplayElementEntity, DisplayElementEntityId> {

	@Query("UPDATE DisplayElementEntity SET questionText=:newQuestionText WHERE id=:questionId")
	static int updateQuestion(@Param("questionId") Long questionId, String updatedQuestion) {
		return 0;
	}

}