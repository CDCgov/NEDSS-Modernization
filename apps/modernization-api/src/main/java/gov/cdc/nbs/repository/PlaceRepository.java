package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import gov.cdc.nbs.entity.odse.Place;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, QuerydslPredicateExecutor<Place> {
        @Query("SELECT coalesce(max(p.id), 0) FROM Place p")
        Long getMaxId();

}