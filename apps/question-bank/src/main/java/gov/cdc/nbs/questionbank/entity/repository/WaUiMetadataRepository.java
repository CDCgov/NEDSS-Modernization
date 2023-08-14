package gov.cdc.nbs.questionbank.entity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.questionbank.entity.WaTemplate;
import gov.cdc.nbs.questionbank.entity.WaUiMetadata;

public interface WaUiMetadataRepository extends JpaRepository<WaUiMetadata, Long> {

    List<WaUiMetadata> findAllByQuestionIdentifier(String questionIdentifier);


    List<WaUiMetadata> findAllByWaTemplateUid(WaTemplate templateId);

    @Modifying
    @Query("Update WaUiMetadata ui SET ui.orderNbr = ui.orderNbr + 1 WHERE ui.waTemplateUid.id = :pageId AND ui.orderNbr >= :orderNumber")
    void incrementOrderNbrGreaterThanOrEqualTo(@Param("pageId") Long pageId, @Param("orderNumber") Integer orderNumber);


    @Query("SELECT COUNT(ui) FROM WaUiMetadata ui WHERE ui.waTemplateUid.id =:pageId AND ui.questionIdentifier =:questionIdentifier")
    Long countByPageAndQuestionIdentifier(
            @Param("pageId") Long pageId,
            @Param("questionIdentifier") String questionIdentifier);

    @Query("Select COALESCE(Max(ui.orderNbr),0) FROM WaUiMetadata ui WHERE ui.waTemplateUid.id =:pageId")
    Integer findMaxOrderNbrForPage(@Param("pageId") Long pageId);
}
