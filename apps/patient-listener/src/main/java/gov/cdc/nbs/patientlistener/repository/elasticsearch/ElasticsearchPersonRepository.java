package gov.cdc.nbs.patientlistener.repository.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import gov.cdc.nbs.patientlistener.elasticsearch.ElasticsearchPerson;


public interface ElasticsearchPersonRepository extends ElasticsearchRepository<ElasticsearchPerson, String> {

}