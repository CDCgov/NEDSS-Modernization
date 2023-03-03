package gov.cdc.nbs.patient.contact;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class PatientContactsByPatientResolver {
  private final ContactNamedByPatientFinder namedByPatientFinder;
  private final PatientNamedByContactFinder namedByContactFinder;

  PatientContactsByPatientResolver(
      final ContactNamedByPatientFinder namedByPatientFinder,
      final PatientNamedByContactFinder namedByContactFinder
  ) {
    this.namedByPatientFinder = namedByPatientFinder;
    this.namedByContactFinder = namedByContactFinder;
  }

  @QueryMapping(name = "findContactsForPatient")
  @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-INVESTIGATION')")
  PatientContacts find(@Argument("patient") final long patient) {
    List<PatientContacts.NamedByPatient> namedByPatients = this.namedByPatientFinder.find(patient);
    List<PatientContacts.NamedByContact> namedByContacts = this.namedByContactFinder.find(patient);
    return new PatientContacts(
        patient,
        namedByPatients,
        namedByContacts
    );
  }
}
