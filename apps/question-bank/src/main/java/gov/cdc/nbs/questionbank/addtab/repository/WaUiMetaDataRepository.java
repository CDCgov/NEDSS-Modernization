package gov.cdc.nbs.questionbank.addtab.repository;

import gov.cdc.nbs.questionbank.entity.addtab.WaUiMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WaUiMetaDataRepository extends JpaRepository<WaUiMetadata, Long> {

    @Query(value = "SELECT MAX(order_nbr) FROM WA_Ui_metadata", nativeQuery = true)
    Long findMaxOrderNumber();
}
