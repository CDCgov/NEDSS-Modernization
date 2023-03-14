package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.entity.odse.Person;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class PatientNamedByContactResolver {
  private final PatientNamedByContactFinder finder;

  PatientNamedByContactResolver(final PatientNamedByContactFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping("namedByContact")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
  List<PatientContacts.NamedByContact> resolve(final Person patient) {
    return this.finder.find(patient.getId());
  }
}
