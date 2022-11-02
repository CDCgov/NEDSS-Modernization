package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import gov.cdc.nbs.entity.odse.SecurityLog;

public interface SecurityLogRepository
        extends JpaRepository<SecurityLog, Long>, QuerydslPredicateExecutor<SecurityLog> {
    @Query("SELECT coalesce(max(sl.id), 0) FROM SecurityLog sl")
    Long getMaxId();

    public List<SecurityLog> findBySessionIdOrderByEventTimeDesc(String sessionId);
}
