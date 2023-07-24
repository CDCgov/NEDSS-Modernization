package gov.cdc.nbs.questionbank.addtab.repository;

import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WaUiMetaDataRepository extends JpaRepository<WaUiMetadata, Long> {

    @Query(value = "SELECT MAX(w.order_nbr) FROM WA_Ui_metadata w WHERE w.nbs_ui_component_uid = '1010'", nativeQuery = true)
    Long findMaxOrderNumberByNbsUiComponentUid(Long nbsUiComponentUid);
}
