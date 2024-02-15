package gov.cdc.nbs.patient.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface PatientSearcher {
  Page<PatientSearchResult> search(
      PatientFilter criteria,
      Pageable pageable
  ) throws IOException;
}
