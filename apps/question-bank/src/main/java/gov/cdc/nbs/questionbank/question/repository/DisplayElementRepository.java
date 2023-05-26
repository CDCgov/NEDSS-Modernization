package gov.cdc.nbs.questionbank.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entities.DisplayElementEntity;
import gov.cdc.nbs.questionbank.entities.DisplayElementId;

public interface DisplayElementRepository extends JpaRepository<DisplayElementEntity, DisplayElementId> {

}
