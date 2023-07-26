package gov.cdc.nbs.questionbank.entity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;

public interface WaUiMetadatumRepository extends JpaRepository<WaUiMetadatum, Long> {

    List<WaUiMetadatum> findAllByQuestionIdentifier(String questionIdentifier);

    @Modifying
    @Query("Update WaUiMetadatum ui SET ui.orderNbr = ui.orderNbr + 1 WHERE ui.waTemplateUid.id = :pageId AND ui.orderNbr >= :orderNumber")
    void incrementOrderNbrGreaterThanOrEqualTo(@Param("pageId") Long pageId, @Param("orderNumber") Integer orderNumber);


    @Query("SELECT COUNT(ui) FROM WaUiMetadatum ui WHERE ui.waTemplateUid.id =:pageId AND ui.questionIdentifier =:questionIdentifier")
    Long countByPageAndQuestionIdentifier(
            @Param("pageId") Long pageId,
            @Param("questionIdentifier") String questionIdentifier);
}
