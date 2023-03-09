package gov.cdc.nbs.patient.contact;

import gov.cdc.nbs.entity.odse.Person;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class ContactNamedByPatientResolver {
  private final ContactNamedByPatientFinder finder;

  ContactNamedByPatientResolver(final ContactNamedByPatientFinder finder) {
    this.finder = finder;
  }

  @SchemaMapping("namedByPatient")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
  List<PatientContacts.NamedByPatient> resolve(final Person patient) {
    return this.finder.find(patient.getId());
  }
}
