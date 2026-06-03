package gov.cdc.nbs.repository;

import gov.cdc.nbs.entity.odse.DataSourceColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceColumnRepository extends JpaRepository<DataSourceColumn, Long> {}
