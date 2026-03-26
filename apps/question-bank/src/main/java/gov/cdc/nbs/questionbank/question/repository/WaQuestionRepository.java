package gov.cdc.nbs.questionbank.question.repository;

import gov.cdc.nbs.questionbank.entity.question.WaQuestion;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WaQuestionRepository extends JpaRepository<WaQuestion, Long> {

  @Query(
      "Select q from WaQuestion q WHERE q.questionNm=:questionNm OR q.questionIdentifier=:questionIdentifier OR q.userDefinedColumnNm=:userDefinedColumnNm OR q.rdbColumnNm=:rdbColumnNm")
  public List<WaQuestion> findAllByUniqueFields(
      @Param("questionNm") String questionNm,
      @Param("questionIdentifier") String questionIdentifier,
      @Param("userDefinedColumnNm") String userDefinedColumnNm,
      @Param("rdbColumnNm") String rdbColumnNm);

  @Query(
      "Select q from WaQuestion q WHERE (q.questionNm LIKE %:search% OR q.questionIdentifier LIKE %:search% OR q.questionLabel LIKE %:search% OR q.subGroupNm LIKE %:search% OR q.id =:searchId) AND q.questionType LIKE %:questionType%")
  public Page<WaQuestion> findAllByNameOrIdentifierOrQuestionTypeOrSubGroup(
      @Param("search") String search,
      @Param("searchId") Long id,
      @Param("questionType") String questionType,
      Pageable pageable);

  @Modifying
  @Query("Update WaQuestion SET dataType =:type WHERE id=:id")
  public void setDataType(@Param("type") String type, @Param("id") Long id);

  @Query(
      "SELECT q.questionIdentifier ,q.questionLabel FROM WaQuestion q WHERE q.questionIdentifier IN :identifiers")
  public List<Object[]> findLabelsByIdentifiers(@Param("identifiers") List<String> identifiers);

  List<Object[]> findIdByQuestionIdentifier(@Param("questionIdentifier") String questionIdentifier);

  List<Object[]> findIdByQuestionNm(@Param("questionNm") String questionNm);

  List<Object[]> findIdByUserDefinedColumnNm(@Param("userDefinedColumnNm") String dataMart);

  List<Object[]> findIdByRdbColumnNm(@Param("rdbColumnNm") String rdbColumnNm);
}
