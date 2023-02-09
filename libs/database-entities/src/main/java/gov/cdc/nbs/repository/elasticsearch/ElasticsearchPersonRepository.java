package gov.cdc.nbs.repository.elasticsearch;

import gov.cdc.nbs.entity.elasticsearch.ElasticsearchPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticsearchPersonRepository extends ElasticsearchRepository<ElasticsearchPerson, String> {

}