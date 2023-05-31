package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.srte.NaicsIndustryCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface NaicsIndustryCodeRepository extends JpaRepository<NaicsIndustryCode, Long>,
        QuerydslPredicateExecutor<NaicsIndustryCode> {
}
