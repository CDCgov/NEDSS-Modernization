package gov.cdc.nbs.patient.contact;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
class PatientContactsByPatientResolver {
    private final ContactNamedByPatientFinder namedByPatientFinder;

    PatientContactsByPatientResolver(final ContactNamedByPatientFinder namedByPatientFinder) {
        this.namedByPatientFinder = namedByPatientFinder;
    }

    @QueryMapping(name = "findContactsForPatient")
    @PreAuthorize("hasAuthority('FIND-PATIENT') and hasAuthority('VIEW-CONTAC')")
    PatientContacts find(@Argument("patient") final long patient) {
        List<PatientContacts.NamedByPatient> contactsNamedByPatients = this.namedByPatientFinder.find(patient);
        return new PatientContacts(
                patient,
                contactsNamedByPatients
        );
    }
}
