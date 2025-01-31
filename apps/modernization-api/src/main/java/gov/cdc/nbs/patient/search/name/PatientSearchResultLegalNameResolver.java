package gov.cdc.nbs.patient.search.name;

import gov.cdc.nbs.patient.search.PatientSearchResult;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

@Controller
class PatientSearchResultLegalNameResolver {

  private final Clock clock;
  private final PatientSearchResultLegalNameFinder finder;


  PatientSearchResultLegalNameResolver(
      final Clock clock,
      final PatientSearchResultLegalNameFinder finder
  ) {
    this.clock = clock;
    this.finder = finder;
  }

  @SchemaMapping(typeName = "PatientSearchResult", field = "legalName")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Optional<PatientSearchResultName> resolve(final PatientSearchResult patient) {
    return this.finder.find(patient.patient(), LocalDate.now(clock));
  }

}
