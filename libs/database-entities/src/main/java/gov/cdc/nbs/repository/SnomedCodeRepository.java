package gov.cdc.nbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gov.cdc.nbs.entity.srte.SnomedCode;

public interface SnomedCodeRepository extends JpaRepository<SnomedCode, String> {


}
