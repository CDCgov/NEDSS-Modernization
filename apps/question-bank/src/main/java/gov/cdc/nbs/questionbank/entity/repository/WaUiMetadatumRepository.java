package gov.cdc.nbs.questionbank.entity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.WaUiMetadatum;

public interface WaUiMetadatumRepository extends JpaRepository<WaUiMetadatum, Long> {

    List<WaUiMetadatum> findAllByQuestionIdentifier(String questionIdentifier);
}
