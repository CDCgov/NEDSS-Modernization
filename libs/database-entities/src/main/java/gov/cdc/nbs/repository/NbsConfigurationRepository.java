package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.NbsConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbsConfigurationRepository extends JpaRepository<NbsConfiguration, String> {}
