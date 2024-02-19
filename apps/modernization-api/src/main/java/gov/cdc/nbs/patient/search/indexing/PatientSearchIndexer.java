package gov.cdc.nbs.patient.search.indexing;

import gov.cdc.nbs.patient.profile.PatientProfileService;
import gov.cdc.nbs.repository.elasticsearch.ElasticsearchPersonRepository;
import org.springframework.stereotype.Component;

@Component
public class PatientSearchIndexer {

  private final PatientProfileService service;
  private final ElasticsearchPersonRepository repository;

  public PatientSearchIndexer(
      final PatientProfileService service,
      final ElasticsearchPersonRepository repository
  ) {
    this.service = service;
    this.repository = repository;
  }

  public void index(final long patient) {

    this.service.with(patient, SearchablePatientConverter::toSearchable)
        .ifPresent(this.repository::save);
  }

}
