package gov.cdc.nbs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import gov.cdc.nbs.entity.odse.ActId;
import gov.cdc.nbs.entity.odse.ActIdId;
import gov.cdc.nbs.entity.projections.ActId2;

public interface ActIdRepository extends JpaRepository<ActId, ActIdId>, QuerydslPredicateExecutor<ActId> {
    @Query(value = "SELECT * FROM act_id WHERE act_uid=:actUid", nativeQuery = true)
    List<ActId2> findAllByActUid(@Param("actUid") long actUid);
}
