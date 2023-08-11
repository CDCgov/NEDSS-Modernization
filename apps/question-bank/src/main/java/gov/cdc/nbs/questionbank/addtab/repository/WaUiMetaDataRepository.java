package gov.cdc.nbs.questionbank.addtab.repository;

import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WaUiMetaDataRepository extends JpaRepository<WaUiMetadata, Long> {

    @Transactional
    @Query(value = "SELECT Top 1 order_nbr FROM WA_Ui_metadata w WHERE w.wa_template_uid = ?1 order by order_nbr desc", nativeQuery = true )
    Long findMaxOrderNumberByTemplateUid(Long waTemplateId);

    @Transactional
    @Query(value = "SELECT" +
            "    COALESCE((SELECT" +
            "            MIN(order_nbr)" +
            "            FROM WA_UI_metadata" +
            "        WHERE" +
            "            wa_template_uid = ?2" +
            "            AND (nbs_ui_component_uid = ?3 or nbs_ui_component_uid = 1010)" +
            "            AND order_nbr > (" +
            "                SELECT" +
            "                    order_nbr FROM WA_UI_metadata" +
            "                WHERE" +
            "                    wa_ui_metadata_uid = ?1)), (" +
            "                SELECT" +
            "                    MAX(order_nbr) + 1" +
            "                    FROM WA_UI_metadata" +
            "                WHERE" +
            "                    wa_template_uid = ?2))", nativeQuery = true )
    Long findOrderNbr_2(Long waUiMetadataUid, Long waTemplateId, Long nbsComponentUid);

    @Transactional
    @Query(value = "SELECT" +
            "    MIN(order_nbr)" +
            "    FROM" +
            "            WA_UI_metadata" +
            "    WHERE" +
            "            wa_template_uid = ?2" +
            "    AND (nbs_ui_component_uid = ?3 or nbs_ui_component_uid = 1010)" +
            "    AND order_nbr > (" +
            "    SELECT" +
            "            order_nbr" +
            "    FROM" +
            "            WA_UI_metadata" +
            "    WHERE" +
            "            wa_ui_metadata_uid = ?1)", nativeQuery = true)
    Long findOrderNbr(Long waUiMetadataUid, Long waTemplateId, Long nbsComponentUid);


    @Transactional
    @Modifying
    @Query(value = "update wa_ui_metadata set order_nbr = order_nbr + 1 where order_nbr >= ?1 and wa_ui_metadata_uid!=?2", nativeQuery = true)
    void updateOrderNumber(Integer orderNbr, Long waUiMetadataUid);

    @Transactional
    @Modifying
    @Query(value = "update wa_ui_metadata set question_label = ?1 where wa_ui_metadata_uid=?2", nativeQuery = true)
    void updateQuestionLabel(String questionLabel, Long waUiMetadataUid);

    @Transactional
    @Modifying
    @Query(value = "update wa_ui_metadata set display_ind = ?1 where wa_ui_metadata_uid=?2", nativeQuery = true)
    void updateVisibility(String visibility, Long waUiMetadataUid);

    @Transactional
    @Modifying
    @Query(value = "update wa_ui_metadata set question_label = ?1, display_ind = ?2 where wa_ui_metadata_uid=?3", nativeQuery = true)
    void updateQuestionLabelAndVisibility(String questionLabel, String visibility, Long waUiMetadataUid);

    @Transactional
    @Modifying
    @Query(value = "update wa_ui_metadata set order_nbr = order_nbr - 1 where order_nbr >= ?1 and wa_ui_metadata_uid!=?2", nativeQuery = true)
    void updateOrderNumberByDecreasing(Integer orderNbr, Long waUiMetadataUid);


    @Query(value = "select order_nbr from WA_Ui_metadata w where w.wa_ui_metadata_uid=?1", nativeQuery = true)
    Integer getOrderNumber(Long waUiMetadataUid);

    @Transactional
    @Modifying
    @Query(value = "delete from WA_Ui_metadata where wa_ui_metadata_uid=?1", nativeQuery = true)
    void deletefromTable(Long waUiMetadataUid);

}
