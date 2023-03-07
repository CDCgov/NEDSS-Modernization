package gov.cdc.nbs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.srte.StateCountyCodeValue;

public interface StateCountyCodeRepository
        extends JpaRepository<StateCountyCodeValue, String>, QuerydslPredicateExecutor<StateCountyCodeValue> {

    /**
     * Finds all counties within the specified state
     *
     * @param stateCode refers to {@link gov.cdc.nbs.entity.srte.StateCode#id}
     * @param pageable
     * @return
     */
    Page<StateCountyCodeValue> findAllByParentIsCd(String stateCode, Pageable pageable);

}
