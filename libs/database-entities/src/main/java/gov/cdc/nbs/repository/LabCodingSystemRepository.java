package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.srte.LabCodingSystem;

public interface LabCodingSystemRepository extends JpaRepository<LabCodingSystem, String> {

}
