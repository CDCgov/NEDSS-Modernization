package gov.cdc.nbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;

public interface EntityLocatorParticipationRepository extends JpaRepository<EntityLocatorParticipation, Long>, QuerydslPredicateExecutor<EntityLocatorParticipation> {
	
	@Query(value = "SELECT e.locator_uid from Entity_locator_participation e where e.entity_uid=:personParentUid and e.use_cd='DTH'", nativeQuery = true)
    Long getLocatorIdByPersonParentId(@Param("personParentUid") Long personParentUid);
	
	@Query(value = "SELECT * from Entity_locator_participation e where e.entity_uid=:personParentUid and e.locator_uid=:locatorId and e.use_cd='DTH'", nativeQuery = true)
	Optional<EntityLocatorParticipation> findByEntityIdAndLocatorUid(@Param("personParentUid") Long personParentUid, @Param("locatorId") Long locatorId);

}
