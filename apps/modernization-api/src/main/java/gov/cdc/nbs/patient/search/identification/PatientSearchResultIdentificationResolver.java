package gov.cdc.nbs.patient.search.identification;

import gov.cdc.nbs.entity.odse.Person;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
class PatientSearchResultIdentificationResolver {

  private final PatientSearchResultIdentificationFinder finder;

  PatientSearchResultIdentificationResolver(final PatientSearchResultIdentificationFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping(typeName = "Person" ,field = "identification")
  @PreAuthorize("hasAuthority('FIND-PATIENT')")
  Collection<PatientSearchResultIdentification> resolve(final Person patient) {
    return this.finder.find(patient.getId());
  }
}
