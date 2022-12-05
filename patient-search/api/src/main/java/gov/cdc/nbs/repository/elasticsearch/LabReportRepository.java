package gov.cdc.nbs.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import gov.cdc.nbs.entity.elasticsearch.LabReport;

public interface LabReportRepository extends ElasticsearchRepository<LabReport, String> {

}
