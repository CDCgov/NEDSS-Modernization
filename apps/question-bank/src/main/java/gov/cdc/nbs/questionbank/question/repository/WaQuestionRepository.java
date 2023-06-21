package gov.cdc.nbs.questionbank.question.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.questionbank.entity.question.WaQuestion;

public interface WaQuestionRepository extends JpaRepository<WaQuestion, Long> {

    @Query("Select q from WaQuestion q WHERE q.questionNm=:questionNm OR q.questionIdentifier=:questionIdentifier OR q.userDefinedColumnNm=:userDefinedColumnNm OR q.rdbColumnNm=:rdbColumnNm")
    public List<WaQuestion> findAllByUniqueFields(
            @Param("questionNm") String questionNm,
            @Param("questionIdentifier") String questionIdentifier,
            @Param("userDefinedColumnNm") String userDefinedColumnNm,
            @Param("rdbColumnNm") String rdbColumnNm);

    @Query("Select q from WaQuestion q WHERE q.questionNm LIKE :search OR q.questionIdentifier LIKE :search")
    public Page<WaQuestion> findAllByNameOrIdentifier(String search, Pageable pageable);

}
