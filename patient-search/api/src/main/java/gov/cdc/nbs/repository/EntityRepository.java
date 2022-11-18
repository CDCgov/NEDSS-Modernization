package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.odse.NBSEntity;

public interface EntityRepository extends JpaRepository<NBSEntity, Long> {

}
