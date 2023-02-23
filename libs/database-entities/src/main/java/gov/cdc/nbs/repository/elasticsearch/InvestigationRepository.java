package gov.cdc.nbs.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import gov.cdc.nbs.entity.elasticsearch.Investigation;

public interface InvestigationRepository extends ElasticsearchRepository<Investigation, String> {

}
