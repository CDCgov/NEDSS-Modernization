package gov.cdc.nbs.questionbank.condition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.condition.ConditionCode;

public interface ConditionRepository extends JpaRepository<ConditionCode, String> {
    
}
