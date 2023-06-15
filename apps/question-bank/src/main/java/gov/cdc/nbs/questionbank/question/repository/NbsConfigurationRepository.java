package gov.cdc.nbs.questionbank.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gov.cdc.nbs.questionbank.entity.NbsConfiguration;

public interface NbsConfigurationRepository extends JpaRepository<NbsConfiguration, String> {

}
