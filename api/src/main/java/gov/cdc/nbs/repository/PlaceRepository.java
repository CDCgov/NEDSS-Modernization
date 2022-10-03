package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>, QuerydslPredicateExecutor<Place> {
        @Query("SELECT coalesce(max(p.id), 0) FROM Place p")
        Long getMaxId();

}