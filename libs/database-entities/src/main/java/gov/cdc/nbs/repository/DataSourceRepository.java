package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.DataSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<DataSource, Long> {}
