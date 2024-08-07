package gov.cdc.nbs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import gov.cdc.nbs.entity.odse.EntityLocatorParticipation;
import gov.cdc.nbs.entity.odse.PostalEntityLocatorParticipation;

public interface EntityLocatorParticipationRepository
        extends JpaRepository<EntityLocatorParticipation, Long>, QuerydslPredicateExecutor<EntityLocatorParticipation> {

    @Query(value = "SELECT e from EntityLocatorParticipation e where e.nbsEntity.id=:personUid and e.useCd='DTH'")
    Optional<PostalEntityLocatorParticipation> findMortalityLocatorParticipation(@Param("personUid") Long personUid);

    @Query(value = "SELECT * from Entity_locator_participation e where e.entity_uid=:personParentUid and e.locator_uid=:locatorId and e.use_cd='DTH'",
            nativeQuery = true)
    Optional<EntityLocatorParticipation> findByEntityIdAndLocatorUid(@Param("personParentUid") Long personParentUid,
            @Param("locatorId") Long locatorId);

}
