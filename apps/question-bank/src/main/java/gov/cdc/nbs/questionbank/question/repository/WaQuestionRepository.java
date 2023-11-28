package gov.cdc.nbs.questionbank.question.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

  @Query("Select q from WaQuestion q WHERE (q.questionNm LIKE %:search% OR q.questionIdentifier LIKE %:search% OR q.questionLabel LIKE %:search% OR q.subGroupNm LIKE %:search% OR q.id =:searchId)")
  public Page<WaQuestion> findAllByNameOrIdentifierOrQuestionTypeOrSubGroup(
      @Param("search") String search,
      @Param("searchId") Long id,
      Pageable pageable);

  @Modifying
  @Query("Update WaQuestion SET data_type =:type WHERE id=:id")
  public void setDataType(@Param("type") String type, @Param("id") Long id);
}
