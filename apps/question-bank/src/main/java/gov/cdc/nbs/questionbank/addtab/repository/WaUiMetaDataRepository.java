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
    @Query(value = "SELECT Top 1 order_nbr FROM WA_Ui_metadata w WHERE w.wa_template_uid = ?1 order by order_nbr desc", nativeQuery = true)
    Long findMaxOrderNumberByTemplateUid(Long waTemplateId);

    @Transactional
    @Query(value =  "SELECT" +
            "    COALESCE((SELECT" +
            "            MIN(order_nbr)" +
            "            FROM WA_UI_metadata" +
            "        WHERE" +
            "            wa_template_uid = ?2" +
            "            AND nbs_ui_component_uid = 1010" +
            "            AND order_nbr > (" +
            "                SELECT" +
            "                    order_nbr FROM WA_UI_metadata" +
            "                WHERE" +
            "                    wa_ui_metadata_uid = ?1)), (" +
            "                SELECT" +
            "                    MAX(order_nbr) + 1" +
            "                    FROM WA_UI_metadata" +
            "                WHERE" +
            "                    wa_template_uid = ?2))", nativeQuery = true)
    Long findOrderNbrForSection(Long waUiMetadataUid, Long waTemplateId);


    @Transactional
    @Query(value = "SELECT" +
            "    COALESCE((SELECT" +
            "            MIN(order_nbr)" +
            "            FROM WA_UI_metadata" +
            "        WHERE" +
            "            wa_template_uid = ?2" +
            "            AND nbs_ui_component_uid = 1015" +
            "            AND order_nbr > (" +
            "                SELECT" +
            "                    order_nbr FROM WA_UI_metadata" +
            "                WHERE" +
            "                    wa_ui_metadata_uid = ?1)), (" +
            "                SELECT" +
            "                    MAX(order_nbr) + 1" +
            "                    FROM WA_UI_metadata" +
            "                WHERE" +
            "                    wa_template_uid = ?2))", nativeQuery = true)
    Long findOrderNbrForSubSection(Long waUiMetadataUid, Long waTemplateId);

    @Transactional
    @Query(value ="SELECT" +
            "    MIN(order_nbr)" +
            "    FROM" +
            "            WA_UI_metadata" +
            "    WHERE" +
            "            wa_template_uid = ?2" +
            "    AND nbs_ui_component_uid = 1010" +
            "    AND order_nbr > (" +
            "    SELECT" +
            "            order_nbr" +
            "    FROM" +
            "            WA_UI_metadata" +
            "    WHERE" +
            "            wa_ui_metadata_uid = ?1)", nativeQuery = true)
    Long findOrderNbrForSection_first(Long waUiMetadataUid, Long waTemplateId);

    @Transactional
    @Query(value = "SELECT" +
            "    MIN(order_nbr)" +
            "    FROM" +
            "            WA_UI_metadata" +
            "    WHERE" +
            "            wa_template_uid = ?2" +
            "    AND nbs_ui_component_uid = 1015" +
            "    AND order_nbr > (" +
            "    SELECT" +
            "            order_nbr" +
            "    FROM" +
            "            WA_UI_metadata" +
            "    WHERE" +
            "            wa_ui_metadata_uid = ?1)", nativeQuery = true)
    Long findOrderNbrForSubSection_first(Long waUiMetadataUid, Long waTemplateId);


    @Transactional
    @Modifying
    @Query(value = "update wa_ui_metadata set order_nbr = order_nbr + 1 where order_nbr >= ?1 and wa_ui_metadata_uid!=?2", nativeQuery = true)
    void updateOrderNumber(Integer orderNbr, Long waUiMetadataUid);



}
