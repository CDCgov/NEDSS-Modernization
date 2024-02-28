package gov.cdc.nbs.patient.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientSearcher {
  Page<PatientSearchResult> search(
      PatientFilter criteria,
      Pageable pageable
  );
}
