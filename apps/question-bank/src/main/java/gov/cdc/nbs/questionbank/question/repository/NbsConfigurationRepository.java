package gov.cdc.nbs.questionbank.question.repository;

import gov.cdc.nbs.questionbank.entity.NbsConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NbsConfigurationRepository extends JpaRepository<NbsConfiguration, String> {}
