package gov.cdc.nbs.questionbank.entity.repository;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WaUiMetadataRepository extends JpaRepository<WaUiMetadata, Long> {

  List<WaUiMetadata> findAllByQuestionIdentifier(String questionIdentifier);

  List<WaUiMetadata> findAllByWaTemplateUid(WaTemplate templateId);

  @Query(
      "SELECT ui FROM WaUiMetadata ui WHERE ui.waTemplateUid.id =:pageId AND ui.nbsUiComponentUid =:nbsUiComponentUid order by ui.orderNbr asc")
  List<WaUiMetadata> findOrderedTabsForPage(
      @Param("pageId") Long pageId, @Param("nbsUiComponentUid") Long nbsUiComponentUid);

  @Query(
      "SELECT ui FROM WaUiMetadata ui WHERE ui.waTemplateUid.id =:pageId AND ui.nbsUiComponentUid =:nbsUiComponentUid AND ( ui.orderNbr > :orderNmbrMin AND ui.orderNbr <= :orderNmbrMax) order by ui.orderNbr asc")
  List<WaUiMetadata> findOrderedChildComponents(
      @Param("pageId") Long pageId,
      @Param("nbsUiComponentUid") Long nbsUiComponentUid,
      @Param("orderNmbrMin") Integer orderNmbrMin,
      @Param("orderNmbrMax") Integer orderNmbrMax);

  @Modifying
  @Query(
      "UPDATE WaUiMetadata ui SET ui.orderNbr = ui.orderNbr + 1 WHERE ui.waTemplateUid.id = :pageId AND ui.orderNbr >= :start")
  void incrementOrderNbrGreaterThanOrEqualTo(
      @Param("pageId") Long pageId, @Param("start") Integer start);

  @Modifying
  @Query(
      "Update WaUiMetadata ui SET ui.orderNbr = ui.orderNbr - 1 WHERE ui.waTemplateUid.id = :pageId AND ui.orderNbr > :orderNumber")
  void decrementOrderNbrGreaterThan(
      @Param("pageId") Long pageId, @Param("orderNumber") Integer orderNumber);

  @Query(
      "SELECT COUNT(ui) FROM WaUiMetadata ui WHERE ui.waTemplateUid.id =:pageId AND ui.questionIdentifier =:questionIdentifier")
  Long countByPageAndQuestionIdentifier(
      @Param("pageId") Long pageId, @Param("questionIdentifier") String questionIdentifier);

  @Query(
      "Select COALESCE(Max(ui.orderNbr),0) FROM WaUiMetadata ui WHERE ui.waTemplateUid.id =:pageId")
  Integer findMaxOrderNbrForPage(@Param("pageId") Long pageId);

  @Query(
      "SELECT COALESCE(min(ui.orderNbr), (SELECT max(ui.orderNbr) + 1 FROM WaUiMetadata ui WHERE ui.waTemplateUid.id=:pageId)) FROM WaUiMetadata ui WHERE ui.waTemplateUid.id = :pageId AND ui.orderNbr > :orderNum AND ui.nbsUiComponentUid IN (1016, 1015, 1010)")
  Integer findMaxOrderNbrForSubsection(
      @Param("pageId") Long pageId, @Param("orderNum") Integer orderNum);

  public void deleteAllByWaTemplateUid(WaTemplate template);

  @Query(value = "SELECT w.orderNbr FROM WaUiMetadata w WHERE w.id = :id")
  Optional<Integer> findOrderNumber(@Param("id") Long id);

  @Query(
      value =
          "SELECT w.nbsUiComponentUid FROM WaUiMetadata w WHERE w.orderNbr =:orderNbr AND w.waTemplateUid.id =:pageId")
  Optional<Long> findNbsUiComponentUid(
      @Param("orderNbr") Integer orderNbr, @Param("pageId") Long pageId);

  @Query(
      "SELECT MIN(m.orderNbr) FROM WaUiMetadata m WHERE m.waTemplateUid.id =:page AND m.orderNbr > :orderNbr AND m.nbsUiComponentUid IN (1015, 1010)")
  Integer findOrderNbrOfNextSectionOrTab(
      @Param("orderNbr") Integer orderNbr, @Param("page") long page);
}
