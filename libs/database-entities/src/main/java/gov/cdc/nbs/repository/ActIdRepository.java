package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.ActId;
import gov.cdc.nbs.entity.odse.ActIdId;

public interface ActIdRepository extends JpaRepository<ActId, ActIdId>, QuerydslPredicateExecutor<ActId> {
    @Query("SELECT * FROM act_ids WHERE act_uid=:actUid")
    List<ActId> findAllByActUid(@Param("actUid") long actUid);
}
